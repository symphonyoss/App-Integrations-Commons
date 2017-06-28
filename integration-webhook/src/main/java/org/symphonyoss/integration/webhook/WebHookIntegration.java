/**
 * Copyright 2016-2017 Symphony Integrations - Symphony LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.symphonyoss.integration.webhook;

import static org.symphonyoss.integration.messageml.MessageMLFormatConstants.MESSAGEML_END;
import static org.symphonyoss.integration.messageml.MessageMLFormatConstants.MESSAGEML_START;
import static org.symphonyoss.integration.model.healthcheck.IntegrationFlags.ValueEnum.NOK;
import static org.symphonyoss.integration.utils.WebHookConfigurationUtils.LAST_POSTED_DATE;

import com.codahale.metrics.Timer;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.symphonyoss.integration.BaseIntegration;
import org.symphonyoss.integration.entity.model.User;
import org.symphonyoss.integration.exception.IntegrationRuntimeException;
import org.symphonyoss.integration.exception.RemoteApiException;
import org.symphonyoss.integration.exception.authentication.AuthenticationException;
import org.symphonyoss.integration.exception.authentication.ConnectivityException;
import org.symphonyoss.integration.exception.bootstrap.BootstrapException;
import org.symphonyoss.integration.exception.bootstrap.RetryLifecycleException;
import org.symphonyoss.integration.exception.bootstrap.UnexpectedBootstrapException;
import org.symphonyoss.integration.exception.config.ForbiddenUserException;
import org.symphonyoss.integration.exception.config.IntegrationConfigException;
import org.symphonyoss.integration.json.JsonUtils;
import org.symphonyoss.integration.model.config.IntegrationInstance;
import org.symphonyoss.integration.model.config.IntegrationSettings;
import org.symphonyoss.integration.model.healthcheck.IntegrationHealth;
import org.symphonyoss.integration.model.message.Message;
import org.symphonyoss.integration.model.message.MessageMLVersion;
import org.symphonyoss.integration.model.stream.StreamType;
import org.symphonyoss.integration.model.yaml.Application;
import org.symphonyoss.integration.service.IntegrationBridge;
import org.symphonyoss.integration.service.IntegrationService;
import org.symphonyoss.integration.service.StreamService;
import org.symphonyoss.integration.service.UserService;
import org.symphonyoss.integration.utils.WebHookConfigurationUtils;
import org.symphonyoss.integration.webhook.exception.InvalidStreamTypeException;
import org.symphonyoss.integration.webhook.exception.StreamTypeNotFoundException;
import org.symphonyoss.integration.webhook.exception.WebHookDisabledException;
import org.symphonyoss.integration.webhook.exception.WebHookParseException;
import org.symphonyoss.integration.webhook.exception.WebHookUnavailableException;
import org.symphonyoss.integration.webhook.exception.WebHookUnprocessableEntityException;
import org.symphonyoss.integration.webhook.metrics.ParserMetricsController;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;

/**
 * WebHook based Integrations, implementing common methods for WebHookIntegrations and defining
 * what needs to be done by it's future implementations.
 *
 * Created by Milton Quilzini on 04/05/16.
 */
public abstract class WebHookIntegration extends BaseIntegration {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebHookIntegration.class);

  private static final String IM_WELCOME_MESSAGE = "Hi there. This is the %s application. I'll let"
      + " you know of any new events sent from the %s integration you configured.";

  private static final String CHAT_ROOM_WELCOME_MESSAGE = "Hi there. This is the %s application. "
      + "I'll let you know of any new events sent from the %s integration configured by %s.";

  private static final String WELCOME_EVENT = "welcome";

  private static final String UNKNOWN_USER = "UNKNOWN";

  private static final String PROLOG_ML_REGEX =
      "((<\\?xml version =\\\")[\\d].[\\d]\\\"[\\s\\w\\d=\"\\?-]*>)";

  @Autowired
  @Qualifier("remoteIntegrationService")
  private IntegrationService integrationService;

  @Autowired
  private IntegrationBridge bridge;

  @Autowired
  private StreamService streamService;

  @Autowired
  private UserService userService;

  @Autowired
  private ParserMetricsController metricsController;

  /**
   * Local Configuration kept for faster processing.
   */
  private IntegrationSettings settings;

  /**
   * Represents the current circuit state that this integration uses to determine whether it is
   * available to receive messages or not. If this flag changes to false, the integration will
   * temporarily stop accepting messages, to prevent unnecessary calls that are likely to fail.
   * After a set timeout in "circuit open" state, the circuit will "close", enabling this
   * integration to accept messages again.
   */
  private boolean circuitClosed = true;

  /**
   * Time, in milliseconds, that this integration circuit breaker will remain open.
   */
  private static final long circuitTimeout = 10000L;

  /**
   * Used to control the timeout for the circuit breaker mechanism used by this integration.
   */
  private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

  /**
   * Entity fields
   */
  private static final String ENTITY_TYPE = "type";
  private static final String ENTITY_VERSION = "version";

  /**
   * Ownership fields
   */
  private static final String OWNERSHIP = "ownership";
  private static final String OWNERSHIP_ENTITY_TYPE = "com.symphony.integration.ownership";
  private static final String OWNERSHIP_ENTITY_VERSION = "1.0";
  private static final String USER_ID = "userId";
  private static final String USER_NAME = "username";

  @Override
  public void onCreate(String integrationUser) {
    LOGGER.info("Create " + getClass().getCanonicalName());

    setupHealthManager(integrationUser);

    try {
      registerUser(integrationUser);
    } catch (BootstrapException e) {
      healthManager.certificateInstalled(NOK);

      healthManager.failBootstrap(e.getMessage());
      throw e;
    } catch (Exception e) {
      healthManager.failBootstrap(e.getMessage());
      throw new UnexpectedBootstrapException(
          String.format("Something went wrong when bootstrapping the integration %s",
              integrationUser), e);
    }

    try {
      authenticate(integrationUser);
      updateConfiguration(integrationUser);

      healthManager.success(settings);
    } catch (ConnectivityException | RetryLifecycleException e) {
      Throwable cause = e.getCause() != null ? e.getCause() : e;
      String message = String.format("%s. Cause: %s", e.getMessage(), cause.getMessage());
      healthManager.retry(message);
      throw e;
    } catch (IntegrationRuntimeException e) {
      Throwable cause = e.getCause() != null ? e.getCause() : e;
      String message = String.format("%s. Cause: %s", e.getMessage(), cause.getMessage());
      healthManager.failBootstrap(message);
      throw e;
    } catch (Exception e) {
      healthManager.failBootstrap(e.getMessage());
      throw new UnexpectedBootstrapException(
          String.format("Something went wrong when bootstrapping the integration %s",
              integrationUser), e);

    }
  }

  private void authenticate(String integrationUser) {
    try {
      authenticationProxy.authenticate(integrationUser);
    } catch (AuthenticationException e) {
      throw new RetryLifecycleException("Unexpected error when authenticating", e);
    }
  }

  private void updateConfiguration(String integrationUser) throws IntegrationConfigException {
    IntegrationSettings settings =
        this.integrationService.getIntegrationByType(integrationUser, integrationUser);
    onConfigChange(settings);
  }

  @Override
  public void onConfigChange(IntegrationSettings settings) {
    this.settings = settings;
  }

  @Override
  public void onDestroy() {
    LOGGER.info("Release resources to " + getClass().getCanonicalName());
    authenticationProxy.invalidate(settings.getType());
  }

  /**
   * Parse the incoming message received from third party services. It should be used to filter
   * which events the integration must handle according to user settings stored on the webhook
   * instance.
   * @param instance Integration instance that contains user settings
   * @param input Message received from the third party services
   * @return Formatted MessageML or null if the integration doesn't handle this specific event
   * @throws WebHookParseException Reports failure to validate message received from third party
   * services.
   */
  public Message parse(IntegrationInstance instance, WebHookPayload input)
      throws WebHookParseException {
    return parse(input);
  }

  /**
   * Parse the incoming message received from the third party services.
   * @param input Message received from the third party services
   * @return Formatted MessageML or null if the integration doesn't handle this specific event
   * @throws WebHookParseException Reports failure to validate message received from third party
   * services.
   */
  public Message parse(WebHookPayload input) throws WebHookParseException {
    return null;
  }

  /**
   * Handle the requests received from the third party services, validate them into proper format
   * and post messages to streams.
   * @param instanceId
   * @param input
   * @throws WebHookParseException
   */
  public void handle(String instanceId, String integrationUser, WebHookPayload input)
      throws WebHookParseException, RemoteApiException {
    if (isAvailable()) {
      IntegrationInstance instance = getIntegrationInstance(instanceId);
      Message message = parseRequest(instance, integrationUser, input);

      if (message != null) {
        List<String> streams = streamService.getStreams(instance);

        if (MessageMLVersion.V2.equals(message.getVersion())) {
          includeOwnershipOnMessageData(message, instance);
        }

        postMessage(instance, integrationUser, streams, message);
      } else {
        String erroMessage = String.format("Event not handled by the %s", integrationUser);
        throw new WebHookUnprocessableEntityException(erroMessage);
      }
    }
  }

  /**
   * Add ownership info into the EntityJSON. It's required for auditing process.
   * @param message Message to be posted
   * @param instance Integration instance
   */
  private void includeOwnershipOnMessageData(Message message, IntegrationInstance instance) {
    String creatorId = instance.getCreatorId();
    String creatorName = instance.getCreatorName();

    if ((StringUtils.isEmpty(creatorId)) && (StringUtils.isEmpty(creatorName))) {
      LOGGER.info("No ownership info for instance {}", instance.getInstanceId());
      return;
    }

    String data = message.getData();

    try {
      ObjectNode dataNode;

      if (StringUtils.isEmpty(data)) {
        dataNode = JsonNodeFactory.instance.objectNode();
      } else {
        dataNode = (ObjectNode) JsonUtils.readTree(data);
      }

      ObjectNode ownership = dataNode.putObject(OWNERSHIP);
      ownership.put(ENTITY_TYPE, OWNERSHIP_ENTITY_TYPE);
      ownership.put(ENTITY_VERSION, OWNERSHIP_ENTITY_VERSION);

      if (StringUtils.isNotEmpty(creatorId)) {
        ownership.put(USER_ID, creatorId);
      }

      if (StringUtils.isNotEmpty(creatorName)) {
        ownership.put(USER_NAME, creatorName);
      }

      String newData = JsonUtils.writeValueAsString(dataNode);
      message.setData(newData);
    } catch (Exception e) {
      LOGGER.warn("Fail to set ownership info for instance " + instance.getInstanceId(), e);
    }
  }

  /**
   * Wraps the parser execution and monitor the parser execution time.
   * @param instance Integration instance
   * @param integrationUser Integration username
   * @param input Webhhok payload
   * @return Formatted MessageML or null if the integration doesn't handle this specific event
   */
  private Message parseRequest(IntegrationInstance instance, String integrationUser,
      WebHookPayload input) {
    Timer.Context context = null;
    boolean success = false;
    Message message = null;

    try {
      context = metricsController.startParserExecution(integrationUser);
      message = parse(instance, input);
      success = true;
    } finally {
      metricsController.finishParserExecution(context, integrationUser, success);
    }

    return message;
  }

  /**
   * Post the welcome message to streams.
   * @param instance Integration instance
   * @param integrationUser Integration username
   * @param payload Json Node that contains a list of streams
   * @throws IOException Reports failure to validate the payload
   */
  public void welcome(IntegrationInstance instance, String integrationUser, String payload)
      throws RemoteApiException {
    List<String> instanceStreams = streamService.getStreams(instance);
    List<String> streams = streamService.getStreams(payload);

    // Remove unexpected streams
    for (Iterator<String> it = streams.iterator(); it.hasNext(); ) {
      String stream = it.next();
      if (!instanceStreams.contains(stream)) {
        it.remove();
      }
    }

    if (streams.isEmpty()) {
      throw new StreamTypeNotFoundException(
          "Cannot determine the stream type of the configuration instance");
    }

    String message = getWelcomeMessage(instance, integrationUser);

    if (!StringUtils.isEmpty(message)) {
      Message formattedMessage = buildMessageML(message, WELCOME_EVENT);
      postMessage(instance, integrationUser, streams, formattedMessage);
    }
  }

  /**
   * Get the welcome message based on stream type configured in the instance.
   * @param instance Integration instance
   * @param integrationUser Integration username
   * @return Welcome message
   */
  private String getWelcomeMessage(IntegrationInstance instance, String integrationUser) {
    StreamType streamType = streamService.getStreamType(instance);
    String appName = settings.getName();

    switch (streamType) {
      case IM:
        return String.format(IM_WELCOME_MESSAGE, appName, appName);
      case CHATROOM:
        String user = getUserDisplayName(integrationUser, instance.getCreatorId());
        String userDisplayName = StringUtils.isEmpty(user) ? UNKNOWN_USER : user;
        return String.format(CHAT_ROOM_WELCOME_MESSAGE, appName, appName, userDisplayName);
      case NONE:
      default:
        throw new InvalidStreamTypeException("The stream type has an invalid value");
    }
  }

  /**
   * Returns the user displayName if it exists, null otherwise.
   * @param integrationUser Integration username
   * @param userId the user identifier to be searched
   * @return the user displayName if it exists, null otherwise.
   */
  private String getUserDisplayName(String integrationUser, String userId) {
    if ((userId == null) || (userId.isEmpty())) {
      return StringUtils.EMPTY;
    }

    Long uid = Long.valueOf(userId);
    User user2 = userService.getUserByUserId(integrationUser, uid);
    if (user2 != null) {
      return user2.getDisplayName();
    } else {
      return StringUtils.EMPTY;
    }
  }

  /**
   * Post messages to streams and update latest post timestamp.
   * @param instance Integration instance
   * @param integrationUser Integration username
   * @param streams List of streams
   * @param message Formatted MessageML
   */
  private void postMessage(IntegrationInstance instance, String integrationUser,
      List<String> streams, Message message) throws RemoteApiException {
    // Post a message
    List<Message> response = sendMessage(instance, integrationUser, streams, message);
    // Get the timestamp of the last message posted
    Long lastPostedDate = getLastPostedDate(response);

    if (lastPostedDate > 0) {
      // Update the configuration instance
      updateLastPostedDate(instance, integrationUser, lastPostedDate);
    }
  }

  /**
   * Send messages to streams using Integration Bridge service.
   * @param instance Integration instance
   * @param integrationUser Integration username
   * @param streams List of streams
   * @param message Formatted MessageML
   * @return List of messages posted to the streams.
   */
  private List<Message> sendMessage(IntegrationInstance instance, String integrationUser,
      List<String> streams, Message message) throws RemoteApiException {
    // Post a message
    List<Message> response = bridge.sendMessage(instance, integrationUser, streams, message);
    return response;
  }

  /**
   * Get the most recent posted date in the provided list of responses
   * @param response
   */
  private Long getLastPostedDate(List<Message> response) {
    Long lastPostedDate = 0L;

    for (Message responseMessage : response) {
      // Get last posted date
      lastPostedDate = Math.max(lastPostedDate, responseMessage.getTimestamp());
    }
    return lastPostedDate;
  }

  /**
   * Update the integration instance with the last posted date.
   * @param instance Integration instance
   * @param timestamp Last posted date
   */
  private void updateLastPostedDate(IntegrationInstance instance, String integrationUser,
      Long timestamp) {
    try {
      // Update posted date
      ObjectNode whiConfigInstance =
          WebHookConfigurationUtils.fromJsonString(instance.getOptionalProperties());
      whiConfigInstance.put(LAST_POSTED_DATE, timestamp);
      instance.setOptionalProperties(WebHookConfigurationUtils.toJsonString(whiConfigInstance));
      integrationService.save(instance, integrationUser);

      healthManager.updateLatestPostTimestamp(timestamp);
    } catch (IntegrationRuntimeException | IOException e) {
      LOGGER.error("Fail to update the last posted date", e);
    }
  }

  /**
   * Builds a Symphony MessageML with a given formatted message.
   * @param message a message properly formatted with Symphony-only tags and/or pure text.
   * @param webHookEvent the event being processed.
   * @return the Symphony MessageML message.
   */
  protected Message buildMessageML(String message, String webHookEvent) {
    if (!StringUtils.isBlank(message)) {
      //Remove XML prolog
      String formattedMessage = message.replaceAll(PROLOG_ML_REGEX, "");
      formattedMessage = MESSAGEML_START + formattedMessage + MESSAGEML_END;

      Message messageSubmission = new Message();
      messageSubmission.setMessage(formattedMessage);
      messageSubmission.setFormat(Message.FormatEnum.MESSAGEML);
      messageSubmission.setVersion(MessageMLVersion.V1);

      return messageSubmission;
    } else {
      LOGGER.info("Unhandled event {}", webHookEvent);
      return null;
    }
  }

  @Override
  public IntegrationHealth getHealthStatus() {
    return healthManager.updateFlags();
  }

  @Override
  public IntegrationSettings getSettings() {
    return settings;
  }

  /**
   * Checks if the integration is available on the moment of this call.
   * It verifies: if the circuit is closed, then if it's able to read its up to date configuration,
   * and then if it's enabled.
   * Any of those checks failing means it's not available now, and it will throw an exception.
   * If the integration is not enabled, it will open its internal circuit.
   * It will also open its circuit if it can't read its own configuration due to a faulty user.
   * @return If all checks pass, returns "true", throws exceptions otherwise.
   * @throws WebHookUnavailableException if its internal circuit is open or if it can't read it's
   * own configuration due to a faulty user.
   * @throws WebHookDisabledException if the integration is not enabled.
   */
  public boolean isAvailable() {
    String type = settings.getType();

    if (this.circuitClosed) {
      try {
        IntegrationSettings whiConfiguration =
            this.integrationService.getIntegrationById(settings.getConfigurationId(), type);

        if (!whiConfiguration.getEnabled()) {
          openCircuit();
          throw new WebHookDisabledException(type);
        } else {
          return this.circuitClosed;
        }
      } catch (ForbiddenUserException e) {
        openCircuit();
        throw new WebHookUnavailableException(type, getHealthStatus().getMessage());
      }
    } else {
      throw new WebHookUnavailableException(type, getHealthStatus().getMessage());
    }
  }

  /**
   * Opens the internal circuit and sets a timer to close it again according to the value on
   * circuitTimeout variable on this class.
   */
  private void openCircuit() {
    this.circuitClosed = false;
    this.scheduler.schedule(new Runnable() {
      @Override
      public void run() {
        closeCircuit();
      }
    }, circuitTimeout, TimeUnit.MILLISECONDS);
  }

  private void closeCircuit() {
    this.circuitClosed = true;
  }

  /**
   * Retrieve the integration instance based on instanceId.
   * @param instanceId Integration instance identifier
   * @return Integration instance that contains information how to handle requests.
   */
  protected IntegrationInstance getIntegrationInstance(String instanceId) {
    return integrationService.getInstanceById(settings.getConfigurationId(), instanceId,
        settings.getType());
  }

  @Override
  public Set<String> getIntegrationWhiteList() {
    if (settings == null) {
      return Collections.emptySet();
    }

    Application application = properties.getApplication(settings.getType());

    if (application == null) {
      return Collections.emptySet();
    }

    return application.getWhiteList();
  }

  /**
   * Retrieve the supported content types of the Integration.
   * @return List with all the supported types by that integration.
   */
  public abstract List<MediaType> getSupportedContentTypes();

  /**
   * Checks if a payload content type is supported by the integration.
   * @param contentType to check if it is supported.
   * @return boolean indicating if it is supported.
   */
  public boolean isSupportedContentType(MediaType contentType) {
    // If none information can be found about supported content-types, all types will be accepted
    // then
    List<MediaType> supportedContentTypes = getSupportedContentTypes();
    if (supportedContentTypes == null
        || supportedContentTypes.isEmpty()
        || supportedContentTypes.contains(MediaType.WILDCARD_TYPE)) {
      return true;
    }
    // Supported types are specified, so, let's check they match
    return supportedContentTypes.contains(contentType);
  }
}

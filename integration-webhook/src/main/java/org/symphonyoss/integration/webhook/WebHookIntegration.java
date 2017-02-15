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

import com.symphony.api.agent.model.V2BaseMessage;
import com.symphony.api.agent.model.V2MessageList;
import com.symphony.api.auth.client.ApiException;
import com.symphony.api.pod.model.ConfigurationInstance;
import com.symphony.api.pod.model.V1Configuration;
import com.symphony.logging.ISymphonyLogger;

import com.codahale.metrics.Timer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.symphonyoss.integration.BaseIntegration;
import org.symphonyoss.integration.entity.model.User;
import org.symphonyoss.integration.exception.IntegrationRuntimeException;
import org.symphonyoss.integration.exception.authentication.ConnectivityException;
import org.symphonyoss.integration.exception.bootstrap.BootstrapException;
import org.symphonyoss.integration.exception.bootstrap.RetryLifecycleException;
import org.symphonyoss.integration.exception.bootstrap.UnexpectedBootstrapException;
import org.symphonyoss.integration.exception.config.ForbiddenUserException;
import org.symphonyoss.integration.exception.config.IntegrationConfigException;
import org.symphonyoss.integration.logging.IntegrationBridgeCloudLoggerFactory;
import org.symphonyoss.integration.model.config.IntegrationSettings;
import org.symphonyoss.integration.model.config.StreamType;
import org.symphonyoss.integration.model.healthcheck.IntegrationHealth;
import org.symphonyoss.integration.model.yaml.Application;
import org.symphonyoss.integration.service.ConfigurationService;
import org.symphonyoss.integration.service.IntegrationBridge;
import org.symphonyoss.integration.service.StreamService;
import org.symphonyoss.integration.service.UserService;
import org.symphonyoss.integration.utils.WebHookConfigurationUtils;
import org.symphonyoss.integration.webhook.exception.InvalidStreamTypeException;
import org.symphonyoss.integration.webhook.exception.StreamTypeNotFoundException;
import org.symphonyoss.integration.webhook.exception.WebHookDisabledException;
import org.symphonyoss.integration.webhook.exception.WebHookParseException;
import org.symphonyoss.integration.webhook.exception.WebHookUnavailableException;
import org.symphonyoss.integration.webhook.metrics.ParserMetricsController;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.ProcessingException;

/**
 * WebHook based Integrations, implementing common methods for WebHookIntegrations and defining
 * what needs to be done by it's future implementations.
 *
 * Created by Milton Quilzini on 04/05/16.
 */
public abstract class WebHookIntegration extends BaseIntegration {

  private static final ISymphonyLogger LOGGER =
      IntegrationBridgeCloudLoggerFactory.getLogger(WebHookIntegration.class);

  private static final String IM_WELCOME_MESSAGE = "Hi there. This is the %s application. I'll let"
      + " you know of any new events sent from the %s integration you configured.";

  private static final String CHAT_ROOM_WELCOME_MESSAGE = "Hi there. This is the %s application. "
      + "I'll let you know of any new events sent from the %s integration configured by %s.";

  private static final String WELCOME_EVENT = "welcome";

  private static final String UNKNOWN_USER = "UNKNOWN";

  @Autowired
  @Qualifier("remoteConfigurationService")
  private ConfigurationService configService;

  @Autowired
  private IntegrationBridge bridge;

  @Autowired
  private StreamService streamService;

  @Autowired
  private WebHookExceptionHandler exceptionHandler;

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
      String message = String.format("%s. Cause: %s", e.getMessage(), e.getCause().getMessage());
      healthManager.retry(message);
      throw e;
    } catch (IntegrationRuntimeException e) {
      String message = String.format("%s. Cause: %s", e.getMessage(), e.getCause().getMessage());
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
    } catch (ApiException e) {
      exceptionHandler.handleAuthenticationApiException(integrationUser, e);
    } catch (ConnectivityException e) {
      // Needed to rethrow this specific exception, otherwise it will endup hidden under the
      // catch bellow.
      throw e;
    } catch (Exception e) {
      exceptionHandler.handleAuthException(e);
    }
  }

  private void updateConfiguration(String integrationUser) throws IntegrationConfigException {
    V1Configuration config =
        this.configService.getConfigurationByType(integrationUser, integrationUser);
    onConfigChange(config);
  }

  @Override
  public void onConfigChange(V1Configuration conf) {
    this.settings = new IntegrationSettings(conf);
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
   * @param instance Configuration instance that contains user settings
   * @param input Message received from the third party services
   * @return Formatted MessageML or null if the integration doesn't handle this specific event
   * @throws WebHookParseException Reports failure to validate message received from third party
   * services.
   */
  public String parse(ConfigurationInstance instance, WebHookPayload input)
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
  public String parse(WebHookPayload input) throws WebHookParseException {
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
      throws WebHookParseException {
    if (isAvailable()) {
      ConfigurationInstance instance = getConfigurationInstance(instanceId);
      String message = parseRequest(instance, integrationUser, input);
      if (message != null) {
        List<String> streams = streamService.getStreams(instance);
        postMessage(instance, integrationUser, streams, message);
      }
    }
  }

  /**
   * Wraps the parser execution and monitor the parser execution time.
   * @param instance Configuration instance
   * @param integrationUser Integration username
   * @param input Webhhok payload
   * @return Formatted MessageML or null if the integration doesn't handle this specific event
   */
  private String parseRequest(ConfigurationInstance instance, String integrationUser,
      WebHookPayload input) {
    Timer.Context context = null;
    boolean success = false;
    String message = null;

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
   * @param instance Configuration instance
   * @param integrationUser Integration username
   * @param payload Json Node that contains a list of streams
   * @throws IOException Reports failure to validate the payload
   */
  public void welcome(ConfigurationInstance instance, String integrationUser, String payload) {
    List<String> instanceStreams = streamService.getStreams(instance);
    List<String> streams = streamService.getStreams(payload);

    // Remove unexpected streams
    for (Iterator<String> it = streams.iterator(); it.hasNext();) {
      String stream = it.next();
      if (!instanceStreams.contains(stream)) {
        it.remove();
      }
    }

    if (streams.isEmpty()) {
      throw new StreamTypeNotFoundException("Cannot determine the stream type of the configuration instance");
    }

    String message = getWelcomeMessage(instance, integrationUser);

    if (!StringUtils.isEmpty(message)) {
      String formattedMessage = buildMessageML(message, WELCOME_EVENT);
      postMessage(instance, integrationUser, streams, formattedMessage);
    }
  }

  /**
   * Get the welcome message based on stream type configured in the instance.
   * @param instance Configuration instance
   * @param integrationUser Integration username
   * @return Welcome message
   */
  private String getWelcomeMessage(ConfigurationInstance instance, String integrationUser) {
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
   * @param instance Configuration instance
   * @param integrationUser Integration username
   * @param streams List of streams
   * @param message Formatted MessageML
   */
  private void postMessage(ConfigurationInstance instance, String integrationUser,
      List<String> streams, String message) {
    // Post a message
    V2MessageList response = sendMessage(instance, integrationUser, streams, message);
    // Get the timestamp of the last message posted
    Long lastPostedDate = getLastPostedDate(response);

    if (lastPostedDate > 0) {
      // Update the configuration instance
      updateLastPostedDate(instance, integrationUser, lastPostedDate);
    }
  }

  /**
   * Send messages to streams using Integration Bridge service.
   * @param instance Configuration instance
   * @param integrationUser Integration username
   * @param streams List of streams
   * @param message Formatted MessageML
   * @return List of messages posted to the streams.
   */
  private V2MessageList sendMessage(ConfigurationInstance instance, String integrationUser,
      List<String> streams, String message) {
    V2MessageList response = new V2MessageList();
    try {
      // Post a message
      response = bridge.sendMessage(instance, integrationUser, streams, message);
    } catch (ProcessingException e) {
      exceptionHandler.handleAuthException(e);
    }
    return response;
  }

  /**
   * Get the most recent posted date in the provided list of responses
   * @param response
   */
  private Long getLastPostedDate(V2MessageList response) {
    Long lastPostedDate = 0L;
    for (V2BaseMessage responseMessage : response) {
      // Get last posted date
      String timestamp = responseMessage.getTimestamp();
      lastPostedDate = Math.max(lastPostedDate, Long.valueOf(timestamp));
    }
    return lastPostedDate;
  }

  /**
   * Update the configuration instance with the last posted date.
   * @param instance Configuration instance
   * @param timestamp Last posted date
   */
  private void updateLastPostedDate(ConfigurationInstance instance, String integrationUser,
      Long timestamp) {
    try {
      // Update posted date
      ObjectNode whiConfigInstance =
          WebHookConfigurationUtils.fromJsonString(instance.getOptionalProperties());
      whiConfigInstance.put(LAST_POSTED_DATE, timestamp);
      instance.setOptionalProperties(WebHookConfigurationUtils.toJsonString(whiConfigInstance));
      configService.save(instance, integrationUser);

      healthManager.updateLatestPostTimestamp(timestamp);
    } catch (IntegrationRuntimeException | IOException e) {
      LOGGER.fatal("Fail to update the last posted date", e);
    }
  }

  /**
   * Builds a Symphony MessageML with a given formatted message.
   * @param formattedMessage a message properly formatted with Symphony-only tags and/or pure text.
   * @param webHookEvent the event being processed.
   * @return the Symphony MessageML message.
   */
  protected String buildMessageML(String formattedMessage, String webHookEvent) {
    if (formattedMessage != null && !formattedMessage.trim().isEmpty()) {
      return MESSAGEML_START + formattedMessage + MESSAGEML_END;
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
        V1Configuration whiConfiguration =
            this.configService.getConfigurationById(settings.getConfigurationId(), type);

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
   * Retrieve the configuration instance based on instanceId.
   * @param instanceId Configuration instance identifier
   * @return Configuration instance that contains information how to handle requests.
   */
  protected ConfigurationInstance getConfigurationInstance(String instanceId) {
    return configService.getInstanceById(settings.getConfigurationId(), instanceId,
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

}

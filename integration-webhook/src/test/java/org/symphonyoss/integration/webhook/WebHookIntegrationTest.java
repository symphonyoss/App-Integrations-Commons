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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.symphonyoss.integration.utils.WebHookConfigurationUtils.LAST_POSTED_DATE;
import static org.mockito.Matchers.anyListOf;

import com.symphony.api.agent.model.V2Message;
import com.symphony.api.agent.model.V2MessageList;
import com.symphony.api.pod.client.ApiException;
import com.symphony.api.pod.model.ConfigurationInstance;
import com.symphony.api.pod.model.V1Configuration;

import com.codahale.metrics.Timer;
import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.symphonyoss.integration.IntegrationStatus;
import org.symphonyoss.integration.MockKeystore;
import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.entity.model.User;
import org.symphonyoss.integration.exception.bootstrap.CertificateNotFoundException;
import org.symphonyoss.integration.exception.bootstrap.LoadKeyStoreException;
import org.symphonyoss.integration.exception.bootstrap.UnexpectedBootstrapException;
import org.symphonyoss.integration.exception.config.ForbiddenUserException;
import org.symphonyoss.integration.model.config.StreamType;
import org.symphonyoss.integration.model.healthcheck.IntegrationFlags;
import org.symphonyoss.integration.model.healthcheck.IntegrationHealth;
import org.symphonyoss.integration.model.yaml.Application;
import org.symphonyoss.integration.model.yaml.IntegrationProperties;
import org.symphonyoss.integration.service.ConfigurationService;
import org.symphonyoss.integration.service.IntegrationBridge;
import org.symphonyoss.integration.service.StreamService;
import org.symphonyoss.integration.service.UserService;
import org.symphonyoss.integration.utils.IntegrationUtils;
import org.symphonyoss.integration.utils.WebHookConfigurationUtils;
import org.symphonyoss.integration.webhook.exception.InvalidStreamTypeException;
import org.symphonyoss.integration.webhook.exception.StreamTypeNotFoundException;
import org.symphonyoss.integration.webhook.exception.WebHookDisabledException;
import org.symphonyoss.integration.webhook.exception.WebHookParseException;
import org.symphonyoss.integration.webhook.exception.WebHookUnavailableException;
import org.symphonyoss.integration.webhook.metrics.ParserMetricsController;

import java.io.IOException;
import java.net.ConnectException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ScheduledExecutorService;

import javax.ws.rs.ProcessingException;

/**
 * Test class responsible to test the flows in the {@link WebHookIntegration}.
 * Created by rsanchez on 06/05/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
@ContextConfiguration(classes = {IntegrationProperties.class, MockWebHookIntegration.class})
public class WebHookIntegrationTest extends MockKeystore {

  private static final String CONFIGURATION_ID = "57bf581ae4b079de6a1cbbf9";

  private static final String INTEGRATION_USER = "jiraWebHookIntegration";

  @MockBean
  private IntegrationBridge service;

  @MockBean
  private StreamService streamService;

  @MockBean(name = "remoteConfigurationService")
  private ConfigurationService configService;

  @MockBean
  private AuthenticationProxy authenticationProxy;

  @MockBean
  private WebHookExceptionHandler exceptionHandler;

  @MockBean
  private UserService userService;

  @Autowired
  private IntegrationProperties properties;

  @MockBean
  private ScheduledExecutorService scheduler;

  @MockBean
  private LoadingCache<String, IntegrationFlags.ValueEnum> configuratorFlagsCache;

  @MockBean
  private IntegrationUtils utils;

  @MockBean
  private ParserMetricsController metricsController;

  @Mock
  private Timer.Context context;

  @Autowired
  private MockWebHookIntegration mockWHI;

  @Before
  public void setup() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    Locale.setDefault(Locale.US);

    when(authenticationProxy.isAuthenticated(anyString())).thenReturn(true);

    V1Configuration configuration = new V1Configuration();
    configuration.setConfigurationId(CONFIGURATION_ID);
    configuration.setName("JIRA");
    configuration.setType(INTEGRATION_USER);
    configuration.setEnabled(true);

    mockWHI.onConfigChange(configuration);

    doAnswer(new Answer<StreamType>() {
      @Override
      public StreamType answer(InvocationOnMock invocationOnMock) throws Throwable {
        ConfigurationInstance instance = (ConfigurationInstance) invocationOnMock.getArguments()[0];
        return WebHookConfigurationUtils.getStreamType(instance.getOptionalProperties());
      }
    }).when(streamService).getStreams(any(ConfigurationInstance.class));

    doAnswer(new GetStreamTypeAnswer()).when(streamService)
        .getStreamType(any(ConfigurationInstance.class));
    doAnswer(new GetStreamsAnswer()).when(streamService)
        .getStreams(any(ConfigurationInstance.class));
    doAnswer(new GetStreamsAnswer()).when(streamService).getStreams(any(String.class));

    doReturn(context).when(metricsController).startParserExecution(INTEGRATION_USER);
  }

  @Test
  public void testOnCreateRuntimeException() {
    try {
      doThrow(RuntimeException.class).when(utils).getCertsDirectory();

      mockWHI.onConfigChange(null);
      mockWHI.onCreate(INTEGRATION_USER);
    } catch (UnexpectedBootstrapException e) {
      assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(),
          mockWHI.getHealthStatus().getStatus());
    }
  }

  @Test
  public void testOnCreateCertNotFoundException() {
    try {
      doThrow(CertificateNotFoundException.class).when(utils).getCertsDirectory();

      mockWHI.onConfigChange(null);
      mockWHI.onCreate(INTEGRATION_USER);
    } catch (CertificateNotFoundException e) {
      IntegrationHealth health  = mockWHI.getHealthStatus();
      assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), health.getStatus());
      assertEquals(IntegrationFlags.ValueEnum.NOK, health.getFlags().getCertificateInstalled());
    }
  }

  @Test
  public void testOnCreateLoadKeystoreException() throws IOException {
    try {
      String certDir = mockCertDir();
      doReturn(certDir).when(utils).getCertsDirectory();

      mockWHI.onConfigChange(null);
      mockWHI.onCreate(INTEGRATION_USER);
    } catch (LoadKeyStoreException e) {
      IntegrationHealth health  = mockWHI.getHealthStatus();
      assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), health.getStatus());
      assertEquals(IntegrationFlags.ValueEnum.NOK, health.getFlags().getCertificateInstalled());
    }
  }

  @Test
  public void testOnCreateCertificateInstalled()
      throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
    String certDir = mockKeyStore();
    doReturn(certDir).when(utils).getCertsDirectory();

    mockWHI.onCreate(INTEGRATION_USER);

    IntegrationHealth health  = mockWHI.getHealthStatus();
    assertEquals(IntegrationStatus.ACTIVE.name(), health.getStatus());
    assertEquals(IntegrationFlags.ValueEnum.OK, health.getFlags().getCertificateInstalled());
  }

  @Test
  public void testHandleWithUpdateTimestamp()
      throws WebHookParseException, IOException {
    doReturn(mockWHI.getConfig()).when(configService)
        .getConfigurationById(CONFIGURATION_ID, INTEGRATION_USER);

    V2MessageList response = new V2MessageList();
    V2Message message1 = new V2Message();
    V2Message message2 = new V2Message();

    Long timestamp1 = 1476109880000L;
    Long timestamp2 = timestamp1 + 1000;

    message1.setTimestamp(timestamp1.toString());
    message2.setTimestamp(timestamp2.toString());

    response.add(message1);
    response.add(message2);

    when(service.sendMessage(any(ConfigurationInstance.class), anyString(),
        anyListOf(String.class),
        anyString())).thenReturn(response);

    Long optionalPropertiesTimestamp = timestamp1 - 1000;
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

    ConfigurationInstance instance = new ConfigurationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    doReturn(instance).when(configService).getInstanceById(anyString(), anyString(), anyString());

    mockWHI.handle(instance.getInstanceId(), INTEGRATION_USER,
        new WebHookPayload(Collections.<String, String>emptyMap(), Collections.<String, String>emptyMap(), "{ \"webhookEvent\": \"mock\" }"));

    Long lastPostedDate = WebHookConfigurationUtils.fromJsonString(instance.getOptionalProperties())
        .path(LAST_POSTED_DATE).asLong();

    assertEquals(timestamp2, lastPostedDate);

    mockWHI.onConfigChange(null);

    IntegrationHealth integrationHealth = mockWHI.getHealthStatus();
    assertEquals("2016-10-10T14:31:21Z+0000", integrationHealth.getLatestPostTimestamp());

    verify(metricsController, times(1)).startParserExecution(INTEGRATION_USER);
    verify(metricsController, times(1)).finishParserExecution(context, INTEGRATION_USER, true);
  }

  @Test
  public void testHandleFailedSend() throws WebHookParseException, IOException {
    doReturn(mockWHI.getConfig()).when(configService)
        .getConfigurationById(CONFIGURATION_ID, INTEGRATION_USER);

    doReturn(new V2MessageList()).when(service)
        .sendMessage(any(ConfigurationInstance.class), anyString(),
            anyListOf(String.class), anyString());

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

    ConfigurationInstance instance = new ConfigurationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    doReturn(instance).when(configService).getInstanceById(anyString(), anyString(), anyString());

    mockWHI.handle(instance.getInstanceId(), INTEGRATION_USER,
        new WebHookPayload(Collections.<String, String>emptyMap(), Collections.<String, String>emptyMap(), "{ \"webhookEvent\": \"mock\" }"));

    Long lastPostedDate = WebHookConfigurationUtils.fromJsonString(instance.getOptionalProperties())
        .path(LAST_POSTED_DATE).asLong();

    // no update to lastPostedDate
    assertEquals(optionalPropertiesTimestamp, lastPostedDate);

    mockWHI.onConfigChange(null);

    IntegrationHealth integrationHealth = mockWHI.getHealthStatus();
    assertNull(integrationHealth.getLatestPostTimestamp());

    verify(metricsController, times(1)).startParserExecution(INTEGRATION_USER);
    verify(metricsController, times(1)).finishParserExecution(context, INTEGRATION_USER, true);
  }

  @Test
  public void testHandleSocketException() throws WebHookParseException, IOException {
    ProcessingException exception = new ProcessingException(new ConnectException());
    doThrow(exception).when(service)
        .sendMessage(any(ConfigurationInstance.class), anyString(),
            anyListOf(String.class), anyString());

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

    ConfigurationInstance instance = new ConfigurationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    V1Configuration configuration = mock(V1Configuration.class);
    doReturn(true).when(configuration).getEnabled();

    doReturn(instance).when(configService).getInstanceById(anyString(), anyString(), anyString());
    doReturn(configuration).when(configService).getConfigurationById(anyString(), anyString());

    mockWHI.handle("1234", INTEGRATION_USER,
        new WebHookPayload(Collections.<String, String>emptyMap(), Collections.<String, String>emptyMap(), "{ \"webhookEvent\": \"mock\" }"));

    Long lastPostedDate = WebHookConfigurationUtils.fromJsonString(instance.getOptionalProperties())
        .path(LAST_POSTED_DATE).asLong();

    // no update to lastPostedDate
    assertEquals(optionalPropertiesTimestamp, lastPostedDate);

    mockWHI.onConfigChange(null);

    IntegrationHealth integrationHealth = mockWHI.getHealthStatus();
    assertNull(integrationHealth.getLatestPostTimestamp());

    verify(metricsController, times(1)).startParserExecution(INTEGRATION_USER);
    verify(metricsController, times(1)).finishParserExecution(context, INTEGRATION_USER, true);
  }

  @Test(expected = StreamTypeNotFoundException.class)
  public void testWelcomeInvalidPayload() throws IOException {
    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(ConfigurationInstance.class), anyString(),
        anyListOf(String.class), anyString());

    mockWHI.welcome(new ConfigurationInstance(), INTEGRATION_USER, "");
  }

  @Test(expected = StreamTypeNotFoundException.class)
  public void testWelcomeEmptyPayload() throws IOException {
    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(ConfigurationInstance.class), anyString(),
        anyListOf(String.class), anyString());

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

    ConfigurationInstance instance = new ConfigurationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    mockWHI.welcome(instance, INTEGRATION_USER, "{}");
  }

  @Test(expected = StreamTypeNotFoundException.class)
  public void testWelcomeInvalidStreams() throws IOException {
    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(ConfigurationInstance.class), anyString(),
        anyListOf(String.class), anyString());

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

    ConfigurationInstance instance = new ConfigurationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    mockWHI.welcome(instance, INTEGRATION_USER, "{ \"streams\": [ \"stream3\", \"stream4\"] }");
  }

  @Test(expected = InvalidStreamTypeException.class)
  public void testWelcomeEmptyStreamType() throws IOException {
    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(ConfigurationInstance.class), anyString(),
        anyListOf(String.class), anyString());

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

    ConfigurationInstance instance = new ConfigurationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    mockWHI.welcome(instance, INTEGRATION_USER, "{ \"streams\": [ \"stream1\" ] }");
  }

  @Test
  public void testWelcomeIMStreamType() throws IOException {
    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(ConfigurationInstance.class), anyString(),
        anyListOf(String.class), anyString());

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"], \"streamType\": "
        + "\"IM\" }";

    ConfigurationInstance instance = new ConfigurationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    mockWHI.welcome(instance, INTEGRATION_USER, "{ \"streams\": [ \"stream1\", \"stream3\" ] }");

    assertEquals(
        "<messageML>Hi there. This is the JIRA application. I'll let you know of any new events "
            + "sent from the JIRA integration you configured.</messageML>",
        answer.getMessage());
    assertEquals(1, answer.getCount());
  }

  @Test
  public void testWelcomeChatroomStreamType() throws IOException, ApiException {
    User user = new User();
    user.setDisplayName("Test user");

    when(authenticationProxy.getSessionToken(anyString())).thenReturn("");
    when(userService.getUserByUserId(anyString(), eq(7890L))).thenReturn(user);

    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(ConfigurationInstance.class), anyString(),
        anyListOf(String.class), anyString());

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"], \"streamType\": "
        + "\"CHATROOM\" }";

    ConfigurationInstance instance = new ConfigurationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setCreatorId("7890");
    instance.setOptionalProperties(optionalProperties);

    mockWHI.welcome(instance, INTEGRATION_USER, "{ \"streams\": [ \"stream1\", \"stream3\", "
        + "\"stream2\" ] }");

    assertEquals(
        "<messageML>Hi there. This is the JIRA application. I'll let you know of any new events "
            + "sent from the JIRA integration configured by Test user.</messageML>",
        answer.getMessage());
    assertEquals(2, answer.getCount());
  }

  @Test
  public void testWelcomeChatroomWithoutUserStreamType() throws IOException {
    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(ConfigurationInstance.class), anyString(),
        anyListOf(String.class), anyString());

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"], \"streamType\": "
        + "\"CHATROOM\" }";

    ConfigurationInstance instance = new ConfigurationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    mockWHI.welcome(instance, INTEGRATION_USER, "{ \"streams\": [ \"stream1\", \"stream2\" ] }");

    assertEquals(
        "<messageML>Hi there. This is the JIRA application. I'll let you know of any new events "
            + "sent from the JIRA integration configured by UNKNOWN.</messageML>",
        answer.getMessage());
    assertEquals(2, answer.getCount());
  }

  @Test(expected = WebHookDisabledException.class)
  public void testUnavailable() {
    V1Configuration config = mockWHI.getConfig();
    config.setEnabled(false);

    doReturn(mockWHI.getConfig()).when(configService)
        .getConfigurationById(CONFIGURATION_ID, INTEGRATION_USER);
    doReturn(IntegrationFlags.ValueEnum.NOK).when(configuratorFlagsCache).getUnchecked
        (INTEGRATION_USER);

    mockWHI.isAvailable();
  }

  @Test(expected = WebHookUnavailableException.class)
  public void testUnavailableForbidden() {
    doThrow(ForbiddenUserException.class).when(configService)
        .getConfigurationById(CONFIGURATION_ID, INTEGRATION_USER);
    doReturn(IntegrationFlags.ValueEnum.NOK).when(configuratorFlagsCache).getUnchecked
        (INTEGRATION_USER);

    mockWHI.isAvailable();
  }

  @Test
  public void testAvailable() {
    doReturn(mockWHI.getConfig()).when(configService)
        .getConfigurationById(CONFIGURATION_ID, INTEGRATION_USER);

    assertTrue(mockWHI.isAvailable());
  }

  @Test
  public void testWhiteList() {
    Set<String> integrationWhiteList = mockWHI.getIntegrationWhiteList();
    assertNotNull(integrationWhiteList);
    assertEquals(3, integrationWhiteList.size());

    assertTrue(integrationWhiteList.contains("squid-104-1.sc1.uc-inf.net"));
    assertTrue(integrationWhiteList.contains("165.254.226.119"));
    assertTrue(integrationWhiteList.contains("107.23.104.115"));

    properties.setApplications(Collections.<String, Application>emptyMap());

    integrationWhiteList = mockWHI.getIntegrationWhiteList();
    assertNotNull(integrationWhiteList);
    assertTrue(integrationWhiteList.isEmpty());
  }

  public static final class SendMessageAnswer implements Answer<V2MessageList> {

    private String message;

    private int count;

    @Override
    @SuppressWarnings("unchecked")
    public V2MessageList answer(InvocationOnMock invocationOnMock) throws Throwable {
      List<String> streams = (List<String>) invocationOnMock.getArguments()[2];
      this.message = (String) invocationOnMock.getArguments()[3];
      this.count = streams.size();
      return new V2MessageList();
    }

    public String getMessage() {
      return message;
    }

    public int getCount() {
      return count;
    }

  }

  public static final class GetStreamTypeAnswer implements Answer<StreamType> {

    @Override
    public StreamType answer(InvocationOnMock invocationOnMock) throws Throwable {
      ConfigurationInstance instance = (ConfigurationInstance) invocationOnMock.getArguments()[0];
      return WebHookConfigurationUtils.getStreamType(instance.getOptionalProperties());
    }

  }

  public static final class GetStreamsAnswer implements Answer<List<String>> {

    @Override
    public List<String> answer(InvocationOnMock invocationOnMock) throws Throwable {
      String optionalProperties;
      Object firstParam = invocationOnMock.getArguments()[0];

      if (firstParam instanceof ConfigurationInstance) {
        ConfigurationInstance instance = (ConfigurationInstance) firstParam;
        optionalProperties = instance.getOptionalProperties();
      } else {
        optionalProperties = (String) firstParam;
      }

      try {
        return WebHookConfigurationUtils.getStreams(optionalProperties);
      } catch (IOException e) {
        return Collections.emptyList();
      }
    }

  }
}

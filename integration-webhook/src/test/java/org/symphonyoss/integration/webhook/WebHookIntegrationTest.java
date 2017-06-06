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
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.symphonyoss.integration.utils.WebHookConfigurationUtils.LAST_POSTED_DATE;

import com.codahale.metrics.MetricRegistry;
import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.symphonyoss.integration.IntegrationStatus;
import org.symphonyoss.integration.MockKeystore;
import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.entity.model.User;
import org.symphonyoss.integration.exception.RemoteApiException;
import org.symphonyoss.integration.exception.bootstrap.CertificateNotFoundException;
import org.symphonyoss.integration.exception.bootstrap.LoadKeyStoreException;
import org.symphonyoss.integration.exception.bootstrap.UnexpectedBootstrapException;
import org.symphonyoss.integration.exception.config.ForbiddenUserException;
import org.symphonyoss.integration.model.config.IntegrationInstance;
import org.symphonyoss.integration.model.config.IntegrationSettings;
import org.symphonyoss.integration.model.healthcheck.IntegrationFlags;
import org.symphonyoss.integration.model.healthcheck.IntegrationHealth;
import org.symphonyoss.integration.model.message.Message;
import org.symphonyoss.integration.model.stream.StreamType;
import org.symphonyoss.integration.model.yaml.IntegrationProperties;
import org.symphonyoss.integration.service.IntegrationBridge;
import org.symphonyoss.integration.service.IntegrationService;
import org.symphonyoss.integration.service.StreamService;
import org.symphonyoss.integration.service.UserService;
import org.symphonyoss.integration.utils.IntegrationUtils;
import org.symphonyoss.integration.utils.WebHookConfigurationUtils;
import org.symphonyoss.integration.webhook.exception.InvalidStreamTypeException;
import org.symphonyoss.integration.webhook.exception.StreamTypeNotFoundException;
import org.symphonyoss.integration.webhook.exception.WebHookDisabledException;
import org.symphonyoss.integration.webhook.exception.WebHookParseException;
import org.symphonyoss.integration.webhook.exception.WebHookUnavailableException;

import java.io.IOException;
import java.net.ConnectException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
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
@ContextConfiguration(classes = {IntegrationProperties.class, MockWebHookIntegration.class,
    MockIntegrationHealthManager.class, V2MockWebHookIntegration.class,
    MockParserMetricsController.class, MetricRegistry.class})
public class WebHookIntegrationTest extends MockKeystore {

  private static final String CONFIGURATION_ID = "57bf581ae4b079de6a1cbbf9";

  private static final String INSTANCE_ID = "592c7696e4b0402b2a03ce5b";

  private static final String INTEGRATION_USER = "jiraWebHookIntegration";

  private static final String MOCK_USERID = "98568743";

  private static final String XML_WITH_PROLOG =
      "<?xml version =\"1.0\" encoding=\"UTF-8\"?><mention"
          + " email=\"rsanchez@symphony.com\"/> created SAM-25 "
          + "(<a href=\"https://whiteam1.atlassian.net/browse/SAM-25\"/>) "
          + "(<b>Highest</b> Story in Sample 1 with labels &quot;production&quot;)"
          + "<br/>Description: Issue "
          + "Test<br/>Assignee: <mention email=\"rsanchez@symphony.com\"/>";

  private static final String VALID_MESSAGEML = "<messageML><mention email=\"rsanchez@symphony"
      + ".com\"/> created SAM-25 (<a href=\"https://whiteam1.atlassian.net/browse/SAM-25\"/>) "
      + "(<b>Highest</b> Story in Sample 1 with labels &quot;production&quot;)<br/>Description: "
      + "Issue "
      + "Test<br/>Assignee: <mention email=\"rsanchez@symphony.com\"/></messageML>";

  @MockBean
  private IntegrationBridge service;

  @MockBean
  private StreamService streamService;

  @MockBean(name = "remoteIntegrationService")
  private IntegrationService integrationService;

  @MockBean
  private AuthenticationProxy authenticationProxy;

  @MockBean
  private UserService userService;

  @SpyBean
  private IntegrationProperties properties;

  @MockBean
  private ScheduledExecutorService scheduler;

  @MockBean
  private LoadingCache<String, IntegrationFlags.ValueEnum> configuratorFlagsCache;

  @MockBean
  private IntegrationUtils utils;

  @Autowired
  private MockParserMetricsController metricsController;

  @Autowired
  private MockWebHookIntegration mockWHI;

  @Autowired
  private V2MockWebHookIntegration v2MockWHI;

  private IntegrationSettings settings;

  @Before
  public void setup() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    Locale.setDefault(Locale.US);

    when(authenticationProxy.isAuthenticated(anyString())).thenReturn(true);

    settings = new IntegrationSettings();
    settings.setConfigurationId(CONFIGURATION_ID);
    settings.setName("JIRA");
    settings.setType(INTEGRATION_USER);
    settings.setEnabled(true);

    mockWHI.onConfigChange(settings);
    v2MockWHI.onConfigChange(settings);

    doReturn(settings).when(integrationService)
        .getIntegrationByType(INTEGRATION_USER, INTEGRATION_USER);

    doAnswer(new Answer<StreamType>() {
      @Override
      public StreamType answer(InvocationOnMock invocationOnMock) throws Throwable {
        IntegrationInstance instance = (IntegrationInstance) invocationOnMock.getArguments()[0];
        return WebHookConfigurationUtils.getStreamType(instance.getOptionalProperties());
      }
    }).when(streamService).getStreams(any(IntegrationInstance.class));

    doAnswer(new GetStreamTypeAnswer()).when(streamService)
        .getStreamType(any(IntegrationInstance.class));
    doAnswer(new GetStreamsAnswer()).when(streamService)
        .getStreams(any(IntegrationInstance.class));
    doAnswer(new GetStreamsAnswer()).when(streamService).getStreams(any(String.class));
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
      IntegrationHealth health = mockWHI.getHealthStatus();
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
      IntegrationHealth health = mockWHI.getHealthStatus();
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

    IntegrationHealth health = mockWHI.getHealthStatus();
    assertEquals(IntegrationStatus.ACTIVE.name(), health.getStatus());
    assertEquals(IntegrationFlags.ValueEnum.OK, health.getFlags().getCertificateInstalled());
  }

  @Test
  public void testHandleWithUpdateTimestamp()
      throws WebHookParseException, IOException, RemoteApiException {
    doReturn(settings).when(integrationService)
        .getIntegrationById(CONFIGURATION_ID, INTEGRATION_USER);

    List<Message> response = new ArrayList<>();
    Message message1 = new Message();
    Message message2 = new Message();

    Long timestamp1 = 1476109880000L;
    Long timestamp2 = timestamp1 + 1000;

    message1.setTimestamp(timestamp1);
    message2.setTimestamp(timestamp2);

    response.add(message1);
    response.add(message2);

    when(service.sendMessage(any(IntegrationInstance.class), anyString(), anyListOf(String.class),
        any(Message.class))).thenReturn(response);

    Long optionalPropertiesTimestamp = timestamp1 - 1000;
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

    IntegrationInstance instance = new IntegrationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    doReturn(instance).when(integrationService)
        .getInstanceById(anyString(), anyString(), anyString());

    mockWHI.handle(instance.getInstanceId(), INTEGRATION_USER,
        new WebHookPayload(Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), "{ \"webhookEvent\": \"mock\" }"));

    Long lastPostedDate = WebHookConfigurationUtils.fromJsonString(instance.getOptionalProperties())
        .path(LAST_POSTED_DATE).asLong();

    assertEquals(timestamp2, lastPostedDate);

    mockWHI.onConfigChange(null);

    IntegrationHealth integrationHealth = mockWHI.getHealthStatus();
    assertEquals("2016-10-10T14:31:21Z+0000", integrationHealth.getLatestPostTimestamp());

    assertTrue(metricsController.isSuccess());
  }

  @Test
  public void testHandleFailedSend() throws WebHookParseException, IOException, RemoteApiException {
    doReturn(settings).when(integrationService)
        .getIntegrationById(CONFIGURATION_ID, INTEGRATION_USER);

    doReturn(new ArrayList<Message>()).when(service)
        .sendMessage(any(IntegrationInstance.class), anyString(), anyListOf(String.class),
            any(Message.class));

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

    IntegrationInstance instance = new IntegrationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    doReturn(instance).when(integrationService)
        .getInstanceById(anyString(), anyString(), anyString());

    mockWHI.handle(instance.getInstanceId(), INTEGRATION_USER,
        new WebHookPayload(Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), "{ \"webhookEvent\": \"mock\" }"));

    Long lastPostedDate = WebHookConfigurationUtils.fromJsonString(instance.getOptionalProperties())
        .path(LAST_POSTED_DATE).asLong();

    // no update to lastPostedDate
    assertEquals(optionalPropertiesTimestamp, lastPostedDate);

    mockWHI.onConfigChange(null);

    IntegrationHealth integrationHealth = mockWHI.getHealthStatus();
    assertNull(integrationHealth.getLatestPostTimestamp());

    assertTrue(metricsController.isSuccess());
  }

  @Test
  public void testHandleSocketException()
      throws WebHookParseException, IOException, RemoteApiException {
    ProcessingException exception = new ProcessingException(new ConnectException());
    doThrow(exception).when(service)
        .sendMessage(any(IntegrationInstance.class), anyString(), anyListOf(String.class),
            any(Message.class));

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

    IntegrationInstance instance = new IntegrationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    IntegrationSettings settings = mock(IntegrationSettings.class);
    doReturn(true).when(settings).getEnabled();

    doReturn(instance).when(integrationService)
        .getInstanceById(anyString(), anyString(), anyString());
    doReturn(settings).when(integrationService).getIntegrationById(anyString(), anyString());

    try {
      mockWHI.handle("1234", INTEGRATION_USER,
          new WebHookPayload(Collections.<String, String>emptyMap(),
              Collections.<String, String>emptyMap(), "{ \"webhookEvent\": \"mock\" }"));
      fail();
    } catch (ProcessingException e) {

      Long lastPostedDate = WebHookConfigurationUtils.fromJsonString(instance.getOptionalProperties())
          .path(LAST_POSTED_DATE).asLong();

      // no update to lastPostedDate
      assertEquals(optionalPropertiesTimestamp, lastPostedDate);

      mockWHI.onConfigChange(null);

      IntegrationHealth integrationHealth = mockWHI.getHealthStatus();
      assertNull(integrationHealth.getLatestPostTimestamp());

      assertTrue(metricsController.isSuccess());
    }
  }

  @Test(expected = StreamTypeNotFoundException.class)
  public void testWelcomeInvalidPayload() throws IOException, RemoteApiException {
    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service)
        .sendMessage(any(IntegrationInstance.class), anyString(), anyListOf(String.class),
            any(Message.class));

    mockWHI.welcome(new IntegrationInstance(), INTEGRATION_USER, "");
  }

  @Test(expected = StreamTypeNotFoundException.class)
  public void testWelcomeEmptyPayload() throws IOException, RemoteApiException {
    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(IntegrationInstance.class), anyString(),
        anyListOf(String.class), any(Message.class));

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

    IntegrationInstance instance = new IntegrationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    mockWHI.welcome(instance, INTEGRATION_USER, "{}");
  }

  @Test(expected = StreamTypeNotFoundException.class)
  public void testWelcomeInvalidStreams() throws IOException, RemoteApiException {
    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(IntegrationInstance.class), anyString(),
        anyListOf(String.class), any(Message.class));

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

    IntegrationInstance instance = new IntegrationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    mockWHI.welcome(instance, INTEGRATION_USER, "{ \"streams\": [ \"stream3\", \"stream4\"] }");
  }

  @Test(expected = InvalidStreamTypeException.class)
  public void testWelcomeEmptyStreamType() throws IOException, RemoteApiException {
    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(IntegrationInstance.class), anyString(),
        anyListOf(String.class), any(Message.class));

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

    IntegrationInstance instance = new IntegrationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    mockWHI.welcome(instance, INTEGRATION_USER, "{ \"streams\": [ \"stream1\" ] }");
  }

  @Test
  public void testWelcomeIMStreamType() throws IOException, RemoteApiException {
    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(IntegrationInstance.class), anyString(),
        anyListOf(String.class), any(Message.class));

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"], \"streamType\": "
        + "\"IM\" }";

    IntegrationInstance instance = new IntegrationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    mockWHI.welcome(instance, INTEGRATION_USER, "{ \"streams\": [ \"stream1\", \"stream3\" ] }");

    assertEquals(
        "<messageML>Hi there. This is the JIRA application. I'll let you know of any new events "
            + "sent from the JIRA integration you configured.</messageML>",
        answer.getFormattedMessage());
    assertEquals(1, answer.getCount());
  }

  @Test
  public void testWelcomeChatroomStreamType() throws IOException, RemoteApiException {
    User user = new User();
    user.setDisplayName("Test user");

    when(authenticationProxy.getSessionToken(anyString())).thenReturn("");
    when(userService.getUserByUserId(anyString(), eq(7890L))).thenReturn(user);

    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(IntegrationInstance.class), anyString(),
        anyListOf(String.class), any(Message.class));

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"], \"streamType\": "
        + "\"CHATROOM\" }";

    IntegrationInstance instance = new IntegrationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setCreatorId("7890");
    instance.setOptionalProperties(optionalProperties);

    mockWHI.welcome(instance, INTEGRATION_USER, "{ \"streams\": [ \"stream1\", \"stream3\", "
        + "\"stream2\" ] }");

    assertEquals(
        "<messageML>Hi there. This is the JIRA application. I'll let you know of any new events "
            + "sent from the JIRA integration configured by Test user.</messageML>",
        answer.getFormattedMessage());
    assertEquals(2, answer.getCount());
  }

  @Test
  public void testWelcomeChatroomWithoutUserStreamType() throws IOException, RemoteApiException {
    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(IntegrationInstance.class), anyString(),
        anyListOf(String.class), any(Message.class));

    Long optionalPropertiesTimestamp = System.currentTimeMillis();
    String optionalProperties = "{ \"lastPostedDate\": " + optionalPropertiesTimestamp
        + ", \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"], \"streamType\": "
        + "\"CHATROOM\" }";

    IntegrationInstance instance = new IntegrationInstance();
    instance.setConfigurationId("jirawebhook");
    instance.setInstanceId("1234");
    instance.setOptionalProperties(optionalProperties);

    mockWHI.welcome(instance, INTEGRATION_USER, "{ \"streams\": [ \"stream1\", \"stream2\" ] }");

    assertEquals(
        "<messageML>Hi there. This is the JIRA application. I'll let you know of any new events "
            + "sent from the JIRA integration configured by UNKNOWN.</messageML>",
        answer.getFormattedMessage());
    assertEquals(2, answer.getCount());
  }

  @Test(expected = WebHookDisabledException.class)
  public void testUnavailable() {
    settings.setEnabled(false);

    doReturn(settings).when(integrationService)
        .getIntegrationById(CONFIGURATION_ID, INTEGRATION_USER);
    doReturn(IntegrationFlags.ValueEnum.NOK).when(configuratorFlagsCache)
        .getUnchecked(INTEGRATION_USER);

    mockWHI.isAvailable();
  }

  @Test(expected = WebHookUnavailableException.class)
  public void testUnavailableForbidden() {
    doThrow(ForbiddenUserException.class).when(integrationService)
        .getIntegrationById(CONFIGURATION_ID, INTEGRATION_USER);
    doReturn(IntegrationFlags.ValueEnum.NOK).when(configuratorFlagsCache).getUnchecked
        (INTEGRATION_USER);

    mockWHI.isAvailable();
  }

  @Test
  public void testAvailable() {
    doReturn(settings).when(integrationService)
        .getIntegrationById(CONFIGURATION_ID, INTEGRATION_USER);
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

    given(properties.getApplication(INTEGRATION_USER)).willReturn(null);

    integrationWhiteList = mockWHI.getIntegrationWhiteList();
    assertNotNull(integrationWhiteList);
    assertTrue(integrationWhiteList.isEmpty());
  }

  @Test
  public void testMessageMLWithProlog() throws IOException {
    String rawMessage = XML_WITH_PROLOG;
    String expected = VALID_MESSAGEML;
    Message result = mockWHI.buildMessageML(rawMessage, "");
    assertEquals(expected, result.getMessage());
  }

  @Test
  public void testHandleMMLV2WithoutCreatorInfo() throws RemoteApiException, IOException {
    doReturn(settings).when(integrationService).getIntegrationById(CONFIGURATION_ID, INTEGRATION_USER);

    WebHookPayload payload = new WebHookPayload(Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), "{ \"message\": \"mockMessage\" }");

    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(IntegrationInstance.class), anyString(),
        anyListOf(String.class), any(Message.class));

    IntegrationInstance instance = mockInstance();
    doReturn(instance).when(integrationService).getInstanceById(CONFIGURATION_ID, INSTANCE_ID, INTEGRATION_USER);

    v2MockWHI.handle(instance.getInstanceId(), INTEGRATION_USER, payload);

    assertEquals("<messageML>mockMessage</messageML>", answer.getFormattedMessage());
    assertNull(answer.getMessage().getData());
  }

  private IntegrationInstance mockInstance() {
    IntegrationInstance instance = new IntegrationInstance();
    instance.setConfigurationId(CONFIGURATION_ID);
    instance.setInstanceId(INSTANCE_ID);
    instance.setOptionalProperties("{}");

    return instance;
  }

  @Test
  public void testHandleMMLV2FailedSetOwnership() throws RemoteApiException, IOException {
    doReturn(settings).when(integrationService).getIntegrationById(CONFIGURATION_ID, INTEGRATION_USER);

    WebHookPayload payload = new WebHookPayload(Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), "{ \"message\": \"mockMessage\", \"data\": \"mockData\" }");

    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(IntegrationInstance.class), anyString(),
        anyListOf(String.class), any(Message.class));

    IntegrationInstance instance = mockInstance();
    instance.setCreatorId(MOCK_USERID);

    doReturn(instance).when(integrationService).getInstanceById(CONFIGURATION_ID, INSTANCE_ID, INTEGRATION_USER);

    v2MockWHI.handle(instance.getInstanceId(), INTEGRATION_USER, payload);

    assertEquals("<messageML>mockMessage</messageML>", answer.getFormattedMessage());
    assertEquals("mockData", answer.getMessage().getData());
  }

  @Test
  public void testHandleMMLV2EmptyData() throws RemoteApiException, IOException {
    doReturn(settings).when(integrationService).getIntegrationById(CONFIGURATION_ID, INTEGRATION_USER);

    WebHookPayload payload = new WebHookPayload(Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), "{ \"message\": \"mockMessage\" }");

    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(IntegrationInstance.class), anyString(),
        anyListOf(String.class), any(Message.class));

    IntegrationInstance instance = mockInstance();
    instance.setCreatorName(INTEGRATION_USER);

    doReturn(instance).when(integrationService).getInstanceById(CONFIGURATION_ID, INSTANCE_ID, INTEGRATION_USER);

    v2MockWHI.handle(instance.getInstanceId(), INTEGRATION_USER, payload);

    assertEquals("<messageML>mockMessage</messageML>", answer.getFormattedMessage());
    assertEquals("{\"Ownership\":{\"username\":\"jiraWebHookIntegration\"}}",
        answer.getMessage().getData());
  }

  @Test
  public void testHandleMMLV2() throws RemoteApiException, IOException {
    doReturn(settings).when(integrationService).getIntegrationById(CONFIGURATION_ID, INTEGRATION_USER);

    WebHookPayload payload = new WebHookPayload(Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(),
        "{ \"message\": \"mockMessage\", \"data\": { \"event\": \"mockEvent\" } }");

    SendMessageAnswer answer = new SendMessageAnswer();
    doAnswer(answer).when(service).sendMessage(any(IntegrationInstance.class), anyString(),
        anyListOf(String.class), any(Message.class));

    IntegrationInstance instance = mockInstance();
    instance.setCreatorId(MOCK_USERID);
    instance.setCreatorName(INTEGRATION_USER);

    doReturn(instance).when(integrationService).getInstanceById(CONFIGURATION_ID, INSTANCE_ID, INTEGRATION_USER);

    v2MockWHI.handle(instance.getInstanceId(), INTEGRATION_USER, payload);

    assertEquals(
        "{\"event\":\"mockEvent\",\"Ownership\":{\"userId\":\"98568743\","
            + "\"username\":\"jiraWebHookIntegration\"}}",
        answer.getMessage().getData());
  }

  public static final class SendMessageAnswer implements Answer<List<Message>> {

    private Message message;

    private int count;

    @Override
    @SuppressWarnings("unchecked")
    public List<Message> answer(InvocationOnMock invocationOnMock) throws Throwable {
      List<String> streams = (List<String>) invocationOnMock.getArguments()[2];
      this.message = (Message) invocationOnMock.getArguments()[3];
      this.count = streams.size();

      if (this.message.getTimestamp() == null) {
        this.message.setTimestamp(System.currentTimeMillis());
      }

      return Arrays.asList(message);
    }

    public Message getMessage() {
      return message;
    }

    public String getFormattedMessage() {
      return message.getMessage();
    }

    public int getCount() {
      return count;
    }

  }


  public static final class GetStreamTypeAnswer implements Answer<StreamType> {

    @Override
    public StreamType answer(InvocationOnMock invocationOnMock) throws Throwable {
      IntegrationInstance instance = (IntegrationInstance) invocationOnMock.getArguments()[0];
      return WebHookConfigurationUtils.getStreamType(instance.getOptionalProperties());
    }

  }


  public static final class GetStreamsAnswer implements Answer<List<String>> {

    @Override
    public List<String> answer(InvocationOnMock invocationOnMock) throws Throwable {
      String optionalProperties;
      Object firstParam = invocationOnMock.getArguments()[0];

      if (firstParam instanceof IntegrationInstance) {
        IntegrationInstance instance = (IntegrationInstance) firstParam;
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

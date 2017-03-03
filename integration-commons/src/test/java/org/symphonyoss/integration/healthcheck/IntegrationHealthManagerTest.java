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

package org.symphonyoss.integration.healthcheck;

import static javax.ws.rs.core.MediaType.WILDCARD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.IntegrationStatus;
import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.model.config.IntegrationSettings;
import org.symphonyoss.integration.model.healthcheck.IntegrationFlags;
import org.symphonyoss.integration.model.healthcheck.IntegrationHealth;
import org.symphonyoss.integration.model.yaml.Application;
import org.symphonyoss.integration.model.yaml.IntegrationBridge;
import org.symphonyoss.integration.model.yaml.IntegrationProperties;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Unit test for {@link IntegrationHealthManager}
 * Created by rsanchez on 05/08/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class IntegrationHealthManagerTest {

  private static final String APP_ID = "jira";

  private static final String INTEGRATION_NAME = "jiraWebHookIntegration";

  private static final String NOT_AVAILABLE = "N/A";

  private static final String MOCK_VERSION = "0.0.1";

  private static final String OUT_OF_SERVICE = "Integration Out of Service. Please, check the flags";

  private static final String SUCCESS = "Success";

  private static final String MOCK_HOST = "test.symphony.com";

  @Spy
  private IntegrationProperties properties = new IntegrationProperties();

  @Mock
  private AuthenticationProxy authenticationProxy;

  @Mock
  private Client client;

  @InjectMocks
  private IntegrationHealthManager healthManager = new IntegrationHealthManager();

  @Before
  public void setup() {
    healthManager.setName(INTEGRATION_NAME);
  }

  @Test
  public void testBootstrap() {
    IntegrationHealth integrationHealth = healthManager.getHealth();

    assertEquals(IntegrationStatus.INACTIVE.name(), integrationHealth.getStatus());

    assertNull(integrationHealth.getMessage());
    assertEquals(NOT_AVAILABLE, integrationHealth.getVersion());

    IntegrationFlags flags = integrationHealth.getFlags();
    assertEquals(IntegrationFlags.ValueEnum.OK, flags.getParserInstalled());
    assertEquals(IntegrationFlags.ValueEnum.NOK, flags.getCertificateInstalled());
    assertEquals(IntegrationFlags.ValueEnum.NOK, flags.getConfiguratorInstalled());
    assertEquals(IntegrationFlags.ValueEnum.NOK, flags.getUserAuthenticated());
  }

  @Test
  public void testSuccessInvalid() {
    testFailBootstrap();

    IntegrationSettings settings = new IntegrationSettings();
    settings.setType(INTEGRATION_NAME);

    healthManager.success(settings);

    IntegrationHealth integrationHealth = healthManager.getHealth();

    assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), integrationHealth.getStatus());
    assertEquals("Fail to bootstrap integration", integrationHealth.getMessage());
  }

  @Test
  public void testSuccess() {
    IntegrationSettings settings = new IntegrationSettings();
    settings.setType(INTEGRATION_NAME);

    healthManager.setVersion(MOCK_VERSION);
    healthManager.success(settings);

    IntegrationHealth integrationHealth = healthManager.getHealth();

    assertEquals(MOCK_VERSION, integrationHealth.getVersion());
    assertEquals(INTEGRATION_NAME, integrationHealth.getName());

    assertEquals(IntegrationStatus.ACTIVE.name(), integrationHealth.getStatus());
    assertEquals(SUCCESS, integrationHealth.getMessage());
  }

  @Test
  public void testRetryInvalid() {
    testSuccess();
    healthManager.retry("Fail to bootstrap integration");

    IntegrationHealth integrationHealth = healthManager.getHealth();

    assertEquals(IntegrationStatus.ACTIVE.name(), integrationHealth.getStatus());
    assertEquals(SUCCESS, integrationHealth.getMessage());
  }

  @Test
  public void testRetry() {
    healthManager.retry("Fail to bootstrap integration");

    IntegrationHealth integrationHealth = healthManager.getHealth();

    assertEquals(IntegrationStatus.RETRYING_BOOTSTRAP.name(), integrationHealth.getStatus());
    assertEquals("Fail to bootstrap integration", integrationHealth.getMessage());

    healthManager.retry("Connection refused");

    integrationHealth = healthManager.getHealth();

    assertEquals(IntegrationStatus.RETRYING_BOOTSTRAP.name(), integrationHealth.getStatus());
    assertEquals("Connection refused", integrationHealth.getMessage());
  }

  @Test
  public void testFailBootstrapInvalid() {
    testSuccess();
    healthManager.failBootstrap("Fail to bootstrap integration");

    IntegrationHealth integrationHealth = healthManager.getHealth();

    assertEquals(IntegrationStatus.ACTIVE.name(), integrationHealth.getStatus());
    assertEquals(SUCCESS, integrationHealth.getMessage());
  }

  @Test
  public void testFailBootstrap() {
    healthManager.failBootstrap("Fail to bootstrap integration");

    IntegrationHealth integrationHealth = healthManager.getHealth();

    assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), integrationHealth.getStatus());
    assertEquals("Fail to bootstrap integration", integrationHealth.getMessage());
  }

  @Test
  public void testLatestUpdateTimestamp() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    Locale.setDefault(Locale.US);

    IntegrationHealth integrationHealth = healthManager.getHealth();

    assertNull(integrationHealth.getLatestPostTimestamp());

    healthManager.updateLatestPostTimestamp(1476109880000L);

    assertEquals("2016-10-10T14:31:20Z+0000", integrationHealth.getLatestPostTimestamp());
  }

  @Test
  public void testFlagsUpdate() {
    healthManager.parserInstalled(IntegrationFlags.ValueEnum.NOK);
    healthManager.certificateInstalled(IntegrationFlags.ValueEnum.OK);
    healthManager.configuratorInstalled(IntegrationFlags.ValueEnum.OK);
    healthManager.userAuthenticated(IntegrationFlags.ValueEnum.OK);

    IntegrationHealth integrationHealth = healthManager.getHealth();

    IntegrationFlags flags = integrationHealth.getFlags();
    assertEquals(IntegrationFlags.ValueEnum.NOK, flags.getParserInstalled());
    assertEquals(IntegrationFlags.ValueEnum.OK, flags.getCertificateInstalled());
    assertEquals(IntegrationFlags.ValueEnum.OK, flags.getConfiguratorInstalled());
    assertEquals(IntegrationFlags.ValueEnum.OK, flags.getUserAuthenticated());
  }

  @Test
  public void testFlagsWithoutConfiguratorInfo() {
    testSuccess();

    IntegrationHealth integrationHealth = healthManager.updateFlags();

    assertEquals(IntegrationStatus.OUT_OF_SERVICE.name(), integrationHealth.getStatus());
    assertEquals(OUT_OF_SERVICE, integrationHealth.getMessage());
  }

  @Test
  public void testFlagsLoadUrlNOK() {
    mockApplications();
    mockIntegrationBridge();

    testSuccess();

    mockResponse("https://test.symphony.com/apps/jira/controller.html",
        Response.Status.BAD_GATEWAY.getStatusCode());

    IntegrationHealth integrationHealth = healthManager.updateFlags();

    assertEquals(IntegrationStatus.OUT_OF_SERVICE.name(), integrationHealth.getStatus());
    assertEquals(OUT_OF_SERVICE, integrationHealth.getMessage());
  }

  @Test
  public void testFlagsIconUrlNOK() {
    mockApplications();
    mockIntegrationBridge();

    testSuccess();

    mockResponse("https://test.symphony.com/apps/jira/controller.html",
        Response.Status.OK.getStatusCode());
    mockResponse("https://test.symphony.com/apps/jira/img/appstore-logo.png",
        Response.Status.BAD_GATEWAY.getStatusCode());

    IntegrationHealth integrationHealth = healthManager.updateFlags();

    assertEquals(IntegrationStatus.OUT_OF_SERVICE.name(), integrationHealth.getStatus());
    assertEquals(OUT_OF_SERVICE, integrationHealth.getMessage());
  }

  @Test
  public void testFlagsUserNotAuthenticated() {
    healthManager.certificateInstalled(IntegrationFlags.ValueEnum.OK);

    mockApplications();
    mockIntegrationBridge();

    testSuccess();

    mockResponse("https://test.symphony.com/apps/jira/controller.html",
        Response.Status.OK.getStatusCode());
    mockResponse("https://test.symphony.com/apps/jira/img/appstore-logo.png",
        Response.Status.OK.getStatusCode());

    doReturn(false).when(authenticationProxy).isAuthenticated(INTEGRATION_NAME);

    IntegrationHealth integrationHealth = healthManager.updateFlags();

    assertEquals(IntegrationStatus.OUT_OF_SERVICE.name(), integrationHealth.getStatus());
    assertEquals(OUT_OF_SERVICE, integrationHealth.getMessage());
  }

  @Test
  public void testFlagsOK() {
    testFlagsUserNotAuthenticated();

    doReturn(true).when(authenticationProxy).isAuthenticated(INTEGRATION_NAME);

    IntegrationHealth integrationHealth = healthManager.updateFlags();

    assertEquals(IntegrationStatus.ACTIVE.name(), integrationHealth.getStatus());
    assertEquals(SUCCESS, integrationHealth.getMessage());
  }

  private void mockApplications() {
    Application application = new Application();
    application.setComponent(INTEGRATION_NAME);
    application.setContext(APP_ID);

    Map<String, Application> applications = new HashMap<>();
    applications.put(APP_ID, application);

    this.properties.setApplications(applications);
  }

  private void mockIntegrationBridge() {
    IntegrationBridge bridge = new IntegrationBridge();
    bridge.setHost(MOCK_HOST);

    this.properties.setIntegrationBridge(bridge);
  }

  private void mockResponse(String path, int statusCode) {
    WebTarget target = mock(WebTarget.class);
    Invocation.Builder builder = mock(Invocation.Builder.class);
    Response response = mock(Response.class);

    doReturn(target).when(client).target(path);
    doReturn(builder).when(target).request();
    doReturn(builder).when(builder).accept(WILDCARD);
    doReturn(response).when(builder).get();
    doReturn(statusCode).when(response).getStatus();
  }
}

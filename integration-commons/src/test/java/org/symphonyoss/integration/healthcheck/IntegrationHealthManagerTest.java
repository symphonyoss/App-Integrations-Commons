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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.IntegrationStatus;
import org.symphonyoss.integration.model.healthcheck.IntegrationFlags;
import org.symphonyoss.integration.model.healthcheck.IntegrationHealth;

import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Unit test for {@link IntegrationHealthManager}
 * Created by rsanchez on 05/08/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class IntegrationHealthManagerTest {

  private static final String INTEGRATION_NAME = "jiraWebHookIntegration";

  private static final String MOCK_VERSION = "0.0.1";

  @Mock
  private ScheduledExecutorService scheduler;

  @InjectMocks
  private IntegrationHealthManager healthManager = new IntegrationHealthManager(MOCK_VERSION);

  @Before
  public void setup() {
    healthManager.setName(INTEGRATION_NAME);
  }

  @Test
  public void testBootstrap() {
    IntegrationHealth integrationHealth = healthManager.getHealth();

    assertEquals(IntegrationStatus.INACTIVE.name(), integrationHealth.getStatus());

    assertNull(integrationHealth.getMessage());
    assertEquals(MOCK_VERSION, integrationHealth.getVersion());

    IntegrationFlags flags = integrationHealth.getFlags();
    assertEquals(IntegrationFlags.ValueEnum.OK, flags.getParserInstalled());
    assertEquals(IntegrationFlags.ValueEnum.NOK, flags.getCertificateInstalled());
    assertEquals(IntegrationFlags.ValueEnum.NOK, flags.getConfiguratorInstalled());
    assertEquals(IntegrationFlags.ValueEnum.NOK, flags.getUserAuthenticated());
  }

  @Test
  public void testSuccessInvalid() {
    testFailBootstrap();

    healthManager.success();

    IntegrationHealth integrationHealth = healthManager.getHealth();

    assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), integrationHealth.getStatus());
    assertEquals("Fail to bootstrap integration", integrationHealth.getMessage());
  }

  @Test
  public void testSuccess() {
    healthManager.success();

    IntegrationHealth integrationHealth = healthManager.getHealth();

    assertEquals(IntegrationStatus.ACTIVE.name(), integrationHealth.getStatus());
    assertEquals("Success", integrationHealth.getMessage());
  }

  @Test
  public void testRetryInvalid() {
    testSuccess();
    healthManager.retry("Fail to bootstrap integration");

    IntegrationHealth integrationHealth = healthManager.getHealth();

    assertEquals(IntegrationStatus.ACTIVE.name(), integrationHealth.getStatus());
    assertEquals("Success", integrationHealth.getMessage());
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
    assertEquals("Success", integrationHealth.getMessage());
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

}

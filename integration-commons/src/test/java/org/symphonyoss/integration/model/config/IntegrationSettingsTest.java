package org.symphonyoss.integration.model.config;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link IntegrationSettings}
 * Created by crepache on 20/06/17.
 */
public class IntegrationSettingsTest {

  private static final String CONFIGURATION_ID = "57e82afce4b07fea0651e8ac";
  private static final String TYPE = "appTest";
  private static final String NAME = "Application Test";
  private static final String DESC = "Application Description";
  private static final String USERNAME = "AppTest";
  private static final Long OWNER = 12394755755L;

  @Test
  public void testIntegrationSettings() {
    IntegrationSettings integrationSettings = new IntegrationSettings();

    integrationSettings.setConfigurationId(CONFIGURATION_ID);
    integrationSettings.setType(TYPE);
    integrationSettings.setName(NAME);
    integrationSettings.setDescription(DESC);
    integrationSettings.setEnabled(Boolean.TRUE);
    integrationSettings.setVisible(Boolean.TRUE);
    integrationSettings.setOwner(OWNER);
    integrationSettings.setActive(Boolean.FALSE);
    integrationSettings.setUsername(USERNAME);


    Assert.assertEquals(CONFIGURATION_ID, integrationSettings.getConfigurationId());
    Assert.assertEquals(TYPE, integrationSettings.getType());
    Assert.assertEquals(NAME, integrationSettings.getName());
    Assert.assertEquals(DESC, integrationSettings.getDescription());
    Assert.assertTrue(integrationSettings.getEnabled());
    Assert.assertTrue(integrationSettings.getVisible());
    Assert.assertEquals(OWNER, integrationSettings.getOwner());
    Assert.assertFalse(integrationSettings.getActive());
    Assert.assertEquals(USERNAME, integrationSettings.getUsername());
  }
}

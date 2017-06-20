package org.symphonyoss.integration.model.config;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link IntegrationSettings}
 * Created by crepache on 20/06/17.
 */
public class IntegrationSettingsTest {

  private static final String MOCK_CONFIGURATION_ID = "57e82afce4b07fea0651e8ac";
  private static final String MOCK_TYPE = "appTest";
  private static final String MOCK_NAME = "Application Test";
  private static final String MOCK_DESC = "Application Description";
  private static final String MOCK_USERNAME = "AppTest";
  private static final Long OWNER = 12394755755L;

  @Test
  public void testIntegrationSettings() {
    IntegrationSettings integrationSettings = new IntegrationSettings();

    integrationSettings.setConfigurationId(MOCK_CONFIGURATION_ID);
    integrationSettings.setType(MOCK_TYPE);
    integrationSettings.setName(MOCK_NAME);
    integrationSettings.setDescription(MOCK_DESC);
    integrationSettings.setEnabled(Boolean.TRUE);
    integrationSettings.setVisible(Boolean.TRUE);
    integrationSettings.setOwner(OWNER);
    integrationSettings.setActive(Boolean.FALSE);
    integrationSettings.setUsername(MOCK_USERNAME);


    Assert.assertEquals(MOCK_CONFIGURATION_ID, integrationSettings.getConfigurationId());
    Assert.assertEquals(MOCK_TYPE, integrationSettings.getType());
    Assert.assertEquals(MOCK_NAME, integrationSettings.getName());
    Assert.assertEquals(MOCK_DESC, integrationSettings.getDescription());
    Assert.assertTrue(integrationSettings.getEnabled());
    Assert.assertTrue(integrationSettings.getVisible());
    Assert.assertEquals(OWNER, integrationSettings.getOwner());
    Assert.assertFalse(integrationSettings.getActive());
    Assert.assertEquals(MOCK_USERNAME, integrationSettings.getUsername());
  }
}

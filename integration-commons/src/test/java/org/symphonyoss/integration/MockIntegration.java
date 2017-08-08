package org.symphonyoss.integration;

import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.healthcheck.IntegrationHealthManager;
import org.symphonyoss.integration.model.config.IntegrationSettings;
import org.symphonyoss.integration.model.healthcheck.IntegrationHealth;
import org.symphonyoss.integration.model.yaml.IntegrationProperties;
import org.symphonyoss.integration.utils.IntegrationUtils;

import java.util.Collections;
import java.util.Set;

/**
 * This is a mock class for the {@link Integration} interface.
 * Created by rsanchez on 11/01/17.
 */
public class MockIntegration extends BaseIntegration {

  public MockIntegration() {}

  public MockIntegration(IntegrationProperties properties, IntegrationUtils utils,
      AuthenticationProxy authenticationProxy, IntegrationHealthManager healthManager) {
    this.properties = properties;
    this.utils = utils;
    this.authenticationProxy = authenticationProxy;
    this.healthManager = healthManager;
  }

  @Override
  public void onCreate(String integrationUser) {
    /* This has no implementation due to the nature of this class */
  }

  @Override
  public void onConfigChange(IntegrationSettings settings) {
    /* This has no implementation due to the nature of this class */
  }

  @Override
  public void onDestroy() {
    /* This has no implementation due to the nature of this class */
  }

  @Override
  public IntegrationHealth getHealthStatus() {
    return healthManager.getHealth();
  }

  @Override
  public IntegrationSettings getSettings() {
    return null;
  }

  @Override
  public Set<String> getIntegrationWhiteList() {
    return Collections.emptySet();
  }
}

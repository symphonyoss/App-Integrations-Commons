package org.symphonyoss.integration;

import com.symphony.api.pod.model.V1Configuration;

import org.symphonyoss.integration.authentication.AuthenticationProxy;
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

  private IntegrationHealth health = new IntegrationHealth();

  public MockIntegration(IntegrationProperties properties, IntegrationUtils utils,
      AuthenticationProxy authenticationProxy) {
    this.properties = properties;
    this.utils = utils;
    this.authenticationProxy = authenticationProxy;
  }

  @Override
  public void onCreate(String integrationUser) {
    /* This has no implementation due to the nature of this class */
  }

  @Override
  public void onConfigChange(V1Configuration conf) {
    /* This has no implementation due to the nature of this class */
  }

  @Override
  public void onDestroy() {
    /* This has no implementation due to the nature of this class */
  }

  @Override
  public IntegrationHealth getHealthStatus() {
    return health;
  }

  public void setHealth(IntegrationHealth health) {
    this.health = health;
  }

  @Override
  public V1Configuration getConfig() {
    return null;
  }

  @Override
  public Set<String> getIntegrationWhiteList() {
    return Collections.emptySet();
  }
}

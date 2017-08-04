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

package org.symphonyoss.integration.model.yaml;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Base data model for the YAML config file, used to read provisioning info for Integration
 * Bridge and other info as well, related to the services that Integration Bridge interacts with (as
 * Symphony Agent, POD API and Key Manager).
 * Created by rsanchez on 10/11/16.
 */
@Configuration
@ConfigurationProperties
public class IntegrationProperties {

  private static final String DEFAULT_HTTPS_PORT = "443";

  private static final String APPS_CONTEXT = "apps";

  private static final String INTEGRATION_CONTEXT = "integration";

  private AdminUser adminUser;

  private ConnectionInfo pod;

  private ConnectionInfo agent;

  private ConnectionInfo podSessionManager;

  private ConnectionInfo keyManager;

  private ConnectionInfo keyManagerAuth;

  private ApiClientConfig apiClientConfig;

  private Map<String, Application> applications = new HashMap<>();

  private IntegrationBridge integrationBridge;

  private Certificate signingCert;

  public AdminUser getAdminUser() {
    return adminUser;
  }

  public void setAdminUser(AdminUser adminUser) {
    this.adminUser = adminUser;
  }

  public ConnectionInfo getPod() {
    return pod;
  }

  public void setPod(ConnectionInfo pod) {
    this.pod = pod;
  }

  public ConnectionInfo getAgent() {
    return agent;
  }

  public void setAgent(ConnectionInfo agent) {
    this.agent = agent;
  }

  public ConnectionInfo getKeyManager() {
    return keyManager;
  }

  public void setKeyManager(ConnectionInfo keyManager) {
    this.keyManager = keyManager;
  }

  public ConnectionInfo getPodSessionManager() {
    return podSessionManager;
  }

  public void setPodSessionManager(ConnectionInfo podSessionManager) {
    this.podSessionManager = podSessionManager;
  }

  public ConnectionInfo getKeyManagerAuth() {
    return keyManagerAuth;
  }

  public void setKeyManagerAuth(ConnectionInfo keyManagerAuth) {
    this.keyManagerAuth = keyManagerAuth;
  }


  public ApiClientConfig getApiClientConfig() {
    return apiClientConfig;
  }

  public void setApiClientConfig(ApiClientConfig apiClientConfig) {
    this.apiClientConfig = apiClientConfig;
  }

  public Map<String, Application> getApplications() {
    return applications;
  }

  public void setApplications(
      Map<String, Application> applications) {
    this.applications = applications;
  }

  public Application getApplication(String component) {
    for (Map.Entry<String, Application> entry : applications.entrySet()) {
      Application application = entry.getValue();
      if (component.equals(application.getComponent())) {
        application.setId(entry.getKey());
        return application;
      }
    }

    return null;
  }

  /**
   * Get application identifier based on application component name
   * @param component Application component name
   * @return Application identifier
   */
  public String getApplicationId(String component) {
    for (Map.Entry<String, Application> entry : applications.entrySet()) {
      if (component.equals(entry.getValue().getComponent())) {
        return entry.getKey();
      }
    }

    return component;
  }

  public IntegrationBridge getIntegrationBridge() {
    return integrationBridge;
  }

  public void setIntegrationBridge(
      IntegrationBridge integrationBridge) {
    this.integrationBridge = integrationBridge;
  }

  public Certificate getSigningCert() {
    return signingCert;
  }

  public void setSigningCert(Certificate signingCert) {
    this.signingCert = signingCert;
  }

  /**
   * Get the global whitelist based on YAML file settings.
   * @return Global origin whitelist
   */
  public Set<String> getGlobalWhiteList() {
    if (integrationBridge == null) {
      return Collections.emptySet();
    }

    return integrationBridge.getWhiteList();
  }

  public String getSymphonyUrl() {
    if ((pod == null) || (StringUtils.isEmpty(pod.getHost()))) {
      return StringUtils.EMPTY;
    }

    String port = pod.getPort();

    if (StringUtils.isEmpty(port)) {
      port = DEFAULT_HTTPS_PORT;
    }

    return String.format("https://%s:%s", pod.getHost(), port);
  }

  public String getPodUrl() {
    String symphonyUrl = getSymphonyUrl();

    if (StringUtils.isEmpty(symphonyUrl)) {
      return StringUtils.EMPTY;
    }

    return String.format("%s/pod", symphonyUrl);
  }

  public String getLoginUrl() {
    String symphonyUrl = getSymphonyUrl();

    if (StringUtils.isEmpty(symphonyUrl)) {
      return StringUtils.EMPTY;
    }

    return String.format("%s/login", symphonyUrl);
  }

  public String getSessionManagerAuthUrl() {
    if ((podSessionManager == null) || (StringUtils.isEmpty(podSessionManager.getHost()))) {
      return StringUtils.EMPTY;
    }

    String port = podSessionManager.getPort();

    if (StringUtils.isEmpty(port)) {
      port = DEFAULT_HTTPS_PORT;
    }

    return String.format("https://%s:%s/sessionauth", podSessionManager.getHost(), port);
  }

  public String getKeyManagerUrl() {
    if ((keyManager == null) || (StringUtils.isEmpty(keyManager.getHost()))) {
      return StringUtils.EMPTY;
    }

    String port = keyManager.getPort();

    if (StringUtils.isEmpty(port)) {
      port = DEFAULT_HTTPS_PORT;
    }

    return String.format("https://%s:%s/relay", keyManager.getHost(), port);
  }

  public String getKeyManagerAuthUrl() {
    if ((keyManagerAuth == null) || (StringUtils.isEmpty(keyManagerAuth.getHost()))) {
      return StringUtils.EMPTY;
    }

    String port = keyManagerAuth.getPort();

    if (StringUtils.isEmpty(port)) {
      port = DEFAULT_HTTPS_PORT;
    }

    return String.format("https://%s:%s/keyauth", keyManagerAuth.getHost(), port);
  }

  public String getAgentUrl() {
    if ((agent == null) || (StringUtils.isEmpty(agent.getHost()))) {
      return StringUtils.EMPTY;
    }

    String port = agent.getPort();

    if (StringUtils.isEmpty(port)) {
      port = DEFAULT_HTTPS_PORT;
    }

    return String.format("https://%s:%s/agent", agent.getHost(), port);
  }

  public String getApplicationUrl(String appId) {
    Application application = getApplications().get(appId);

    if (getIntegrationBridge() == null || application == null) {
      return StringUtils.EMPTY;
    }

    return String.format("https://%s/%s/%s", getIntegrationBridge().getHost(), APPS_CONTEXT, application.getContext());
  }

  public String getIntegrationBridgeUrl() {
    if ((integrationBridge == null) || (StringUtils.isEmpty(integrationBridge.getHost()))){
      return StringUtils.EMPTY;
    }

    String port = agent.getPort();

    if (StringUtils.isEmpty(port)) {
      port = DEFAULT_HTTPS_PORT;
    }

    return String.format("https://%s:%s/%s", integrationBridge.getHost(), port, INTEGRATION_CONTEXT);
  }

  @Override
  public String toString() {
    return "IntegrationProperties{" +
        "pod=" + pod +
        ", agent=" + agent +
        ", sessionManager=" + podSessionManager +
        ", keyManager=" + keyManager +
        '}';
  }
}
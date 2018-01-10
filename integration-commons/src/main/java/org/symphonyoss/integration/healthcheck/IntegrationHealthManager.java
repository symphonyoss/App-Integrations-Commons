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
import static org.symphonyoss.integration.model.healthcheck.IntegrationFlags.ValueEnum.NOK;
import static org.symphonyoss.integration.model.healthcheck.IntegrationFlags.ValueEnum.NOT_APPLICABLE;
import static org.symphonyoss.integration.model.healthcheck.IntegrationFlags.ValueEnum.OK;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.symphonyoss.integration.IntegrationStatus;
import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.model.config.IntegrationSettings;
import org.symphonyoss.integration.model.healthcheck.IntegrationConfigurator;
import org.symphonyoss.integration.model.healthcheck.IntegrationFlags;
import org.symphonyoss.integration.model.healthcheck.IntegrationHealth;
import org.symphonyoss.integration.model.yaml.Application;
import org.symphonyoss.integration.model.yaml.HttpClientConfig;
import org.symphonyoss.integration.model.yaml.IntegrationBridge;
import org.symphonyoss.integration.model.yaml.IntegrationProperties;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Maintains the state of an individual Integration's health ({@link IntegrationHealth}), such as Jira, Github, etc.
 * Usually part of the basic attributes owned by any Integration on Integration Bridge.
 * Created by rsanchez on 03/08/16.
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IntegrationHealthManager {

  private static final Logger LOG = LoggerFactory.getLogger(IntegrationHealthManager.class);

  private static final String SUCCESS = "Success";

  private static final String OUT_OF_SERVICE = "Integration Out of Service. Please, check the flags";

  private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'Z";

  private static final String APP_CONTROLLER_PAGE = "/controller.html";

  private static final String APP_ICON_IMAGE = "/img/appstore-logo.png";


  private IntegrationHealth health = new IntegrationHealth();

  private IntegrationFlags flags = new IntegrationFlags();

  @Autowired
  private AuthenticationProxy authenticationProxy;

  @Autowired
  private IntegrationProperties properties;

  /**
   * Integration settings
   */
  private IntegrationSettings settings;

  /**
   * HTTP client
   */
  private Client client;

  /**
   * Cache to 'configurator installed' flag.
   */
  private LoadingCache<String, IntegrationFlags.ValueEnum> configuratorFlagsCache;

  public IntegrationHealthManager() {
    this.flags.setParserInstalled(OK);
    this.flags.setConfiguratorInstalled(NOK);
    this.flags.setUserCertificateInstalled(NOK);
    this.flags.setAppCertificateInstalled(NOT_APPLICABLE);
    this.flags.setUserAuthenticated(NOK);

    this.health.setStatus(IntegrationStatus.INACTIVE.name());
    this.health.setFlags(flags);

    initHttpClient();
    initCache();
  }

  /**
   * Initializes HTTP Client
   */
  private void initHttpClient() {
    HttpClientConfig httpConfig;

    if ((properties == null) || (properties.getHttpClientConfig() == null)) {
      httpConfig = new HttpClientConfig();
    } else {
      httpConfig = properties.getHttpClientConfig();
    }

    if (this.client == null) {
      final ClientConfig clientConfig = new ClientConfig();
      clientConfig.property(ClientProperties.CONNECT_TIMEOUT, httpConfig.getConnectTimeout());
      clientConfig.property(ClientProperties.READ_TIMEOUT, httpConfig.getReadTimeout());

      this.client = ClientBuilder.newBuilder().withConfig(clientConfig).build();
    }
  }

  /**
   * Init local cache
   */
  private void initCache() {
    this.configuratorFlagsCache = CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.SECONDS)
        .build(new CacheLoader<String, IntegrationFlags.ValueEnum>() {
          @Override
          public IntegrationFlags.ValueEnum load(String key) throws Exception {
            return getConfiguratorInstalledFlag();
          }
        });
  }

  public void setName(String name) {
    this.health.setName(name);
  }

  public void setVersion(String version) {
    this.health.setVersion(version);
  }

  public IntegrationHealth getHealth() {
    return health;
  }

  public void success(IntegrationSettings settings) {
    this.settings = settings;

    List<String> expectedStatus = Arrays.asList(IntegrationStatus.INACTIVE.name(),
        IntegrationStatus.RETRYING_BOOTSTRAP.name());

    if (expectedStatus.contains(health.getStatus())) {
      this.health.setStatus(IntegrationStatus.ACTIVE.name());
      this.health.setMessage(SUCCESS);
    }

    initConfiguratorData();
  }

  /**
   * Initializes the configurator data.
   *
   * This method should fill the configurator data according to the YAML file.
   */
  private void initConfiguratorData() {
    Application application = properties.getApplication(settings.getType());
    IntegrationBridge bridge = properties.getIntegrationBridge();

    if ((application != null) && (bridge != null) && (StringUtils.isNotEmpty(application.getContext())) &&
        (StringUtils.isNotEmpty(bridge.getHost()))) {
      IntegrationConfigurator configurator = new IntegrationConfigurator();

      String baseUrl = properties.getApplicationUrl(application.getId());

      configurator.setLoadUrl(baseUrl.concat(APP_CONTROLLER_PAGE));
      configurator.setIconUrl(baseUrl.concat(APP_ICON_IMAGE));

      this.health.setConfigurator(configurator);
    }
  }

  public void retry(String message) {
    List<String> expectedStatus = Arrays.asList(IntegrationStatus.INACTIVE.name(),
        IntegrationStatus.RETRYING_BOOTSTRAP.name());

    if (expectedStatus.contains(health.getStatus())) {
      this.health.setStatus(IntegrationStatus.RETRYING_BOOTSTRAP.name());
      this.health.setMessage(message);
    }
  }

  public void failBootstrap(String message) {
    if (IntegrationStatus.INACTIVE.name().equals(health.getStatus())) {
      this.health.setStatus(IntegrationStatus.FAILED_BOOTSTRAP.name());
      this.health.setMessage(message);
    }
  }

  public void updateLatestPostTimestamp(Long timestamp) {
    SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_FORMAT);
    Date date = new Date(timestamp);
    this.health.setLatestPostTimestamp(formatter.format(date));
  }

  /**
   * Update the integration flags.
   *
   * If all the flags are OK the status becomes ACTIVE, otherwise the status becomes OUT_OF_SERVICE.
   * @return Updated health status
   */
  public IntegrationHealth updateFlags() {
    updateConfiguratorInstalledFlag();
    updateUserAuthenticatedFlag();

    boolean active = flags.isUp();

    // Update integration status according to the flags
    // If status = OUT_OF_SERVICE and all flags are OK the status becomes ACTIVE
    // However, if status = ACTIVE and at least one flag are NOK the status becomes OUT_OF_SERVICE
    if ((active) && (IntegrationStatus.OUT_OF_SERVICE.name().equals(health.getStatus()))) {
      this.health.setStatus(IntegrationStatus.ACTIVE.name());
      this.health.setMessage(SUCCESS);
    } else if (!(active) && (IntegrationStatus.ACTIVE.name().equals(health.getStatus()))) {
      this.health.setStatus(IntegrationStatus.OUT_OF_SERVICE.name());
      this.health.setMessage(OUT_OF_SERVICE);
    }

    return health;
  }

  /**
   * Updates the 'user authenticated' flag.
   */
  private void updateUserAuthenticatedFlag() {
    boolean authenticated = false;

    if (settings != null) {
      authenticated = authenticationProxy.isAuthenticated(settings.getType());
    }

    if (authenticated) {
      userAuthenticated(OK);
    } else {
      userAuthenticated(NOK);
    }
  }

  /**
   * Updates the 'configurator installed' flag.
   */
  private void updateConfiguratorInstalledFlag() {
    if (settings != null) {
      IntegrationFlags.ValueEnum flagStatus = configuratorFlagsCache.getUnchecked(settings.getType());
      configuratorInstalled(flagStatus);
    }
  }

  /**
   * Retrieves the 'configurator installed' flag. This method sends a request to Configurator app if
   * the response is HTTP 200 then 'configurator installed' flag will be OK, otherwise it will be
   * NOK.
   *
   * This method should check the load URL and icon URL.
   */
  private IntegrationFlags.ValueEnum getConfiguratorInstalledFlag() {
    IntegrationConfigurator configurator = health.getConfigurator();

    if (configurator == null) {
      return NOK;
    }

    String loadUrl = configurator.getLoadUrl();
    IntegrationFlags.ValueEnum result = checkWebResource(loadUrl);

    if (OK.equals(result)) {
      String iconUrl = configurator.getIconUrl();
      return checkWebResource(iconUrl);
    } else {
      return NOK;
    }
  }

  /**
   * Check if the web resource was already deployed.
   * @param path Web resource path
   * @return OK if the resource was already deployed or NOK otherwise.
   */
  private IntegrationFlags.ValueEnum checkWebResource(String path) {
    try {
      WebTarget target = client.target(path);
      Response response = target.request().accept(WILDCARD).get();

      if (Response.Status.OK.getStatusCode() == response.getStatus()) {
        return OK;
      } else {
        return NOK;
      }
    } catch (Exception e) {
      LOG.error("Fail to verify resouce " + path, e);
      return NOK;
    }
  }

  public void parserInstalled(IntegrationFlags.ValueEnum value) {
    this.flags.setParserInstalled(value);
  }

  public void configuratorInstalled(IntegrationFlags.ValueEnum value) {
    this.flags.setConfiguratorInstalled(value);
  }

  public void userCertificateInstalled(IntegrationFlags.ValueEnum value) {
    this.flags.setUserCertificateInstalled(value);
  }

  public void appCertificateInstalled(IntegrationFlags.ValueEnum value) {
    this.flags.setAppCertificateInstalled(value);
  }

  public void userAuthenticated(IntegrationFlags.ValueEnum value) {
    this.flags.setUserAuthenticated(value);
  }

}

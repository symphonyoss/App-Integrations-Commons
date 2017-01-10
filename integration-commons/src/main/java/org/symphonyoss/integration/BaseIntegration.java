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

package org.symphonyoss.integration;

import static javax.ws.rs.core.MediaType.TEXT_HTML_TYPE;
import static org.symphonyoss.integration.model.healthcheck.IntegrationFlags.ValueEnum.NOK;
import static org.symphonyoss.integration.model.healthcheck.IntegrationFlags.ValueEnum.OK;
import static org.symphonyoss.integration.model.yaml.Keystore.DEFAULT_KEYSTORE_TYPE_SUFFIX;

import com.symphony.logging.ISymphonyLogger;
import com.symphony.logging.SymphonyLoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.exception.bootstrap.CertificateNotFoundException;
import org.symphonyoss.integration.exception.bootstrap.LoadKeyStoreException;
import org.symphonyoss.integration.healthcheck.IntegrationHealthManager;
import org.symphonyoss.integration.model.DefaultAppKeystore;
import org.symphonyoss.integration.model.healthcheck.IntegrationFlags;
import org.symphonyoss.integration.model.yaml.Application;
import org.symphonyoss.integration.model.yaml.IntegrationBridge;
import org.symphonyoss.integration.model.yaml.IntegrationProperties;
import org.symphonyoss.integration.model.yaml.Keystore;
import org.symphonyoss.integration.utils.IntegrationUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Abstract integration class that contains the key services for all the integrations.
 * Separates methods, objects and logic that are common to any kind of integration on Integration Bridge so other
 * implementations don't need to "re-implement" their own.
 * Created by rsanchez on 21/11/16.
 */
public abstract class BaseIntegration {

  private static final ISymphonyLogger LOG = SymphonyLoggerFactory.getLogger(BaseIntegration.class);

  private static final String APPS_CONTEXT = "apps";

  private static final String APP_DEFAULT_PAGE = "controller.html";

  @Autowired
  protected AuthenticationProxy authenticationProxy;

  @Autowired
  protected IntegrationProperties properties;

  /**
   * Status of the integration
   */
  protected IntegrationHealthManager healthManager = new IntegrationHealthManager();

  /**
   * HTTP client
   */
  private Client client;

  /**
   * Cache to 'configurator installed' flag.
   */
  private LoadingCache<String, IntegrationFlags.ValueEnum> configuratorFlagsCache;

  @Autowired
  protected IntegrationUtils utils;

  public BaseIntegration() {
    final ClientConfig configuration = new ClientConfig();
    configuration.property(ClientProperties.CONNECT_TIMEOUT, 1000);
    configuration.property(ClientProperties.READ_TIMEOUT, 1000);

    this.client = ClientBuilder.newBuilder().withConfig(configuration).build();

    this.configuratorFlagsCache = CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.SECONDS)
        .build(new CacheLoader<String, IntegrationFlags.ValueEnum>() {
          @Override
          public IntegrationFlags.ValueEnum load(String key) throws Exception {
            return getConfiguratorInstalledFlag(key);
          }
        });
  }

  /**
   * Get application identifier based on integration username
   * @param integrationUser Integration username
   * @return Application identifier
   */
  public String getApplicationId(String integrationUser) {
    return properties.getApplicationId(integrationUser);
  }

  /**
   * Read the key store and register the user with new SSL context.
   * @param integrationUser Integration username
   */
  public void registerUser(String integrationUser) {
    String certsDir = utils.getCertsDirectory();

    Application application = properties.getApplication(integrationUser);

    if (application == null) {
      throw new CertificateNotFoundException("Certificate file unknown");
    }

    Keystore keystoreConfig;
    if (application.getKeystore() == null) {
      keystoreConfig = new DefaultAppKeystore(application.getId());
    } else {
      keystoreConfig = application.getKeystore();
    }

    String locationFile = keystoreConfig.getFile();
    if (StringUtils.isBlank(locationFile)) {
      locationFile = application.getId() + DEFAULT_KEYSTORE_TYPE_SUFFIX;
    }

    String storeLocation = certsDir + locationFile;
    String password = keystoreConfig.getPassword();
    String type = keystoreConfig.getType();

    KeyStore keyStore;
    try {
      keyStore = loadKeyStore(storeLocation, password, type);
    } catch (GeneralSecurityException | IOException e) {
      throw new LoadKeyStoreException(
          String.format("Fail to load keystore file at %s", storeLocation), e);
    }

    healthManager.certificateInstalled(OK);
    authenticationProxy.registerUser(integrationUser, keyStore, password);
  }

  /**
   * Load the keystore file.
   * @param storeLocation Keystore path.
   * @param password Keystore password
   * @param type Keystore type
   * @return Keystore object
   */
  private KeyStore loadKeyStore(String storeLocation, String password, String type)
      throws GeneralSecurityException, IOException {
    final KeyStore ks = KeyStore.getInstance(type);
    ks.load(new FileInputStream(storeLocation), password.toCharArray());
    return ks;
  }

  /**
   * Retrieves the cached 'configurator installed' flag.
   * @param appType Application type
   */
  public IntegrationFlags.ValueEnum getCachedConfiguratorInstalledFlag(String appType) {
    return configuratorFlagsCache.getUnchecked(appType);
  }

  /**
   * Retrieves the 'configurator installed' flag.
   * @param appType Application type
   */
  public IntegrationFlags.ValueEnum getConfiguratorInstalledFlag(String appType) {
    Application application = properties.getApplication(appType);
    IntegrationBridge bridge = properties.getIntegrationBridge();

    if ((application == null) || (bridge == null)) {
      return NOK;
    }

    return getConfiguratorInstalledFlag(bridge.getHost(), application.getContext());
  }

  /**
   * Retrieves the 'configurator installed' flag. This method sends a request to Configurator app if
   * the response is HTTP 200 then 'configurator installed' flag will be OK, otherwise it will be
   * NOK.
   * @param host Integration Bridge host
   * @param context Application context
   */
  private IntegrationFlags.ValueEnum getConfiguratorInstalledFlag(String host, String context) {
    if ((StringUtils.isEmpty(host)) || (StringUtils.isEmpty(context))) {
      return NOK;
    }

    try {
      String baseUrl = String.format("https://%s", host);

      WebTarget target =
          client.target(baseUrl).path(APPS_CONTEXT).path(context).path(APP_DEFAULT_PAGE);
      Response response = target.request().accept(TEXT_HTML_TYPE).get();

      if (Response.Status.OK.getStatusCode() == response.getStatus()) {
        return OK;
      } else {
        return NOK;
      }
    } catch (Exception e) {
      LOG.error("Fail to verify configurator status.", e);
      return NOK;
    }
  }

  public IntegrationHealthManager getHealthManager() {
    return healthManager;
  }
}

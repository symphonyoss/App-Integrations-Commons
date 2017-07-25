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

import static org.symphonyoss.integration.model.healthcheck.IntegrationFlags.ValueEnum.OK;
import static org.symphonyoss.integration.model.yaml.Keystore.DEFAULT_KEYSTORE_TYPE_SUFFIX;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.symphonyoss.integration.model.yaml.AppAuthenticationModel;
import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.exception.bootstrap.LoadKeyStoreException;
import org.symphonyoss.integration.healthcheck.IntegrationHealthManager;
import org.symphonyoss.integration.model.yaml.Application;
import org.symphonyoss.integration.model.yaml.IntegrationProperties;
import org.symphonyoss.integration.model.yaml.Keystore;
import org.symphonyoss.integration.utils.IntegrationUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

/**
 * Abstract integration class that contains the key services for all the integrations.
 * Separates methods, objects and logic that are common to any kind of integration on Integration Bridge so other
 * implementations don't need to "re-implement" their own.
 * Created by rsanchez on 21/11/16.
 */
public abstract class BaseIntegration implements Integration {

  @Autowired
  protected AuthenticationProxy authenticationProxy;

  @Autowired
  protected IntegrationProperties properties;

  /**
   * Status of the integration
   */
  @Autowired
  protected IntegrationHealthManager healthManager;

  @Autowired
  protected IntegrationUtils utils;

  /**
   * Setup the health manager adding the application identifier and version.
   * @param integrationUser Integration username
   */
  public void setupHealthManager(String integrationUser) {
    String applicationId = properties.getApplicationId(integrationUser);
    healthManager.setName(applicationId);

    String version = getClass().getPackage().getImplementationVersion();
    healthManager.setVersion(version);
  }

  /**
   * Read the key store and register the user with new SSL context.
   * @param integrationUser Integration username
   */
  public void registerUser(String integrationUser) {
    String certsDir = utils.getCertsDirectory();

    Application application = properties.getApplication(integrationUser);

    if ((application == null) || (application.getKeystore() == null) ||
        (StringUtils.isEmpty(application.getKeystore().getPassword()))) {
      String appId = application != null ? application.getId() : integrationUser;
      throw new LoadKeyStoreException(
          "Fail to retrieve the keystore password. Application: " + appId);
    }

    KeyStore keyStore = loadKeyStore(certsDir, application);

    healthManager.certificateInstalled(OK);
    authenticationProxy.registerUser(integrationUser, keyStore, application.getKeystore().getPassword());
  }

  /**
   * Load the keystore file.
   * @param certsDir Certificate directory
   * @param application Application settings
   * @return Keystore object
   */
  protected KeyStore loadKeyStore(String certsDir, Application application) {
    Keystore keystoreConfig = application.getKeystore();

    String locationFile = keystoreConfig.getFile();
    if (StringUtils.isBlank(locationFile)) {
      locationFile = application.getId() + DEFAULT_KEYSTORE_TYPE_SUFFIX;
    }

    String storeLocation = certsDir + locationFile;
    String password = keystoreConfig.getPassword();
    String type = keystoreConfig.getType();

    try(FileInputStream inputStream = new FileInputStream(storeLocation)) {
      final KeyStore ks = KeyStore.getInstance(type);
      ks.load(inputStream, password.toCharArray());

      return ks;
    } catch (GeneralSecurityException | IOException e) {
      throw new LoadKeyStoreException(
          String.format("Fail to load keystore file at %s", storeLocation), e);
    }
  }

  public IntegrationHealthManager getHealthManager() {
    return healthManager;
  }

  @Override
  public AppAuthenticationModel getAuthenticationModel() {
    return null;
  }

}

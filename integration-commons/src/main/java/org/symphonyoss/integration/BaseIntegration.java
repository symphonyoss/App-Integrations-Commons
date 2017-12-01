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

import static org.symphonyoss.integration.model.healthcheck.IntegrationFlags.ValueEnum.NOK;
import static org.symphonyoss.integration.model.healthcheck.IntegrationFlags.ValueEnum.OK;
import static org.symphonyoss.integration.model.yaml.Keystore.DEFAULT_KEYSTORE_APP_SUFFIX;
import static org.symphonyoss.integration.model.yaml.Keystore.DEFAULT_KEYSTORE_TYPE_SUFFIX;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.authentication.api.AppAuthenticationProxy;
import org.symphonyoss.integration.exception.IntegrationRuntimeException;
import org.symphonyoss.integration.exception.bootstrap.LoadKeyStoreException;
import org.symphonyoss.integration.exception.bootstrap.UnexpectedBootstrapException;
import org.symphonyoss.integration.healthcheck.IntegrationHealthManager;
import org.symphonyoss.integration.model.yaml.Application;
import org.symphonyoss.integration.model.yaml.IntegrationProperties;
import org.symphonyoss.integration.model.yaml.Keystore;
import org.symphonyoss.integration.utils.IntegrationUtils;

import java.io.ByteArrayInputStream;
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

  private static final String KEYSTORE_DATA_TEMPLATE = "apps.%s.keystore.data";

  private static final String KEYSTORE_PASS_TEMPLATE = "apps.%s.keystore.password";

  private static final String APP_KEYSTORE_DATA_TEMPLATE = "apps.%s.app_keystore.data";

  private static final String APP_KEYSTORE_PASS_TEMPLATE = "apps.%s.app_keystore.password";

  @Autowired
  protected AuthenticationProxy authenticationProxy;

  @Autowired
  private AppAuthenticationProxy appAuthenticationProxy;

  @Autowired
  protected IntegrationProperties properties;

  /**
   * Status of the integration
   */
  @Autowired
  protected IntegrationHealthManager healthManager;

  @Autowired
  protected IntegrationUtils utils;

  @Autowired
  protected Environment environment;

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
    try {
      String certsDir = utils.getCertsDirectory();

      Application application = properties.getApplication(integrationUser);

      KeyStore keyStore = loadUserKeyStore(certsDir, application, integrationUser);

      healthManager.userCertificateInstalled(OK);

      authenticationProxy.registerUser(integrationUser, keyStore, application.getKeystore().getPassword());
    } catch (IntegrationRuntimeException e) {
      healthManager.userCertificateInstalled(NOK);
      healthManager.failBootstrap(e.getMessage());

      throw e;
    } catch (Exception e) {
      healthManager.userCertificateInstalled(NOK);
      healthManager.failBootstrap(e.getMessage());

      throw new UnexpectedBootstrapException(
          String.format("Something went wrong when bootstrapping the integration %s",
              integrationUser), e);
    }
  }

  /**
   * Read the application key store and register it with new SSL context.
   * @param integrationUser Integration username
   */
  public void registerApp(String integrationUser) {
    try {
      String certsDir = utils.getCertsDirectory();

      Application application = properties.getApplication(integrationUser);

      KeyStore keyStore = loadAppKeyStore(certsDir, application, integrationUser);

      healthManager.appCertificateInstalled(OK);

      appAuthenticationProxy.registerApplication(application.getId(), keyStore,
          application.getAppKeystore().getPassword());
    } catch (IntegrationRuntimeException e) {
      healthManager.appCertificateInstalled(NOK);
      healthManager.failBootstrap(e.getMessage());

      throw e;
    } catch (Exception e) {
      healthManager.appCertificateInstalled(NOK);
      healthManager.failBootstrap(e.getMessage());

      throw new UnexpectedBootstrapException(
          String.format("Something went wrong when bootstrapping the integration %s",
              integrationUser), e);
    }
  }

  /**
   * Load the user keystore file.
   *
   * @param certsDir Certificate directory
   * @param application Application settings
   * @param integrationUser Integration username
   * @return Keystore object
   */
  protected KeyStore loadUserKeyStore(String certsDir, Application application, String integrationUser) {
    if (application == null) {
      throw new LoadKeyStoreException(
          "Fail to retrieve the user keystore password. Application: " + integrationUser);
    }

    String dataEnv = String.format(KEYSTORE_DATA_TEMPLATE, application.getId());
    String passEnv = String.format(KEYSTORE_PASS_TEMPLATE, application.getId());

    String data = environment.getProperty(dataEnv);
    String pass = environment.getProperty(passEnv);

    if (application.getKeystore() == null) {
      application.setKeystore(new Keystore());
    }

    Keystore keystoreConfig = application.getKeystore();

    if (StringUtils.isNotEmpty(pass)) {
      keystoreConfig.setPassword(pass);
    } else if (StringUtils.isEmpty(keystoreConfig.getPassword())) {
      throw new LoadKeyStoreException(
          "Fail to retrieve the user keystore password. Application: " + application.getId());
    }

    if (StringUtils.isNotEmpty(data)) {
      return loadDataKeystore(data, keystoreConfig.getType(), keystoreConfig.getPassword());
    }

    String defaultFilename = application.getId() + DEFAULT_KEYSTORE_TYPE_SUFFIX;

    return loadKeyStore(certsDir, keystoreConfig, defaultFilename);
  }

  /**
   * Load the application keystore file.
   *
   * @param certsDir Certificate directory
   * @param application Application settings
   * @param integrationUser Integration username
   * @return Keystore object
   */
  protected KeyStore loadAppKeyStore(String certsDir, Application application, String integrationUser) {
    if (application == null) {
      throw new LoadKeyStoreException(
          "Fail to retrieve the app keystore password. Application: " + integrationUser);
    }

    String dataEnv = String.format(APP_KEYSTORE_DATA_TEMPLATE, application.getId());
    String passEnv = String.format(APP_KEYSTORE_PASS_TEMPLATE, application.getId());

    String data = environment.getProperty(dataEnv);
    String pass = environment.getProperty(passEnv);

    if (application.getAppKeystore() == null) {
      application.setAppKeystore(new Keystore());
    }

    Keystore keystoreConfig = application.getAppKeystore();

    if (StringUtils.isNotEmpty(pass)) {
      keystoreConfig.setPassword(pass);
    } else if (StringUtils.isEmpty(keystoreConfig.getPassword())) {
      throw new LoadKeyStoreException(
          "Fail to retrieve the app keystore password. Application: " + application.getId());
    }

    if (StringUtils.isNotEmpty(data)) {
      return loadDataKeystore(data, keystoreConfig.getType(), keystoreConfig.getPassword());
    }

    String defaultFilename = application.getId() + DEFAULT_KEYSTORE_APP_SUFFIX + DEFAULT_KEYSTORE_TYPE_SUFFIX;
    return loadKeyStore(certsDir, keystoreConfig, defaultFilename);
  }

  /**
   * Load the keystore file
   *
   * @param certsDir Certificate directory
   * @param keystoreSettings Keystore settings
   * @param defaultFilename Default keystore filename
   * @return Keystore object
   */
  private KeyStore loadKeyStore(String certsDir, Keystore keystoreSettings, String defaultFilename) {
    String locationFile = keystoreSettings.getFile();

    if (StringUtils.isBlank(locationFile)) {
      locationFile = defaultFilename;
    }

    String storeLocation = certsDir + locationFile;

    try(FileInputStream inputStream = new FileInputStream(storeLocation)) {
      String type = keystoreSettings.getType();
      String password = keystoreSettings.getPassword();

      final KeyStore ks = KeyStore.getInstance(type);
      ks.load(inputStream, password.toCharArray());

      return ks;
    } catch (GeneralSecurityException | IOException e) {
      throw new LoadKeyStoreException(
          String.format("Fail to load keystore file at %s", storeLocation), e);
    }
  }

  /**
   * Load the keystore from Base64 encoded string
   *
   * @param data Base64 encoded string
   * @param type Keystore type
   * @param password Keystore password
   * @return Keystore object
   */
  private KeyStore loadDataKeystore(String data, String type, String password) {
    byte[] keystoreBytes = Base64.decodeBase64(data.getBytes());

    try (ByteArrayInputStream bis = new ByteArrayInputStream(keystoreBytes)) {
      final KeyStore ks = KeyStore.getInstance(type);
      ks.load(bis, password.toCharArray());

      return ks;
    } catch (GeneralSecurityException | IOException e) {
      throw new LoadKeyStoreException(String.format("Fail to load keystore data"), e);
    }
  }

  public IntegrationHealthManager getHealthManager() {
    return healthManager;
  }
}

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.symphonyoss.integration.model.healthcheck.IntegrationFlags.ValueEnum.NOK;
import static org.symphonyoss.integration.model.healthcheck.IntegrationFlags.ValueEnum.OK;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.authentication.api.AppAuthenticationProxy;
import org.symphonyoss.integration.exception.ExceptionMessageFormatter;
import org.symphonyoss.integration.exception.bootstrap.CertificateNotFoundException;
import org.symphonyoss.integration.exception.bootstrap.LoadKeyStoreException;
import org.symphonyoss.integration.exception.bootstrap.UnexpectedBootstrapException;
import org.symphonyoss.integration.healthcheck.IntegrationHealthManager;
import org.symphonyoss.integration.model.healthcheck.IntegrationHealth;
import org.symphonyoss.integration.model.yaml.Application;
import org.symphonyoss.integration.model.yaml.IntegrationProperties;
import org.symphonyoss.integration.model.yaml.Keystore;
import org.symphonyoss.integration.utils.IntegrationUtils;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Collections;

import javax.ws.rs.client.Client;

/**
 * Test class to validate {@link BaseIntegration}
 * Created by rsanchez on 22/11/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseIntegrationTest extends MockKeystore {

  private static final String APP_ID = "jira";

  private static final String APP_TYPE = "jiraWebHookIntegration";

  private static final String DEFAULT_KEYSTORE_PASSWORD = "changeit";

  private static final String NOT_AVAILABLE = "N/A";

  @Mock
  private AuthenticationProxy authenticationProxy;

  @Mock
  private AppAuthenticationProxy appAuthenticationProxy;

  @Spy
  private IntegrationProperties properties = new IntegrationProperties();

  @Mock
  private Client client;

  @Mock
  private IntegrationUtils utils;

  private IntegrationHealthManager healthManager = new IntegrationHealthManager();

  @InjectMocks
  private MockIntegration integration =
      new MockIntegration(properties, utils, authenticationProxy, healthManager);

  private Application application;

  @Before
  public void init() {
    Keystore keystore = new Keystore();
    keystore.setPassword(DEFAULT_KEYSTORE_PASSWORD);

    this.application = new Application();
    this.application.setComponent(APP_TYPE);
    this.application.setKeystore(keystore);
    this.application.setAppKeystore(keystore);

    properties.setApplications(Collections.singletonMap(APP_ID, application));
  }

  @Test
  public void testSetupHealthManager() {
    integration.setupHealthManager(APP_TYPE);

    IntegrationHealth health = integration.getHealthManager().getHealth();

    assertEquals(NOT_AVAILABLE, health.getVersion());
    assertEquals(APP_ID, health.getName());
  }

  @Test
  public void testRegisterUserCertNotFound() {
    doThrow(new CertificateNotFoundException()).when(utils).getCertsDirectory();

    try {
      integration.registerUser(APP_TYPE);
      fail();
    } catch (CertificateNotFoundException e) {
      String message = "Certificate folder not found";
      assertEquals(ExceptionMessageFormatter.format("Integration Bootstrap", message), e.getMessage());
      assertEquals(NOK, integration.getHealthStatus().getFlags().getUserCertificateInstalled());
      assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), integration.getHealthStatus().getStatus());
    }
  }

  @Test
  public void testRegisterUserCertFileUnknown() throws IOException {
    properties.setApplications(Collections.<String, Application>emptyMap());

    String dir = mockCertDir();
    doReturn(dir).when(utils).getCertsDirectory();

    try {
      integration.registerUser(APP_TYPE);
      fail();
    } catch (LoadKeyStoreException e) {
      String message = "Fail to retrieve the user keystore password. Application: " + APP_TYPE;
      String formattedMessage =
          ExceptionMessageFormatter.format("Integration Bootstrap", message, e.getCause());
      assertEquals(formattedMessage, e.getMessage());
      assertEquals(NOK, integration.getHealthStatus().getFlags().getUserCertificateInstalled());
      assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), integration.getHealthStatus().getStatus());
    }
  }

  @Test
  public void testRegisterUserLoadKeystoreException() throws IOException {
    String dir = mockCertDir();
    doReturn(dir).when(utils).getCertsDirectory();

    try {
      integration.registerUser(APP_TYPE);
      fail();
    } catch (LoadKeyStoreException e) {
      String message = "Fail to load keystore file at " + dir + APP_ID + ".p12";
      String formattedMessage =
          ExceptionMessageFormatter.format("Integration Bootstrap", message, e.getCause());
      assertEquals(formattedMessage, e.getMessage());
      assertEquals(NOK, integration.getHealthStatus().getFlags().getUserCertificateInstalled());
      assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), integration.getHealthStatus().getStatus());
    }
  }

  @Test
  public void testRegisterUserRuntimeException()
      throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
    String dir = mockKeyStore();
    doReturn(dir).when(utils).getCertsDirectory();

    doThrow(RuntimeException.class).when(authenticationProxy)
        .registerUser(eq(APP_TYPE), any(KeyStore.class), eq(DEFAULT_KEYSTORE_PASSWORD));

    try {
      integration.registerUser(APP_TYPE);
      fail();
    } catch (UnexpectedBootstrapException e) {
      String message = "Something went wrong when bootstrapping the integration " + APP_TYPE;
      String formattedMessage =
          ExceptionMessageFormatter.format("Integration Bootstrap", message, e.getCause());
      assertEquals(formattedMessage, e.getMessage());
      assertEquals(NOK, integration.getHealthStatus().getFlags().getUserCertificateInstalled());
      assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), integration.getHealthStatus().getStatus());
    }
  }

  @Test
  public void testRegisterUser()
      throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
    String dir = mockKeyStore();
    doReturn(dir).when(utils).getCertsDirectory();

    integration.registerUser(APP_TYPE);

    assertEquals(OK, integration.getHealthStatus().getFlags().getUserCertificateInstalled());
  }

  @Test
  public void testRegisterAppCertNotFound() {
    doThrow(new CertificateNotFoundException()).when(utils).getCertsDirectory();

    try {
      integration.registerApp(APP_TYPE);
      fail();
    } catch (CertificateNotFoundException e) {
      String message = "Certificate folder not found";
      assertEquals(ExceptionMessageFormatter.format("Integration Bootstrap", message), e.getMessage());
      assertEquals(NOK, integration.getHealthStatus().getFlags().getAppCertificateInstalled());
      assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), integration.getHealthStatus().getStatus());
    }
  }

  @Test
  public void testRegisterAppCertFileUnknown() throws IOException {
    properties.setApplications(Collections.<String, Application>emptyMap());

    String dir = mockCertDir();
    doReturn(dir).when(utils).getCertsDirectory();

    try {
      integration.registerApp(APP_TYPE);
      fail();
    } catch (LoadKeyStoreException e) {
      String message = "Fail to retrieve the app keystore password. Application: " + APP_TYPE;
      String formattedMessage =
          ExceptionMessageFormatter.format("Integration Bootstrap", message, e.getCause());
      assertEquals(formattedMessage, e.getMessage());
      assertEquals(NOK, integration.getHealthStatus().getFlags().getAppCertificateInstalled());
      assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), integration.getHealthStatus().getStatus());
    }
  }

  @Test
  public void testRegisterAppLoadKeystoreException() throws IOException {
    String dir = mockCertDir();
    doReturn(dir).when(utils).getCertsDirectory();

    try {
      integration.registerApp(APP_TYPE);
      fail();
    } catch (LoadKeyStoreException e) {
      String message = "Fail to load keystore file at " + dir + APP_ID + "_app.p12";
      String formattedMessage =
          ExceptionMessageFormatter.format("Integration Bootstrap", message, e.getCause());
      assertEquals(formattedMessage, e.getMessage());
      assertEquals(NOK, integration.getHealthStatus().getFlags().getAppCertificateInstalled());
      assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), integration.getHealthStatus().getStatus());
    }
  }

  @Test
  public void testRegisterAppRuntimeException()
      throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
    String dir = mockKeyStore("jira_app.p12");
    doReturn(dir).when(utils).getCertsDirectory();

    doThrow(RuntimeException.class).when(appAuthenticationProxy)
        .registerApplication(eq(APP_ID), any(KeyStore.class), eq(DEFAULT_KEYSTORE_PASSWORD));

    try {
      integration.registerApp(APP_TYPE);
      fail();
    } catch (UnexpectedBootstrapException e) {
      String message = "Something went wrong when bootstrapping the integration " + APP_TYPE;
      String formattedMessage =
          ExceptionMessageFormatter.format("Integration Bootstrap", message, e.getCause());
      assertEquals(formattedMessage, e.getMessage());
      assertEquals(NOK, integration.getHealthStatus().getFlags().getAppCertificateInstalled());
      assertEquals(IntegrationStatus.FAILED_BOOTSTRAP.name(), integration.getHealthStatus().getStatus());
    }
  }

  @Test
  public void testRegisterApp()
      throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
    String dir = mockKeyStore("jira_app.p12");
    doReturn(dir).when(utils).getCertsDirectory();

    integration.registerApp(APP_TYPE);

    assertEquals(OK, integration.getHealthStatus().getFlags().getAppCertificateInstalled());
  }
}

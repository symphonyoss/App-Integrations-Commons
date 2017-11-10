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

package org.symphonyoss.integration.authentication.api;

import org.symphonyoss.integration.authentication.api.model.AppToken;
import org.symphonyoss.integration.authentication.api.model.PodCertificate;

import java.security.KeyStore;

import javax.ws.rs.client.Client;

/**
 * Perform the app authentication and keep the tokens for each application.
 *
 * Created by rsanchez on 07/08/17.
 */
public interface AppAuthenticationProxy {

  /**
   * Should be invoked by the integration to register their keystores.
   *
   * @param applicationId Application identifier
   * @param keyStore Application keystore
   * @param keyStorePassword Keystore password
   */
  void registerApplication(String applicationId, KeyStore keyStore, String keyStorePassword);

  /**
   * Retrieves an HTTP client build with the proper SSL context for the application.
   *
   * @param applicationId Application identifier
   */
  Client httpClientForApplication(String applicationId, String serviceName);

  /**
   * Authenticates application on the POD.
   *
   * @param appId Application identifier
   * @param appToken Application token
   * @return Token pair that contains application token and symphony token
   */
  AppToken authenticate(String appId, String appToken);

  /**
   * Retrieve and return the POD public certificate in PEM format.
   * @param appId Application identifier
   * @return The found certificate.
   */
  PodCertificate getPodPublicCertificate(String appId);
}

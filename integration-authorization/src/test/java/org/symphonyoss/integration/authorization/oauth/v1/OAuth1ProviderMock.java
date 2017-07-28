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

package org.symphonyoss.integration.authorization.oauth.v1;

import java.net.URL;

/**
 * Implementation of the abstract class {@link OAuth1Provider}.
 * Created by campidelli on 26-jul-17.
 */
public class OAuth1ProviderMock extends OAuth1Provider {

  private boolean configured;
  private String consumerKey;
  private String privateKey;
  private URL requestTemporaryTokenUrl;
  private URL authorizationCallbackUrl;
  private URL authorizeTemporaryTokenUrl;
  private URL requestAccessTokenUrl;

  public OAuth1ProviderMock(String consumerKey, String privateKey,
      URL requestTemporaryTokenUrl, URL authorizationCallbackUrl,
      URL authorizeTemporaryTokenUrl, URL requestAccessTokenUrl) {
    this.consumerKey = consumerKey;
    this.privateKey = privateKey;
    this.requestTemporaryTokenUrl = requestTemporaryTokenUrl;
    this.authorizationCallbackUrl = authorizationCallbackUrl;
    this.authorizeTemporaryTokenUrl = authorizeTemporaryTokenUrl;
    this.requestAccessTokenUrl = requestAccessTokenUrl;
    this.configured = true;
  }

  void setConfigured(boolean configured) {
    this.configured = configured;
  }

  @Override
  protected boolean isConfigured() {
    return configured;
  }

  @Override
  public String getConsumerKey() {
    return consumerKey;
  }

  @Override
  public String getPrivateKey() {
    return privateKey;
  }

  @Override
  public URL getRequestTemporaryTokenUrl() {
    return requestTemporaryTokenUrl;
  }

  @Override
  public URL getAuthorizationCallbackUrl() {
    return authorizationCallbackUrl;
  }

  @Override
  public URL getAuthorizeTemporaryTokenUrl() {
    return authorizeTemporaryTokenUrl;
  }

  @Override
  public URL getRequestAccessTokenUrl() {
    return requestAccessTokenUrl;
  }
}

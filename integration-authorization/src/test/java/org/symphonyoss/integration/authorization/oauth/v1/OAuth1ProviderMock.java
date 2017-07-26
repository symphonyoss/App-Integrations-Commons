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

/**
 * Implementation of the abstract class {@link OAuth1Provider}.
 * Created by campidelli on 26-jul-17.
 */
public class OAuth1ProviderMock extends OAuth1Provider {

  private String consumerKey;
  private String privateKey;
  private String requestTemporaryTokenUrl;
  private String authorizationCallbackUrl;
  private String authorizeTemporaryTokenUrl;
  private String requestAccessTokenUrl;

  public OAuth1ProviderMock(String consumerKey, String privateKey,
      String requestTemporaryTokenUrl, String authorizationCallbackUrl,
      String authorizeTemporaryTokenUrl, String requestAccessTokenUrl) {
    this.consumerKey = consumerKey;
    this.privateKey = privateKey;
    this.requestTemporaryTokenUrl = requestTemporaryTokenUrl;
    this.authorizationCallbackUrl = authorizationCallbackUrl;
    this.authorizeTemporaryTokenUrl = authorizeTemporaryTokenUrl;
    this.requestAccessTokenUrl = requestAccessTokenUrl;
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
  public String getRequestTemporaryTokenUrl() {
    return requestTemporaryTokenUrl;
  }

  @Override
  public String getAuthorizationCallbackUrl() {
    return authorizationCallbackUrl;
  }

  @Override
  public String getAuthorizeTemporaryTokenUrl() {
    return authorizeTemporaryTokenUrl;
  }

  @Override
  public String getRequestAccessTokenUrl() {
    return requestAccessTokenUrl;
  }
}

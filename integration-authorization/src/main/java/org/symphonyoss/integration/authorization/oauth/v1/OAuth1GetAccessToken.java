/**
 * Copyright 2016-2017 Symphony Integrations - Symphony LLC
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.symphonyoss.integration.authorization.oauth.v1;

import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth.OAuthRsaSigner;
import com.google.api.client.http.apache.ApacheHttpTransport;
import org.symphonyoss.integration.authorization.oauth.OAuthRsaSignerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Holds all necessary attributes to start the OAuth dance by getting a temporary token.
 *
 * Created by campidelli on 24-jul-2017.
 */
public class OAuth1GetAccessToken extends OAuthGetAccessToken {

  /**
   * Create a new OAuth1GetTemporaryToken.
   * @param requestAccessTokenUrl encoded authorization server URL.
   * @param consumerKey consumer key
   * @param privateKey  private key in PKCS8 format
   * @param temporaryToken temporary token.
   * @param verifier verifier code authorized by the user.
   */
  public OAuth1GetAccessToken(URL requestAccessTokenUrl, String consumerKey,
      OAuthRsaSigner privateKey, String temporaryToken, String verifier) {
    super(requestAccessTokenUrl.toString());
    this.usePost = true;
    this.consumerKey = consumerKey;
    this.transport = new ApacheHttpTransport();
    this.signer = privateKey;
    this.temporaryToken = temporaryToken;
    this.verifier = verifier;
  }

  /**
   * Request the access token and return its value.
   * @return Access token value
   * @throws IOException Thrown when the requested server is down.
   */
  public String getValue() throws IOException {
    return execute().token;
  }
}

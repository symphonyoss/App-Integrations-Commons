package org.symphonyoss.integration.authorization.oauth.v1;

import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.http.apache.ApacheHttpTransport;
import org.symphonyoss.integration.authorization.oauth.OAuthRsaSignerFactory;

/**
 * Holds all necessary attributes to start the OAuth dance by getting a temporary token.
 *
 * Created by campidelli on 24-jul-2017.
 */
public class OAuth1GetTemporaryToken extends OAuthGetTemporaryToken {

  /**
   * Create a new OAuth1GetTemporaryToken.
   * @param requestTemporaryTokenUrl encoded authorization server URL.
   * @param consumerKey consumer key
   * @param privateKey  private key in PKCS8 format
   * @param authorizationCallbackUrl encoded callback URL.
   */
  public OAuth1GetTemporaryToken(String requestTemporaryTokenUrl, String consumerKey,
      String privateKey, String authorizationCallbackUrl) {
    super(requestTemporaryTokenUrl);
    this.usePost = true;
    this.consumerKey = consumerKey;
    this.transport = new ApacheHttpTransport();
    this.callback = authorizationCallbackUrl;
    this.signer = OAuthRsaSignerFactory.getOAuthRsaSigner(privateKey);
  }
}

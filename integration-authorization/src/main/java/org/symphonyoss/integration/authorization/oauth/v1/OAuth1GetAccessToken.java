package org.symphonyoss.integration.authorization.oauth.v1;

import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.http.apache.ApacheHttpTransport;
import org.symphonyoss.integration.authorization.oauth.OAuthRsaSignerFactory;

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
  public OAuth1GetAccessToken(String requestAccessTokenUrl, String consumerKey, String privateKey,
      String temporaryToken, String verifier) {
    super(requestAccessTokenUrl);
    this.usePost = true;
    this.consumerKey = consumerKey;
    this.transport = new ApacheHttpTransport();
    this.signer = OAuthRsaSignerFactory.getOAuthRsaSigner(privateKey);
    this.temporaryToken = temporaryToken;
    this.verifier = verifier;
  }
}

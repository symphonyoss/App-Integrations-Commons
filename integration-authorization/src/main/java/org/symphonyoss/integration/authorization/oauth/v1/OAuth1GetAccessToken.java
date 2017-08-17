package org.symphonyoss.integration.authorization.oauth.v1;

import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.auth.oauth.OAuthRsaSigner;
import com.google.api.client.http.apache.ApacheHttpTransport;

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

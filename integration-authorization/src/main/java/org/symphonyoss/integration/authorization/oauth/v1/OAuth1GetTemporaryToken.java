package org.symphonyoss.integration.authorization.oauth.v1;

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
public class OAuth1GetTemporaryToken extends OAuthGetTemporaryToken {

  /**
   * Create a new OAuth1GetTemporaryToken.
   * @param requestTemporaryTokenUrl encoded authorization server URL.
   * @param consumerKey consumer key
   * @param privateKey  private key in PKCS8 format
   * @param authorizationCallbackUrl encoded callback URL.
   */
  public OAuth1GetTemporaryToken(URL requestTemporaryTokenUrl, String consumerKey,
      OAuthRsaSigner privateKey, URL authorizationCallbackUrl) {
    super(requestTemporaryTokenUrl.toString());
    this.usePost = true;
    this.consumerKey = consumerKey;
    this.transport = new ApacheHttpTransport();
    this.callback = authorizationCallbackUrl.toString();
    this.signer = privateKey;
  }

  /**
   * Request the temporary token and return its value.
   * @return Temporary token value
   * @throws IOException Thrown when the requested server is down.
   */
  public String getValue() throws IOException {
    return execute().token;
  }
}

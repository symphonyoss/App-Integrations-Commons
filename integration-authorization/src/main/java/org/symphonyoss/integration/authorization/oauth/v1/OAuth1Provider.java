package org.symphonyoss.integration.authorization.oauth.v1;

import com.google.api.client.auth.oauth.OAuthAuthorizeTemporaryTokenUrl;

import java.io.IOException;

/**
 * Provides a generic implementation of OAuth 1.0 basic dance.
 *
 * Created by campidelli on 24-jul-2017.
 */
public abstract class OAuth1Provider {

  public abstract String getConsumerKey();

  public abstract String getPrivateKey();

  public abstract String getRequestTemporaryTokenUrl();

  public abstract String getAuthorizationCallbackUrl();

  public abstract String getAuthorizeTemporaryTokenUrl();

  public abstract String getRequestAccessTokenUrl();

  /**
   * Starts the OAuth dance by asking for a temporary token.
   * @return Temporary tokenL.
   */
  public String requestTemporaryToken() {
    OAuth1GetTemporaryToken temporaryToken = new OAuth1GetTemporaryToken(
        getRequestTemporaryTokenUrl(), getConsumerKey(), getPrivateKey(),
        getAuthorizationCallbackUrl());
    try {
      return temporaryToken.execute().token;
    } catch (IOException e) {
      throw new OAuth1Exception("'Request temporary token' server url is invalid or unreachable.",
          e, "Verify if the informed 'Request temporary token' server url is valid and reachable.");
    }
  }

  /**
   * Return a URL to be authorized by the user.
   * @param temporaryToken The previously generated temporary token.
   * @return Authorization URL.
   */
  public String requestAuthorizationUrl(String temporaryToken) {
    String baseUrl = getAuthorizeTemporaryTokenUrl();

    OAuthAuthorizeTemporaryTokenUrl authorizationURL = new OAuthAuthorizeTemporaryTokenUrl(baseUrl);
    authorizationURL.temporaryToken = temporaryToken;

    return authorizationURL.toString();
  }

  /**
   * Ends the OAuth dance by requesting a access token. This token will be used in the future to
   * make authorized API calls.
   * @param temporaryToken temporary token.
   * @param verifier verifier code authorized by the user.
   * @return Authorization URL.
   */
  public String requestAcessToken(String temporaryToken, String verifier) {
    OAuth1GetAccessToken accessToken = new OAuth1GetAccessToken(
        getRequestAccessTokenUrl(), getConsumerKey(), getPrivateKey(),
        temporaryToken, verifier);
    try {
      return accessToken.execute().token;
    } catch (IOException e) {
      throw new OAuth1Exception("'Request access token' server url is invalid or unreachable.",
          e, "Verify if the informed 'Request access token' server url is valid and reachable.");
    }
  }
}

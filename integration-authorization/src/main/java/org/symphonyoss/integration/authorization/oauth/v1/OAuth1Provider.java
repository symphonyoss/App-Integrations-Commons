package org.symphonyoss.integration.authorization.oauth.v1;

import com.google.api.client.auth.oauth.OAuthAuthorizeTemporaryTokenUrl;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.auth.oauth.OAuthRsaSigner;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.symphonyoss.integration.authorization.oauth.OAuthRsaSignerFactory;
import org.symphonyoss.integration.logging.LogMessageSource;

import java.io.IOException;
import java.net.URL;

/**
 * Provides a generic implementation of OAuth 1.0 basic dance.
 *
 * Created by campidelli on 24-jul-2017.
 */
public abstract class OAuth1Provider {

  @Autowired
  LogMessageSource logMessage;

  @Autowired
  OAuthRsaSignerFactory rsaSignerFactory;

  /**
   * Starts the OAuth dance by asking for a temporary token.
   * @return Temporary tokenL.
   */
  public String requestTemporaryToken() {
    checkConfiguration();

    OAuthRsaSigner rsaSigner = rsaSignerFactory.getOAuthRsaSigner(getPrivateKey());
    OAuth1GetTemporaryToken temporaryToken = new OAuth1GetTemporaryToken(
        getRequestTemporaryTokenUrl(), getConsumerKey(), rsaSigner,
        getAuthorizationCallbackUrl());
    try {
      return temporaryToken.getValue();
    } catch (IOException e) {
      throw new OAuth1Exception(
          logMessage.getMessage("integration.authorization.request.temp.token"), e,
          logMessage.getMessage("integration.authorization.request.temp.token.solution"));
    }
  }

  /**
   * Return a URL to be authorized by the user.
   * @param temporaryToken The previously generated temporary token.
   * @return Authorization URL.
   */
  public String requestAuthorizationUrl(String temporaryToken) {
    checkConfiguration();

    String baseUrl = getAuthorizeTemporaryTokenUrl().toString();

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
    checkConfiguration();

    OAuthRsaSigner rsaSigner = rsaSignerFactory.getOAuthRsaSigner(getPrivateKey());
    OAuth1GetAccessToken accessToken = new OAuth1GetAccessToken(
        getRequestAccessTokenUrl(), getConsumerKey(), rsaSigner,
        temporaryToken, verifier);
    try {
      return accessToken.getValue();
    } catch (IOException e) {
      throw new OAuth1Exception(
          logMessage.getMessage("integration.authorization.request.access.token"), e,
          logMessage.getMessage("integration.authorization.request.access.token.solution"));
    }
  }

  /**
   * Performs an authenticated call to the specified URL, using the informed HTTP method and
   * content when necessary (Optional, required only for POST and PUT).
   * @param accessToken Previously authenticated access token.
   * @param resourceUrl URL to the required resource.
   * @param httpMethod HTTP method to be used (GET, POST, PUT or DELETE).
   * @param httpContent HTTP POST or PUT content to be sent.
   * @return HttpResponse contaning the status code and response data.
   */
  public HttpResponse makeAuthorizedRequest(String accessToken, URL resourceUrl, String httpMethod,
      HttpContent httpContent) {
    checkConfiguration();

    OAuthParameters parameters = new OAuthParameters();
    parameters.consumerKey = getConsumerKey();
    parameters.signer = rsaSignerFactory.getOAuthRsaSigner(getPrivateKey());
    parameters.token = accessToken;

    HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(parameters);

    try {
      HttpRequest request = requestFactory.buildRequest(httpMethod, new GenericUrl(resourceUrl),
          httpContent);
      return request.execute();
    } catch (IOException e) {
      throw new OAuth1Exception(
          logMessage.getMessage("integration.authorization.make.request.invalid"), e,
          logMessage.getMessage("integration.authorization.make.request.invalid.solution"));
    }
  }

  private void checkConfiguration() {
    if (!isConfigured()) {
      throw new OAuth1Exception(
          logMessage.getMessage("integration.authorization.auth.is.not.configured"),
          logMessage.getMessage("integration.authorization.auth.is.not.configured.solution"));
    }
  }

  /**
   * @return <code>true</code> when this provider is configured and ready to be used.
   */
  protected abstract boolean isConfigured();

  /**
   * @return The consumer key used to identify the third-party app.
   */
  public abstract String getConsumerKey();

  /**
   * @return Private key used to start the OAuth dance.
   */
  public abstract String getPrivateKey();

  /**
   * @return The URL used to request the temporary token.
   */
  public abstract URL getRequestTemporaryTokenUrl();

  /**
   * @return The URL to be called when the user authorize the app usage.
   */
  public abstract URL getAuthorizationCallbackUrl();

  /**
   * @return The URL used to show to the user a page with the authorization form.
   */
  public abstract URL getAuthorizeTemporaryTokenUrl();

  /**
   * @return The URL used to request the access token.
   */
  public abstract URL getRequestAccessTokenUrl();
}

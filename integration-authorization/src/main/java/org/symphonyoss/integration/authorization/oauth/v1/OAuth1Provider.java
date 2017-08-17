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
import org.symphonyoss.integration.utils.RsaKeyUtils;
import org.symphonyoss.integration.logging.LogMessageSource;

import java.io.IOException;
import java.net.URL;

/**
 * Provides a generic implementation of OAuth 1.0 basic dance.
 *
 * Created by campidelli on 24-jul-2017.
 */
public abstract class OAuth1Provider {

  private static final String REQUEST_TEMP_TOKEN = "integration.authorization.request.temp.token";

  private static final String REQUEST_TEMP_TOKEN_SOLUTION = REQUEST_TEMP_TOKEN + ".solution";

  private static final String REQUEST_ACCESS_TOKEN = "integration.authorization.request.accesstoken";

  private static final String REQUEST_ACCESS_TOKEN_SOLUTION = REQUEST_ACCESS_TOKEN + ".solution";

  private static final String INVALID_REQUEST = "integration.authorization.make.request.invalid";

  private static final String INVALID_REQUEST_SOLUTION = INVALID_REQUEST + ".solution";

  private static final String NOT_CONFIGURED = "integration.authorization.auth.is.not.configured";

  private static final String NOT_CONFIGURED_SOLUTION = NOT_CONFIGURED + ".solution";

  @Autowired
  private LogMessageSource logMessage;

  @Autowired
  private RsaKeyUtils rsaSignerFactory;

  /**
   * Starts the OAuth dance by asking for a temporary token.
   * @return Temporary tokenL.
   * @throws OAuth1Exception when there is a problem with this operation.
   */
  public String requestTemporaryToken() throws OAuth1Exception {
    checkConfiguration();

    OAuthRsaSigner rsaSigner = rsaSignerFactory.getOAuthRsaSigner(getPrivateKey());
    OAuth1GetTemporaryToken temporaryToken = new OAuth1GetTemporaryToken(
        getRequestTemporaryTokenUrl(), getConsumerKey(), rsaSigner,
        getAuthorizationCallbackUrl());
    try {
      return temporaryToken.getValue();
    } catch (IOException e) {
      throw new OAuth1Exception(logMessage.getMessage(REQUEST_TEMP_TOKEN), e,
          logMessage.getMessage(REQUEST_TEMP_TOKEN_SOLUTION));
    }
  }

  /**
   * Return a URL to be authorized by the user.
   * @param temporaryToken The previously generated temporary token.
   * @return Authorization URL.
   * @throws OAuth1Exception when there is a problem with this operation.
   */
  public String requestAuthorizationUrl(String temporaryToken) throws OAuth1Exception {
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
   * @throws OAuth1Exception when there is a problem with this operation.
   */
  public String requestAcessToken(String temporaryToken, String verifier) throws OAuth1Exception {
    checkConfiguration();

    OAuthRsaSigner rsaSigner = rsaSignerFactory.getOAuthRsaSigner(getPrivateKey());
    OAuth1GetAccessToken accessToken = new OAuth1GetAccessToken(
        getRequestAccessTokenUrl(), getConsumerKey(), rsaSigner,
        temporaryToken, verifier);
    try {
      return accessToken.getValue();
    } catch (IOException e) {
      throw new OAuth1Exception(logMessage.getMessage(REQUEST_ACCESS_TOKEN), e,
          logMessage.getMessage(REQUEST_ACCESS_TOKEN_SOLUTION));
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
   * @throws OAuth1Exception when there is a problem with this operation.
   */
  public HttpResponse makeAuthorizedRequest(String accessToken, URL resourceUrl, String httpMethod,
      HttpContent httpContent) throws OAuth1Exception {
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
      throw new OAuth1Exception(logMessage.getMessage(INVALID_REQUEST), e,
          logMessage.getMessage(INVALID_REQUEST_SOLUTION));
    }
  }

  private void checkConfiguration() throws OAuth1Exception {
    if (!isConfigured()) {
      throw new OAuth1Exception(logMessage.getMessage(NOT_CONFIGURED), logMessage.getMessage(NOT_CONFIGURED_SOLUTION));
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

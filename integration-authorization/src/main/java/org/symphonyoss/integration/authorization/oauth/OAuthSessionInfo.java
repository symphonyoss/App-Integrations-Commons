package org.symphonyoss.integration.authorization.oauth;

/**
 * Wraps all information about a OAuth Session, user and instance/integration URL.
 * Created by campidelli on 25-jul-17.
 */
public abstract class OAuthSessionInfo {

  private String consumerKey;
  private String privateKey;
  private String temporaryToken;
  private String verifierCode;
  private String accessToken;
  private String requestTemporaryTokenUrl;
  private String authorizeTemporaryTokenUrl;
  private String authorizationCallbackUrl;
  private String requestAccessTokenUrl;

  public String getConsumerKey() {
    return consumerKey;
  }

  public void setConsumerKey(String consumerKey) {
    this.consumerKey = consumerKey;
  }

  public String getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  public String getTemporaryToken() {
    return temporaryToken;
  }

  public void setTemporaryToken(String temporaryToken) {
    this.temporaryToken = temporaryToken;
  }

  public String getVerifierCode() {
    return verifierCode;
  }

  public void setVerifierCode(String verifierCode) {
    this.verifierCode = verifierCode;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRequestTemporaryTokenUrl() {
    return requestTemporaryTokenUrl;
  }

  public void setRequestTemporaryTokenUrl(String requestTemporaryTokenUrl) {
    this.requestTemporaryTokenUrl = requestTemporaryTokenUrl;
  }

  public String getAuthorizationCallbackUrl() {
    return authorizationCallbackUrl;
  }

  public void setAuthorizationCallbackUrl(String authorizationCallbackUrl) {
    this.authorizationCallbackUrl = authorizationCallbackUrl;
  }

  public String getAuthorizeTemporaryTokenUrl() {
    return authorizeTemporaryTokenUrl;
  }

  public void setAuthorizeTemporaryTokenUrl(String authorizeTemporaryTokenUrl) {
    this.authorizeTemporaryTokenUrl = authorizeTemporaryTokenUrl;
  }

  public String getRequestAccessTokenUrl() {
    return requestAccessTokenUrl;
  }

  public void setRequestAccessTokenUrl(String requestAccessTokenUrl) {
    this.requestAccessTokenUrl = requestAccessTokenUrl;
  }
}

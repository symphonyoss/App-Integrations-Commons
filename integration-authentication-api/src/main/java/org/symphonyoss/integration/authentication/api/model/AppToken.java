package org.symphonyoss.integration.authentication.api.model;

/**
 * Holds the application ID, application token and symphony token. This class will be used to
 * authenticate applications on the POD.
 *
 * Created by rsanchez on 08/08/17.
 */
public class AppToken {

  private String appId;

  private String appToken;

  private String symphonyToken;

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getAppToken() {
    return appToken;
  }

  public void setAppToken(String appToken) {
    this.appToken = appToken;
  }

  public String getSymphonyToken() {
    return symphonyToken;
  }

  public void setSymphonyToken(String symphonyToken) {
    this.symphonyToken = symphonyToken;
  }
}

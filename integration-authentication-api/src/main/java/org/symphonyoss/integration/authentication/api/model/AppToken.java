package org.symphonyoss.integration.authentication.api.model;

import java.util.Objects;

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

  public AppToken() { }

  public AppToken(String appId, String appToken, String symphonyToken) {
    this.appId = appId;
    this.appToken = appToken;
    this.symphonyToken = symphonyToken;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    AppToken appToken1 = (AppToken) o;
    return Objects.equals(appId, appToken1.appId) &&
        Objects.equals(appToken, appToken1.appToken) &&
        Objects.equals(symphonyToken, appToken1.symphonyToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appId, appToken, symphonyToken);
  }
}

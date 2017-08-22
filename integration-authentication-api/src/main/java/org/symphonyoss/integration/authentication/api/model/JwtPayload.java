package org.symphonyoss.integration.authentication.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

/**
 * Holds the Symphony's user authentication data as a JWT.
 *
 * Created by campidelli on 15/08/17.
 */
public class JwtPayload {

  @JsonProperty("aud")
  private String applicationId;

  @JsonProperty("iss")
  private String companyName;

  @JsonProperty("sub")
  private String userId;

  @JsonProperty("exp")
  private Long expirationDateInSeconds;

  private JwtUser user;

  public JwtPayload() {
  }

  public JwtPayload(String applicationId, String companyName, String userId,
      Long expirationDateInSeconds, JwtUser user) {
    this.applicationId = applicationId;
    this.companyName = companyName;
    this.userId = userId;
    this.expirationDateInSeconds = expirationDateInSeconds;
    this.user = user;
  }

  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Long getExpirationDateInSeconds() {
    return expirationDateInSeconds;
  }

  public void setExpirationDateInSeconds(Long expirationDateInSeconds) {
    this.expirationDateInSeconds = expirationDateInSeconds;
  }

  public JwtUser getUser() {
    return user;
  }

  public void setUser(JwtUser user) {
    this.user = user;
  }

  public Date getExpirationDate() {
    if (expirationDateInSeconds == null || expirationDateInSeconds == 0) {
      return null;
    }
    return new Date(Long.valueOf(expirationDateInSeconds + "000"));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    JwtPayload that = (JwtPayload) o;
    return Objects.equals(applicationId, that.applicationId) &&
        Objects.equals(companyName, that.companyName) &&
        Objects.equals(userId, that.userId) &&
        Objects.equals(expirationDateInSeconds, that.expirationDateInSeconds) &&
        Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationId, companyName, userId, expirationDateInSeconds, user);
  }
}

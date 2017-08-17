package org.symphonyoss.integration.authentication.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

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
  private Date expirationDate;

  private JwtUser user;

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

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  public JwtUser getUser() {
    return user;
  }

  public void setUser(JwtUser user) {
    this.user = user;
  }
}

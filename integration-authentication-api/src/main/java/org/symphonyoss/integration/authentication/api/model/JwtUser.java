package org.symphonyoss.integration.authentication.api.model;

import java.util.Objects;

/**
 * Holds the JWT authentication user data
 * Created by campidelli on 16/08/17.
 */
public class JwtUser {

  private String id;
  private String emailAddress;
  private String username;
  private String firstName;
  private String lastName;
  private String displayName;
  private String title;
  private String company;
  private String companyId;
  private String location;
  private String avatarUrl;
  private String avatarSmallUrl;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getAvatarSmallUrl() {
    return avatarSmallUrl;
  }

  public void setAvatarSmallUrl(String avatarSmallUrl) {
    this.avatarSmallUrl = avatarSmallUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    JwtUser jwtUser = (JwtUser) o;
    return Objects.equals(id, jwtUser.id) &&
        Objects.equals(emailAddress, jwtUser.emailAddress) &&
        Objects.equals(username, jwtUser.username) &&
        Objects.equals(firstName, jwtUser.firstName) &&
        Objects.equals(lastName, jwtUser.lastName) &&
        Objects.equals(displayName, jwtUser.displayName) &&
        Objects.equals(title, jwtUser.title) &&
        Objects.equals(company, jwtUser.company) &&
        Objects.equals(companyId, jwtUser.companyId) &&
        Objects.equals(location, jwtUser.location) &&
        Objects.equals(avatarUrl, jwtUser.avatarUrl) &&
        Objects.equals(avatarSmallUrl, jwtUser.avatarSmallUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, emailAddress, username, firstName, lastName, displayName, title,
        company,
        companyId, location, avatarUrl, avatarSmallUrl);
  }
}

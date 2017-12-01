package org.symphonyoss.integration.model.yaml;

/**
 * This model stores info used if the user needs to pass through a proxy server
 * before connecting to the POD and/or Key Manager
 * Created by hamitay on 02/11/17.
 */
public class ProxyConnectionInfo {

  private String user;

  private String password;

  private String uri;

  public String getUser() { return user; }

  public void setUser(String user) { this.user = user; }

  public String getPassword() { return password; }

  public void setPassword(String password) { this.password = password; }

  public String getURI() { return uri; }

  public void setURI(String uri) { this.uri = uri; }

  @Override
  public String toString() {
    return "ProxyConnectionInfo{" +
        "uri='" + uri + '\'' +
        ", user=" + user + '\'' +
        ", password=" + password + '\'' +
        '}';
  }
}

package org.symphonyoss.integration.model.yaml;

/**
 * Indicates the admin user keystore information.
 * Created by rsanchez on 18/01/17.
 */
public class AdminUser {

  private String keystoreFile;

  private String keystorePassword;

  public String getKeystoreFile() {
    return keystoreFile;
  }

  public void setKeystoreFile(String keystoreFile) {
    this.keystoreFile = keystoreFile;
  }

  public String getKeystorePassword() {
    return keystorePassword;
  }

  public void setKeystorePassword(String keystorePassword) {
    this.keystorePassword = keystorePassword;
  }
}

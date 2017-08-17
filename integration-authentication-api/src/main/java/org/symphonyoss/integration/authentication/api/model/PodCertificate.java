package org.symphonyoss.integration.authentication.api.model;

/**
 * Pod public certificate in PEM format.
 * Created by campidelli on 8/16/17.
 */
public class PodCertificate {

  private String certificate;

  public String getCertificate() {
    return certificate;
  }

  public void setCertificate(String certificate) {
    this.certificate = certificate;
  }
}

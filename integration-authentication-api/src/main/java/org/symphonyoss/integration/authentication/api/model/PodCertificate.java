package org.symphonyoss.integration.authentication.api.model;

import java.util.Objects;

/**
 * Pod public certificate in PEM format.
 * Created by campidelli on 8/16/17.
 */
public class PodCertificate {

  private String certificate;

  public PodCertificate() { }

  public PodCertificate(String certificate) {
    this.certificate = certificate;
  }

  public String getCertificate() {
    return certificate;
  }

  public void setCertificate(String certificate) {
    this.certificate = certificate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    PodCertificate that = (PodCertificate) o;
    return Objects.equals(certificate, that.certificate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(certificate);
  }
}

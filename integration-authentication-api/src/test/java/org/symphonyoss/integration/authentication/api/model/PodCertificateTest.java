package org.symphonyoss.integration.authentication.api.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link PodCertificate}
 *
 * Created by campidelli on 18/08/17.
 */
public class PodCertificateTest {

  @Test
  public void testModel() {
    PodCertificate cert = new PodCertificate();
    cert.setCertificate("12345");

    PodCertificate cert2 = new PodCertificate(cert.getCertificate());

    assertEquals(cert, cert2);
  }

}

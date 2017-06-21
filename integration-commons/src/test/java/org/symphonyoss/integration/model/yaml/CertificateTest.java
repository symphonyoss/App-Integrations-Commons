package org.symphonyoss.integration.model.yaml;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link Certificate}
 * Created by crepache on 21/06/17.
 */
public class CertificateTest {

  private static final String CA_CERT_CHAIN_FILE = "CaCertChainFile";

  private static final String CA_CERT_FILE = "CaCertFile";

  private static final String CA_KEY_FILE = "CaKeyFile";

  private static final String CA_KEY_PASSWORD = "CaKeyPassword";

  @Test
  public void testCertificate() {
    Certificate certificate = new Certificate();
    certificate.setCaCertChainFile(CA_CERT_CHAIN_FILE);
    certificate.setCaCertFile(CA_CERT_FILE);
    certificate.setCaKeyFile(CA_KEY_FILE);
    certificate.setCaKeyPassword(CA_KEY_PASSWORD);

    Assert.assertEquals(CA_CERT_CHAIN_FILE, certificate.getCaCertChainFile());
    Assert.assertEquals(CA_CERT_FILE, certificate.getCaCertFile());
    Assert.assertEquals(CA_KEY_FILE, certificate.getCaKeyFile());
    Assert.assertEquals(CA_KEY_PASSWORD, certificate.getCaKeyPassword());
  }

}
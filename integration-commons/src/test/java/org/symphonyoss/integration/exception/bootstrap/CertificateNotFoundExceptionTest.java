package org.symphonyoss.integration.exception.bootstrap;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link CertificateNotFoundException}.
 * Created by crepache on 23/06/17.
 */
public class CertificateNotFoundExceptionTest {

  @Test
  public void testCertificateNotFoundException() {
    CertificateNotFoundException exception = new CertificateNotFoundException();
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Integration Bootstrap\n" +
        "Message: Certificate folder not found\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testCertificateNotFoundExceptionWithMessage() {
    CertificateNotFoundException exception = new CertificateNotFoundException("message");
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Integration Bootstrap\n" +
        "Message: message\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testCertificateNotFoundExceptionWithMessageAndCause() {
    Exception cause = new Exception("cause");
    CertificateNotFoundException exception = new CertificateNotFoundException("message", cause);
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Integration Bootstrap\n" +
        "Message: message\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n" +
        "Stack trace: cause\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }

}

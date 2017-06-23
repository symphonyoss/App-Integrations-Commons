package org.symphonyoss.integration.exception.bootstrap;

import org.junit.Assert;
import org.junit.Test;
import org.symphonyoss.integration.exception.ExpectedMessageBuilder;

/**
 * Unit tests for {@link CertificateNotFoundException}.
 * Created by crepache on 23/06/17.
 */
public class CertificateNotFoundExceptionTest {

  private static final String COMPONENT = "Integration Bootstrap";
  private static final String MESSAGE = "message";

  @Test
  public void testCertificateNotFoundException() {
    String message = "Certificate folder not found";
    CertificateNotFoundException exception = new CertificateNotFoundException();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(message)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testCertificateNotFoundExceptionWithMessage() {
    CertificateNotFoundException exception = new CertificateNotFoundException(MESSAGE);
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testCertificateNotFoundExceptionWithMessageAndCause() {
    String causeStr = "cause";
    Exception cause = new Exception(causeStr);
    CertificateNotFoundException exception = new CertificateNotFoundException(MESSAGE, cause);
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(causeStr)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }

}

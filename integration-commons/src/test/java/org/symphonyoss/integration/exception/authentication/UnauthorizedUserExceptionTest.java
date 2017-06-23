package org.symphonyoss.integration.exception.authentication;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link UnauthorizedUserException}.
 * Created by crepache on 23/06/17.
 */
public class UnauthorizedUserExceptionTest {

  private String message = "message";

  @Test
  public void testUnexpectedAutoException() {
    UnauthorizedUserException exception = new UnauthorizedUserException(message);
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Authentication Proxy\n" +
        "Message: message\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }


  @Test
  public void testUnexpectedAutoExceptionWithCause() {
    Throwable cause = new Throwable("cause");
    UnauthorizedUserException exception = new UnauthorizedUserException(message, cause);
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Authentication Proxy\n" +
        "Message: message\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n" +
        "Stack trace: cause\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }
}

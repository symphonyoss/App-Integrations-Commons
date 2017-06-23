package org.symphonyoss.integration.exception.config;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ForbiddenUserException}.
 * Created by crepache on 23/06/17.
 */
public class ForbiddenUserExceptionTest {

  @Test
  public void testForbiddenUserException() {
    ForbiddenUserException exception = new ForbiddenUserException();
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Configuration Service\n" +
        "Message: Unable to retrieve configurations.\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testForbiddenUserExceptionWithMessage() {
    ForbiddenUserException exception = new ForbiddenUserException("message");
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Configuration Service\n" +
        "Message: Unable to retrieve configurations. message\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testForbiddenUserExceptionWithCause() {
    Throwable cause = new Throwable("cause");
    ForbiddenUserException exception = new ForbiddenUserException(cause);
    Throwable resultCause = exception.getCause();

    Assert.assertEquals(cause, resultCause);
  }
}

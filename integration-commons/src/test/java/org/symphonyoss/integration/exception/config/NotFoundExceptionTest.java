package org.symphonyoss.integration.exception.config;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link NotFoundException}.
 * Created by crepache on 23/06/17.
 */
public class NotFoundExceptionTest {

  @Test
  public void testForbiddenUserException() {
    NotFoundException exception = new NotFoundException();
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
    NotFoundException exception = new NotFoundException("message");
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
    NotFoundException exception = new NotFoundException(cause);
    Throwable resultCause = exception.getCause();

    Assert.assertEquals(cause, resultCause);
  }
}

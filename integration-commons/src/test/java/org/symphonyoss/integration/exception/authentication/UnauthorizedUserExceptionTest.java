package org.symphonyoss.integration.exception.authentication;

import org.junit.Assert;
import org.junit.Test;
import org.symphonyoss.integration.exception.ExpectedMessageBuilder;

/**
 * Unit tests for {@link UnauthorizedUserException}.
 * Created by crepache on 23/06/17.
 */
public class UnauthorizedUserExceptionTest {
  private static final String COMPONENT = "Authentication Proxy";

  private String message = "message";

  @Test
  public void testUnexpectedAutoException() {
    UnauthorizedUserException exception = new UnauthorizedUserException(message);
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(message)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }


  @Test
  public void testUnexpectedAutoExceptionWithCause() {
    String causeStr = "cause";
    Throwable cause = new Throwable(causeStr);
    UnauthorizedUserException exception = new UnauthorizedUserException(message, cause);
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(message)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(causeStr)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }
}

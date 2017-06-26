package org.symphonyoss.integration.exception.config;

import org.junit.Assert;
import org.junit.Test;
import org.symphonyoss.integration.exception.ExpectedMessageBuilder;

/**
 * Unit tests for {@link ForbiddenUserException}.
 * Created by crepache on 23/06/17.
 */
public class ForbiddenUserExceptionTest {
  public static final String COMPONENT = "Configuration Service";
  public static final String MESSAGE_DEFAULT = "Unable to retrieve configurations.";
  public static final String MESSAGE = "message";

  @Test
  public void testForbiddenUserException() {
    ForbiddenUserException exception = new ForbiddenUserException();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(MESSAGE_DEFAULT)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testForbiddenUserExceptionWithMessage() {
    ForbiddenUserException exception = new ForbiddenUserException("message");
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(MESSAGE_DEFAULT + " " + MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

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

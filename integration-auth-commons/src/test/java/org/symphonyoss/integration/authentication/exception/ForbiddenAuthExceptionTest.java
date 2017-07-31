package org.symphonyoss.integration.authentication.exception;

import org.junit.Assert;
import org.junit.Test;
import org.symphonyoss.integration.authentication.exception.ForbiddenAuthException;
import org.symphonyoss.integration.exception.ExpectedMessageBuilder;

/**
 * Unit tests for {@link ForbiddenAuthException}.
 * Created by crepache on 23/06/17.
 */
public class ForbiddenAuthExceptionTest {
  private static final String COMPONENT = "Authentication Proxy";

  private String message = "message";

  @Test
  public void testUnexpectedAutoException() {
    ForbiddenAuthException exception = new ForbiddenAuthException(message);
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
    ForbiddenAuthException exception = new ForbiddenAuthException(message, cause);
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(message)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(causeStr)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testUnexpectedAutoExceptionWithCauseAndSolution() {
    String causeStr = "cause";
    Throwable cause = new Throwable(causeStr);
    ForbiddenAuthException exception = new ForbiddenAuthException(message, cause, ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION);
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

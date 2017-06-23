package org.symphonyoss.integration.exception.bootstrap;

import org.junit.Assert;
import org.junit.Test;
import org.symphonyoss.integration.exception.ExpectedMessageBuilder;

/**
 * Unit tests for {@link UnexpectedBootstrapException}.
 * Created by crepache on 23/06/17.
 */
public class UnexpectedBootstrapExceptionTest {
  private static final String COMPONENT = "Integration Bootstrap";
  private static final String MESSAGE = "message";
  private static final String CAUSE = "exception";

  @Test
  public void testUnexpectedBootstrapException() {
    Exception e = new Exception(CAUSE);
    UnexpectedBootstrapException exception = new UnexpectedBootstrapException(MESSAGE, e);
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(CAUSE)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }
}

package org.symphonyoss.integration.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link IntegrationRuntimeException}.
 * Created by crepache on 23/06/17.
 */
public class IntegrationRuntimeExceptionTest {
  private static final String CAUSE = "cause";

  private String message = "message";
  private String component = "component";
  private String solution = "solutions";
  private Throwable cause = new Throwable(CAUSE);

  @Test
  public void testIntegrationRuntimeException() {
    IntegrationRuntimeException exception = new IntegrationRuntimeException(component, message);
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(component)
        .message(message)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testIntegrationRuntimeExceptionWithSolution() {
    IntegrationRuntimeException exception =
        new IntegrationRuntimeException(component, message, solution);
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(component)
        .message(message)
        .solutions(solution)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testIntegrationRuntimeExceptionWithCause() {
    IntegrationRuntimeException exception =
        new IntegrationRuntimeException(component, message, cause);
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(component)
        .message(message)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(CAUSE)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testIntegrationRuntimeExceptionWithCauseAndSolution() {
    IntegrationRuntimeException exception =
        new IntegrationRuntimeException(component, message, cause, solution);
    String resultMessage = exception.getMessage();
    String expectedMessage =
        new ExpectedMessageBuilder()
            .component(component)
            .message(message)
            .solutions(solution)
            .stackTrace(CAUSE)
            .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }
}

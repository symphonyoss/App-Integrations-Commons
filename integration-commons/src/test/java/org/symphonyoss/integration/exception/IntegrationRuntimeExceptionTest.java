package org.symphonyoss.integration.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link IntegrationRuntimeException}.
 * Created by crepache on 23/06/17.
 */
public class IntegrationRuntimeExceptionTest {

  private String message = "message";

  private String component = "component";

  private String solution = "solution";

  private Throwable cause = new Throwable("cause");

  @Test
  public void testIntegrationRuntimeException() {
    IntegrationRuntimeException exception = new IntegrationRuntimeException(component, message);
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: component\n" +
        "Message: message\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testIntegrationRuntimeExceptionWithSolution() {
    IntegrationRuntimeException exception =
        new IntegrationRuntimeException(component, message, solution);
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: component\n" +
        "Message: message\n" +
        "Solutions: \n" +
        "solution\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testIntegrationRuntimeExceptionWithCause() {
    IntegrationRuntimeException exception =
        new IntegrationRuntimeException(component, message, cause);
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: component\n" +
        "Message: message\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n" +
        "Stack trace: cause\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testIntegrationRuntimeExceptionWithCauseAndSolution() {
    IntegrationRuntimeException exception =
        new IntegrationRuntimeException(component, message, cause, solution);
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: component\n" +
        "Message: message\n" +
        "Solutions: \n" +
        "solution\n" +
        "Stack trace: cause\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }
}

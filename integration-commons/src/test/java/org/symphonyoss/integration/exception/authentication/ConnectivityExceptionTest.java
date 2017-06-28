package org.symphonyoss.integration.exception.authentication;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.symphonyoss.integration.exception.ExpectedMessageBuilder;

/**
 * Unit tests for {@link ConnectivityException}.
 * Created by crepache on 23/06/17.
 */
public class ConnectivityExceptionTest {
  private static final String COMPONENT = "Authentication Proxy";
  private static final String MESSAGE = "Integration Bridge can't reach service name service!";
  private static final String CAUSE = "cause";

  private String serviceName = "service name";
  private String solution = "solutions";
  private Throwable cause = new Throwable(CAUSE);

  @Test
  public void testConnectivityException() {
    ConnectivityException exception = new ConnectivityException(COMPONENT, serviceName);
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testConnectivityExceptionWithSolution() {
    ConnectivityException exception = new ConnectivityException(COMPONENT, serviceName, solution);
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(MESSAGE)
        .solutions(solution)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testConnectivityExceptionWithCause() {
    ConnectivityException exception = new ConnectivityException(COMPONENT, serviceName, cause);
    Throwable resultCause = exception.getCause();

    Assert.assertEquals(cause, resultCause);
  }

  @Test
  public void testConnectivityExceptionWithSolutionAndCause() {
    ConnectivityException exception = new ConnectivityException(COMPONENT, serviceName, cause, solution);
    String resultMessage = exception.getMessage();
    Throwable resultCause = exception.getCause();

    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(MESSAGE)
        .solutions(solution)
        .stackTrace(CAUSE)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
    Assert.assertEquals(cause, resultCause);
  }

}

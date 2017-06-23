package org.symphonyoss.integration.exception.authentication;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ConnectivityException}.
 * Created by crepache on 23/06/17.
 */
public class ConnectivityExceptionTest {

  private String serviceName = "service name";

  private String solution = "solution";

  private Throwable cause = new Throwable("cause");

  @Test
  public void testConnectivityException() {
    ConnectivityException exception = new ConnectivityException(serviceName);
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Authentication Proxy\n" +
        "Message: Integration Bridge can't reach service name service!\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testConnectivityExceptionWithSolution() {
    ConnectivityException exception = new ConnectivityException(serviceName, solution);
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Authentication Proxy\n" +
        "Message: Integration Bridge can't reach service name service!\n" +
        "Solutions: \n" +
        "solution\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testConnectivityExceptionWithCause() {
    ConnectivityException exception = new ConnectivityException(serviceName, cause);
    Throwable resultCause = exception.getCause();

    Assert.assertEquals(cause, resultCause);
  }

  @Test
  public void testConnectivityExceptionWithSolutionAndCause() {
    ConnectivityException exception = new ConnectivityException(serviceName, cause, solution);
    String resultMessage = exception.getMessage();
    Throwable resultCause = exception.getCause();
    String expectedMessage = "\n" +
        "Component: Authentication Proxy\n" +
        "Message: Integration Bridge can't reach service name service!\n" +
        "Solutions: \n" +
        "solution\n" +
        "Stack trace: cause\n";

    Assert.assertEquals(expectedMessage, resultMessage);
    Assert.assertEquals(cause, resultCause);
  }

}

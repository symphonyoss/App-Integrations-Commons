package org.symphonyoss.integration.exception.bootstrap;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link UnexpectedBootstrapException}.
 * Created by crepache on 23/06/17.
 */
public class UnexpectedBootstrapExceptionTest {

  @Test
  public void testUnexpectedBootstrapException() {
    Exception e = new Exception("exception");
    UnexpectedBootstrapException exception = new UnexpectedBootstrapException("message", e);
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Integration Bootstrap\n" +
        "Message: message\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n" +
        "Stack trace: exception\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }
}

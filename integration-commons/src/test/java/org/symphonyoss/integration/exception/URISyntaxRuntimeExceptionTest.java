package org.symphonyoss.integration.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link URISyntaxRuntimeException}.
 * Created by crepache on 23/06/17.
 */
public class URISyntaxRuntimeExceptionTest {

  private String message = "message";

  private Throwable cause = new Throwable("cause");

  @Test
  public void URISyntaxRuntimeExceptionWithMessage() {
    URISyntaxRuntimeException exception = new URISyntaxRuntimeException(message);
    String resultMessage = exception.getMessage();
    String expectedMessage = "message";

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void URISyntaxRuntimeExceptionWithThrowable() {
    URISyntaxRuntimeException exception = new URISyntaxRuntimeException(cause);
    Throwable resultCause = exception.getCause();
    Throwable expectedCause = this.cause;

    Assert.assertEquals(expectedCause, resultCause);
  }

  @Test
  public void URISyntaxRuntimeExceptionWithMessageAndThrowable() {
    URISyntaxRuntimeException exception = new URISyntaxRuntimeException(message, cause);
    String resultMessage = exception.getMessage();
    String expectedMessage = "message";
    Throwable resultCause = exception.getCause();
    Throwable expectedCause = this.cause;

    Assert.assertEquals(expectedMessage, resultMessage);
    Assert.assertEquals(expectedCause, resultCause);
  }
}

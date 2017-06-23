package org.symphonyoss.integration.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link RemoteApiException}.
 * Created by crepache on 23/06/17.
 */
public class RemoteApiExceptionTest {

  private int code = 0;

  private String message = "message";

  private Exception e = new Exception();

  @Test
  public void testRemoteApiExceptionWithCodeAndMessage() {
    RemoteApiException exception = new RemoteApiException(code, message);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Commons\n" +
        "Message: message\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n";

    Assert.assertEquals(expectedCode, 0);
    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithCodeAndException() {
    RemoteApiException exception = new RemoteApiException(code, e);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Commons\n" +
        "Message: None\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n" +
        "Stack trace: \n";

    Assert.assertEquals(expectedCode, 0);
    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithExceptionAndMessage() {
    RemoteApiException exception = new RemoteApiException(code, message, e);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Commons\n" +
        "Message: message\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n" +
        "Stack trace: \n";

    Assert.assertEquals(expectedCode, 0);
    Assert.assertEquals(expectedMessage, resultMessage);
  }

}

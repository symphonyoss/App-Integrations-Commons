package org.symphonyoss.integration.exception;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link RemoteApiException}.
 * Created by crepache on 23/06/17.
 */
public class RemoteApiExceptionTest {

  private static final String COMMONS = "Commons";
  private static final String MESSAGE = "MESSAGE";
  private static final String NONE = "None";

  private int code = 0;
  private Exception e = new Exception();

  @Test
  public void testRemoteApiExceptionWithCodeAndMessage() {
    RemoteApiException exception = new RemoteApiException(code, MESSAGE);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    Assert.assertEquals(expectedCode, 0);
    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithCodeAndException() {
    RemoteApiException exception = new RemoteApiException(code, e);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(NONE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(StringUtils.EMPTY)
        .build();

    Assert.assertEquals(expectedCode, 0);
    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithExceptionAndMessage() {
    RemoteApiException exception = new RemoteApiException(code, MESSAGE, e);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(StringUtils.EMPTY)
        .build();

    Assert.assertEquals(expectedCode, 0);
    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithCodeAndExceptionAndSolution() {
    RemoteApiException exception = new RemoteApiException(code, e, ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(NONE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(StringUtils.EMPTY)
        .build();

    Assert.assertEquals(expectedCode, 0);
    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithSolution() {
    RemoteApiException exception = new RemoteApiException(code, MESSAGE, ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    Assert.assertEquals(expectedCode, 0);
    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithExceptionAndSolution() {
    RemoteApiException exception = new RemoteApiException(code, MESSAGE, e,
        ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION);

    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(StringUtils.EMPTY)
        .build();

    Assert.assertEquals(expectedCode, 0);
    Assert.assertEquals(expectedMessage, resultMessage);
  }

}

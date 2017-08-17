package org.symphonyoss.integration.authorization.oauth.v1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.symphonyoss.integration.exception.ExpectedMessageBuilder;

/**
 * Unit tests for {@link OAuth1HttpRequestException}
 * Created by hamitay on 8/17/17.
 */

public class OAuth1HttpRequestExceptionTest {

  private static final String COMPONENT = "Third-party integration";

  private static final String MESSAGE = "message";

  private static final String NO_SOLUTION =
      "No solution has been cataloged for troubleshooting this problem.";

  private static final int CODE = 400;

  @Test
  public void testOAuth1HttpRequestException() {
    OAuth1HttpRequestException exception = new OAuth1HttpRequestException("message", 400);
    String resultMessage = exception.getMessage();
    String expectedMessage =
        new ExpectedMessageBuilder().component(COMPONENT)
            .message(MESSAGE)
            .solutions(NO_SOLUTION)
            .build();
    int resultCode = exception.getCode();

    assertEquals(expectedMessage, resultMessage);
    assertEquals(CODE, resultCode);
  }
}

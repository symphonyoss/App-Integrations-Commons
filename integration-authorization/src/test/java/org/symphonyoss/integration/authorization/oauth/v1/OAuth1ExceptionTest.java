package org.symphonyoss.integration.authorization.oauth.v1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.symphonyoss.integration.exception.ExpectedMessageBuilder;

/**
 * Unit tests for {@link OAuth1Exception}
 * Created by hamitay on 10/17/17.
 */

public class OAuth1ExceptionTest {

  private static final String COMPONENT = "Third-party integration/app authorization.";

  public static String MESSAGE = "message";

  public static String SOLUTION = "solution";

  public static Exception CAUSE = new Exception("cause");

  @Test
  public void testOAuth1ExceptionWithMessageAndSolution() {
    OAuth1Exception exception = new OAuth1Exception(MESSAGE, SOLUTION);
    String resultMessage = exception.getMessage();

    String expectedMessage =
        new ExpectedMessageBuilder()
            .message(MESSAGE)
            .solutions(SOLUTION)
            .component(COMPONENT)
            .build();

    assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testOAuth1ExceptionWithMessageCauseAndSolution() {
    OAuth1Exception exception = new OAuth1Exception(MESSAGE, CAUSE, SOLUTION);
    String resultMessage = exception.getMessage();

    String expectedMessage =
        new ExpectedMessageBuilder()
            .message(MESSAGE)
            .solutions(SOLUTION)
            .component(COMPONENT)
            .stackTrace(CAUSE.getMessage())
            .build();

    assertEquals(expectedMessage, resultMessage);
  }

}

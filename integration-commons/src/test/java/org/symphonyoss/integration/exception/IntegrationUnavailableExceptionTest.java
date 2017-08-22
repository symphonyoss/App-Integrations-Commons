package org.symphonyoss.integration.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link IntegrationUnavailableException}
 * Created by hamitay on 8/18/17.
 */
public class IntegrationUnavailableExceptionTest {

  private static final String CONFIGURATION_TYPE = "configurationType";

  private static final String COMPONENT = "Webhook Dispatcher";

  private static final String MESSAGE = "message";

  private static final String SOLUTION = "solution";

  @Test
  public void testIntegrationUnavailableExceptionWithConfigurationType() {
    IntegrationUnavailableException exception =
        new IntegrationUnavailableException(CONFIGURATION_TYPE);
    String resultMessage = exception.getMessage();

    String expectedMessage = new ExpectedMessageBuilder().component(COMPONENT)
        .message(String.format("Configuration %s unavailable", CONFIGURATION_TYPE))
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testIntegrationUnavailableExceptionWithMessageAndSolution() {
    IntegrationUnavailableException exception =
        new IntegrationUnavailableException(COMPONENT, MESSAGE, SOLUTION);
    String resultMessage = exception.getMessage();

    String expectedMessage = new ExpectedMessageBuilder().component(COMPONENT)
        .message(MESSAGE)
        .solutions(SOLUTION)
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }



}

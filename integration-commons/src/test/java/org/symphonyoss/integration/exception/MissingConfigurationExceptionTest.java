package org.symphonyoss.integration.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MissingConfigurationException}.
 * Created by crepache on 23/06/17.
 */
public class MissingConfigurationExceptionTest {

  private static final String EXPECTED_MESSAGE =
      "Verify the YAML configuration file. No configuration found to the key key";
  private static final String SOLUTION = "solutions";
  private static final String COMPONENT = "component";

  @Test
  public void testMissingConfigurationException() {
    MissingConfigurationException exception = new MissingConfigurationException(COMPONENT, "key");
    String message = exception.getMessage();
    String expected = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(EXPECTED_MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    Assert.assertEquals(expected, message);
  }

  @Test
  public void testMissingConfigurationExceptionWithSolution() {
    MissingConfigurationException exception =
        new MissingConfigurationException(COMPONENT, "key", SOLUTION);
    String message = exception.getMessage();
    String expected = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(EXPECTED_MESSAGE)
        .solutions(SOLUTION)
        .build();

    Assert.assertEquals(expected, message);
  }
}

package org.symphonyoss.integration.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link MissingConfigurationException}.
 * Created by crepache on 23/06/17.
 */
public class MissingConfigurationExceptionTest {

  private static final String EXPECTED_MESSAGE =
      "Verify the YAML configuration file. No configuration found to the key test.key";
  private static final String KEY = "test.key";
  private static final String SOLUTION = "solutions";
  private static final String COMPONENT = "component";


  @Test
  public void testMissingConfigurationException() {
    MissingConfigurationException exception = new MissingConfigurationException(COMPONENT, KEY);
    String message = exception.getMessage();
    String expected = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(EXPECTED_MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    assertEquals(expected, message);
  }

  @Test
  public void testMissingConfigurationExceptionWithSolution() {
    MissingConfigurationException exception =
        new MissingConfigurationException(COMPONENT, EXPECTED_MESSAGE, SOLUTION);

    String message = exception.getMessage();
    String expected = new ExpectedMessageBuilder()
        .component(COMPONENT)
        .message(EXPECTED_MESSAGE)
        .solutions(SOLUTION)
        .build();

    assertEquals(expected, message);
  }
}

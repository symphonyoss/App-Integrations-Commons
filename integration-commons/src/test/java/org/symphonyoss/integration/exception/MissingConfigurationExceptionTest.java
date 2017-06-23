package org.symphonyoss.integration.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MissingConfigurationException}.
 * Created by crepache on 23/06/17.
 */
public class MissingConfigurationExceptionTest {

  @Test
  public void testMissingConfigurationException() {
    MissingConfigurationException exception = new MissingConfigurationException("component", "key");
    String message = exception.getMessage();
    String expected = "\n" +
        "Component: component\n" +
        "Message: Verify the YAML configuration file. No configuration found to the key key\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n";

    Assert.assertEquals(expected, message);
  }

  @Test
  public void testMissingConfigurationExceptionWithSolution() {
    MissingConfigurationException exception =
        new MissingConfigurationException("component", "key", "solution");
    String message = exception.getMessage();
    String expected = "\n" +
        "Component: component\n" +
        "Message: Verify the YAML configuration file. No configuration found to the key key\n" +
        "Solutions: \n" +
        "solution\n";

    Assert.assertEquals(expected, message);
  }
}

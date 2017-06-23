package org.symphonyoss.integration.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link EntityXMLGeneratorException}.
 * Created by crepache on 23/06/17.
 */
public class EntityXMLGeneratorExceptionTest {

  @Test
  public void testEntityXMLGeneratorException() {
    Exception e = new Exception("message");
    EntityXMLGeneratorException exception = new EntityXMLGeneratorException(e);
    String resultMessage = exception.getMessage();
    String expectedMessage = "\n" +
        "Component: Commons\n" +
        "Message: message\n" +
        "Solutions: \n" +
        "No solution has been cataloged for troubleshooting this problem.\n" +
        "Stack trace: message\n";

    Assert.assertEquals(expectedMessage, resultMessage);
  }
}

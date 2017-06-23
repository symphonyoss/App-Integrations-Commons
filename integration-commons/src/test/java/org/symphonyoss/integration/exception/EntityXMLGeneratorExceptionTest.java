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
    String expectedMessage = new ExpectedMessageBuilder()
        .component("Commons")
        .message("message")
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace("message")
        .build();

    Assert.assertEquals(expectedMessage, resultMessage);
  }
}

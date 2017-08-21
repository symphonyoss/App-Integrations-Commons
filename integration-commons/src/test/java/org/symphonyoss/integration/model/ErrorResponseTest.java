package org.symphonyoss.integration.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ErrorResponse}
 * Created by hamitay on 8/18/17.
 */
public class ErrorResponseTest {

  private static final int STATUS = 400;

  private static final String MESSAGE = "message";

  private static final Object PROPERTIES = new Object();

  @Test
  public void testEquals() {
    ErrorResponse response1 = new ErrorResponse(STATUS, MESSAGE);
    response1.setProperties(PROPERTIES);
    ErrorResponse response2 = new ErrorResponse(0,"");
    response2.setMessage(MESSAGE);
    response2.setStatus(STATUS);
    response2.setProperties(PROPERTIES);

    Assert.assertTrue(response1.equals(response1));
    Assert.assertTrue(response1.equals(response2));
  }

  @Test
  public void testHashCode() {
    ErrorResponse response = new ErrorResponse(STATUS, MESSAGE);
    response.setProperties(PROPERTIES);

    int expectedHash = response.getStatus();
    expectedHash = 31 * expectedHash + MESSAGE.hashCode();
    expectedHash = 31 * expectedHash + PROPERTIES.hashCode();

    Assert.assertEquals(expectedHash, response.hashCode());
  }

}

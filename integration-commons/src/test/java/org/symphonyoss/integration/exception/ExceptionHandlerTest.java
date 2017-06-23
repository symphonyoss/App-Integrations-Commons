package org.symphonyoss.integration.exception;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

/**
 * Created by crepache on 23/06/17.
 */
public class ExceptionHandlerTest extends ExceptionHandler {

  @Test
  public void testUnauthorizedError() {
    Assert.assertTrue(unauthorizedError(Response.Status.UNAUTHORIZED.getStatusCode()));
    Assert.assertFalse(unauthorizedError(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
  }

  @Test
  public void testForbiddenError() {
    Assert.assertTrue(forbiddenError(Response.Status.FORBIDDEN.getStatusCode()));
    Assert.assertFalse(forbiddenError(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
  }
}

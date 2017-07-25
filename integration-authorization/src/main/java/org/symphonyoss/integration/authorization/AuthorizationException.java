package org.symphonyoss.integration.authorization;

import org.symphonyoss.integration.exception.IntegrationRuntimeException;

/**
 * Created by campidelli on 7/25/17.
 */
public abstract class AuthorizationException extends IntegrationRuntimeException {

  private static final String COMPONENT = "Third-party integration/app authorization.";

  public AuthorizationException(String message) {
    super(COMPONENT, message);
  }

  public AuthorizationException(String message, String... solutions) {
    super(COMPONENT, message, solutions);
  }

  public AuthorizationException(String message, Throwable cause, String... solutions) {
    super(COMPONENT, message, cause, solutions);
  }

  public AuthorizationException(String message, Throwable cause) {
    super(COMPONENT, message, cause);
  }
}


package org.symphonyoss.integration.authorization;

import org.symphonyoss.integration.exception.IntegrationRuntimeException;

/**
 * Exception used to inform runtime exceptions regarding authorization process.
 *
 * Created by campidelli on 7/25/17.
 */
public abstract class AuthorizationException extends IntegrationRuntimeException {

  private static final String COMPONENT = "Third-party integration/app authorization.";

  public AuthorizationException(String message, Throwable cause, String... solutions) {
    super(COMPONENT, message, cause, solutions);
  }

  public AuthorizationException(String message, String... solutions) {
    super(COMPONENT, message, solutions);
  }
}


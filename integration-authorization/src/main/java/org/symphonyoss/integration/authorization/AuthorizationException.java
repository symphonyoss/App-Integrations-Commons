package org.symphonyoss.integration.authorization;

import org.symphonyoss.integration.exception.IntegrationException;

/**
 * Class used to inform exceptions regarding authorization process.
 *
 * Created by campidelli on 7/25/17.
 */
public class AuthorizationException extends IntegrationException {

  private static final String COMPONENT = "Third-party integration/app authorization.";

  public AuthorizationException(String message, Throwable cause, String... solutions) {
    super(COMPONENT, message, cause, solutions);
  }

  public AuthorizationException(String message, String... solutions) {
    super(COMPONENT, message, solutions);
  }
}


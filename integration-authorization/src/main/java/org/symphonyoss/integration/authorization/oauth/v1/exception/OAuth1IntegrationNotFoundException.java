package org.symphonyoss.integration.authorization.oauth.v1.exception;

import org.symphonyoss.integration.authorization.oauth.v1.exception.OAuth1Exception;

/**
 * Exception used to inform that the informed integration wasn't found
 *
 * Created by hamitay on 17/10/17.
 */
public class OAuth1IntegrationNotFoundException extends OAuth1Exception {

  public OAuth1IntegrationNotFoundException(String message, Throwable cause, String... solutions) {
    super(message, cause, solutions);
  }

  public OAuth1IntegrationNotFoundException(String message, String... solutions) {
    super(message, solutions);
  }
}

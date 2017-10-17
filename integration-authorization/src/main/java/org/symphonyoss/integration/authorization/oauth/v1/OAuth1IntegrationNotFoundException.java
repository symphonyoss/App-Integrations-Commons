package org.symphonyoss.integration.authorization.oauth.v1;

/**
 * Exception used to inform that the informed integration wasn't found on the OAuth1 process
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

package org.symphonyoss.integration.authorization.oauth.v1;

/**
 * Exception used to inform missing parameters on the OAuth1 process
 *
 * Created by hamitay on 17/10/17.
 */
public class OAuth1MissingParametersException extends OAuth1Exception {

  public OAuth1MissingParametersException(String message, Throwable cause, String... solutions) {
    super(message, cause, solutions);
  }

  public OAuth1MissingParametersException(String message, String... solutions) {
    super(message, solutions);
  }
}

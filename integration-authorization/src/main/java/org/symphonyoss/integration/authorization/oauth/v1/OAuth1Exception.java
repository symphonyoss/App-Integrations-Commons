package org.symphonyoss.integration.authorization.oauth.v1;

import org.symphonyoss.integration.authorization.AuthorizationException;

/**
 * Exception used to inform runtime exceptions regarding OAuth1 process.
 *
 * Created by campidelli on 7/25/17.
 */
public class OAuth1Exception extends AuthorizationException {

  public OAuth1Exception(String message, Throwable cause, String... solutions) {
    super(message, cause, solutions);
  }

  public OAuth1Exception(String message, String... solutions) {
    super(message, solutions);
  }
}


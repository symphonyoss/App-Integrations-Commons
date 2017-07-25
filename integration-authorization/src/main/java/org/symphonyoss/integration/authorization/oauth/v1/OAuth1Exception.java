package org.symphonyoss.integration.authorization.oauth.v1;

import org.symphonyoss.integration.authorization.AuthorizationException;

/**
 * Created by campidelli on 7/25/17.
 */
public class OAuth1Exception extends AuthorizationException {

  public OAuth1Exception(String message, String... solutions) {
    super(message, solutions);
  }

  public OAuth1Exception(String message, Throwable cause, String... solutions) {
    super(message, cause, solutions);
  }
}


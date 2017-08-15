package org.symphonyoss.integration.authorization.oauth.v1;

import org.symphonyoss.integration.exception.IntegrationException;

/**
 * Created by alexandre-silva-daitan on 14/08/17.
 */
public class  OAuth1HttpRequestException extends IntegrationException {

  private static final String COMPONENT = "Third-party integration";
  private int code;

  public OAuth1HttpRequestException(String message, int code) {
    super(COMPONENT, message);
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}


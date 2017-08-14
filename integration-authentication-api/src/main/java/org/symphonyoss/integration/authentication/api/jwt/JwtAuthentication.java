package org.symphonyoss.integration.authentication.api.jwt;

/**
 * Service interface responsible for handling JWT authentication stuff.
 *
 * Created by rsanchez on 14/08/17.
 */
public interface JwtAuthentication {

  /**
   * Return user identifier from HTTP Authorization header.
   *
   * @param authorizationHeader HTTP Authorization header
   * @return User identifier or null if the authorization header is not present or it's not a valid JWT token
   */
  Long getUserIdFromAuthorizationHeader(String authorizationHeader);

  /**
   * Retrieves JWT token from HTTP Authorization header.
   *
   * @param authorizationHeader HTTP Authorization header
   * @return JWT token or null if the authorization header is not present or it's not a valid JWT
   * token
   */
  String getJwtToken(String authorizationHeader);

}

package org.symphonyoss.integration.authorization;

import org.symphonyoss.integration.Integration;
import org.symphonyoss.integration.model.yaml.AppAuthorizationModel;

/**
 * Contract containing the minimum responsibility of a authorized integration.
 * Created by campidelli on 31-jul-17.
 */
public interface AuthorizedIntegration extends Integration {

  /**
   * Retrieve integration authorization properties.
   * @return Integration authentication properties
   */
  AppAuthorizationModel getAuthorizationModel();

  boolean isUserAuthorized(String url, Long userId) throws AuthorizationException;

  String getAuthorizationUrl(String url, Long userId) throws AuthorizationException;

  /**
   * Authorize a integration to make requests to third-party applications.
   * @param authorizationPayload Object contaning all the parameters, headers and the request body
   * sent by the third-party application
   * @throws AuthorizationException when a problem occur with this operation.
   */
  void authorize(AuthorizationPayload authorizationPayload) throws AuthorizationException;

}

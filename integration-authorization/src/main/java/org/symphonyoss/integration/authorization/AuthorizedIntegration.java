/**
 * Copyright 2016-2017 Symphony Integrations - Symphony LLC
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

  /**
   * Check if a user is authorized to perform calls to a resource provided by a given url.
   * @param url Resource url.
   * @param userId User id.
   * @return <code>true</code> if the user is authorized.
   * @throws AuthorizationException Thrown in any case of error.
   */
  boolean isUserAuthorized(String url, Long userId) throws AuthorizationException;

  /**
   * Get an authorization url for user to perform calls to a resource provided by a given url.
   * @param url Resource url.
   * @param userId User id.
   * @return An authorization url.
   * @throws AuthorizationException Thrown in any case of error.
   */
  String getAuthorizationUrl(String url, Long userId) throws AuthorizationException;

  /**
   * Authorize a integration to make requests to third-party applications.
   * @param authorizationPayload Object contaning all the parameters, headers and the request body
   * sent by the third-party application
   * @throws AuthorizationException when a problem occur with this operation.
   */
  void authorize(AuthorizationPayload authorizationPayload) throws AuthorizationException;

  /**
   * Get an access token for user to perform calls to an external system resource.
   * @param url Integration URL.
   * @param userId User id.
   * @return An access token.
   * @throws AuthorizationException Thrown in any case of error.
   */
  String getAccessToken(String url, Long userId) throws AuthorizationException;

}
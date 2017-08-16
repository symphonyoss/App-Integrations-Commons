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

import java.util.List;
import java.util.Map;

/**
 * Provides a contract to implement a repository service to authorization entities.
 * Created by campidelli on 1-aug-17.
 */
public interface AuthorizationRepositoryService {

  /**
   * Save the user authorization data (replaces if already exists).
   * @param configurationId Used to identify the current integration.
   * @param data Data to be saved.
   * @throws AuthorizationException Thrown in case of error.
   */
  void save(String integrationUser, String configurationId, UserAuthorizationData data) throws AuthorizationException;

  /**
   * Find a user authorization data that matches with the given url and userId
   * @param configurationId Used to identify the current integration.
   * @param url Third-party integration url.
   * @param userId User id.
   * @return Data found or null otherwise.
   * @throws AuthorizationException Thrown in case of error.
   */
  UserAuthorizationData find(String integrationUser, String configurationId, String url,
      Long userId) throws AuthorizationException;

  /**
   * Search for user data based on a map of filters.
   * @param configurationId Used to identify the current integration.
   * @param filter Map containing pairs of key/values used as filter.
   * @return List contaning the found data entries or empty.
   * @throws AuthorizationException Thrown in case of error.
   */
  List<UserAuthorizationData> search(String integrationUser, String configurationId,
      Map<String, String> filter) throws AuthorizationException;
}

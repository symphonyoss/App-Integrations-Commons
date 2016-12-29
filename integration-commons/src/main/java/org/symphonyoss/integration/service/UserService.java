/**
 * Copyright 2016-2017 Symphony Integrations - Symphony LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.symphonyoss.integration.service;

import org.symphonyoss.integration.entity.model.User;

/**
 * Interface that defines methods to user services
 * Created by cmarcondes on 11/2/16.
 */
public interface UserService {

  /**
   * Search a user by Email converting it into a {@link User}
   * @param integrationUser the integration bot user id
   * @param email the email of the user to be retrieved
   * @return - if email is null, returns null.
   * - if a user is not found, returns a User with the email received.
   * - if a user is found, convert it into a User
   */
  User getUserByEmail(String integrationUser, String email);

  /**
   * Search a user by UserName converting it into a {@link User}
   * @param integrationUser the integration bot user id
   * @param userName the username of the user to be retrieved
   * @return - if userName is null, returns null.
   * - if a user is not found, returns a User with the email received.
   * - if a user is found, convert it into a User
   */
  User getUserByUserName(String integrationUser, String userName);

  /**
   * Search a user by UserId converting it into a {@link User}
   * @param integrationUser the integration bot user id
   * @param userId the id of the user to be retrieved
   * @return - if userId is null, returns null.
   * - if a user is not found, returns null.
   * - if a user is found, convert it into a User
   */
  User getUserByUserId(String integrationUser, Long userId);

}

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

package org.symphonyoss.integration;

import org.symphonyoss.integration.authorization.UserAuthorizationData;
import org.symphonyoss.integration.exception.authentication.UnauthorizedUserException;
import org.symphonyoss.integration.model.config.IntegrationSettings;
import org.symphonyoss.integration.model.healthcheck.IntegrationHealth;
import org.symphonyoss.integration.model.yaml.AppAuthorizationModel;

import java.util.Set;

/**
 * Interface defining a minimum responsibility for Integration implementations, defining a basic
 * lifecycle.
 *
 * Created by Milton Quilzini on 04/05/16.
 */
public interface Integration {

  /**
   * Everything that needs to be executed when an Integration is being bootstrapped.
   * @param integrationUser
   */
  void onCreate(String integrationUser);

  /**
   * Performs the necessary internal changes based on a Configuration update.
   * @param settings the settings incoming from Symphony.
   */
  void onConfigChange(IntegrationSettings settings);

  /**
   * Everything that needs to be executed when an Integration is being shutdown.
   */
  void onDestroy();

  /**
   * Get the health status of the integration
   * @return Health Status of the integration
   */
  IntegrationHealth getHealthStatus();

  /**
   * Get the integration config
   * @return Integration config
   */
  IntegrationSettings getSettings();

  /**
   * Retrieve the integration whitelist.
   * @return Integration whitelist.
   */
  Set<String> getIntegrationWhiteList();

  /**
   * Retrieve integration authorization properties.
   * @return Integration authentication properties
   */
  AppAuthorizationModel getAuthorizationModel();

  /**
   * Verify if the user authorization properties are valid. This verification should be performed
   * by each integration.
   *
   * @param authData User authorization properties
   * @throws UnauthorizedUserException If the user authorization properties are invalid or have
   * expired.
   */
  void verifyUserAuthorizationData(UserAuthorizationData authData);

}

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

import org.symphonyoss.integration.exception.config.IntegrationConfigException;
import org.symphonyoss.integration.model.config.IntegrationInstance;
import org.symphonyoss.integration.model.config.IntegrationSettings;

/**
 * class that defines methods for querying the integrations
 *
 * Created by rsanchez on 03/05/16.
 */
public interface IntegrationService {

  /**
   * Initializes essential parameters for ConfigurationService.
   * Should be implemented with @{@link javax.annotation.PostConstruct} to avoid unsafe
   * initializations.
   */
  void init();

  /**
   * Get a particular integration based on integration identifier.
   * @param integrationId Integration identifier.
   * @param userId user to query configurations.
   * @return Configuration object
   * @throws IntegrationConfigException
   */
  IntegrationSettings getIntegrationById(String integrationId, String userId);

  /**
   * Get a particular integration based on integration type.
   * @param integrationType Integration type.
   * @param userId user to query integrations.
   * @return Integration settings object
   * @throws IntegrationConfigException
   */
  IntegrationSettings getIntegrationByType(String integrationType, String userId);

  /**
   * Create or update an integration.
   * @param settings Integration settings object.
   * @param userId user to query integrations.
   * @throws IntegrationConfigException
   */
  IntegrationSettings save(IntegrationSettings settings, String userId);

  /**
   * Get a particular integration instance based on integration instance identifier.
   * @param integrationId Integration identifier.
   * @param instanceId Integration instance identifier.
   * @param userId user to query instances.
   * @return Integration instance object
   * @throws IntegrationConfigException
   */
  IntegrationInstance getInstanceById(String integrationId, String instanceId, String userId);

  /**
   * Create or update an integration instance.
   * @param instance Integration instance object.
   * @param userId user to query instances.
   * @throws IntegrationConfigException
   */
  IntegrationInstance save(IntegrationInstance instance, String userId);

}

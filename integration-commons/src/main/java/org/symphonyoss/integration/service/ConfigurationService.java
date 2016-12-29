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

import com.symphony.api.pod.model.ConfigurationInstance;
import com.symphony.api.pod.model.V1Configuration;

import org.symphonyoss.integration.exception.config.IntegrationConfigException;

/**
 * class that defines methods for querying the configurations of the integrations
 *
 * Created by rsanchez on 03/05/16.
 */
public interface ConfigurationService {

  /**
   * Initializes essential parameters for ConfigurationService.
   * Should be implemented with @{@link javax.annotation.PostConstruct} to avoid unsafe
   * initializations.
   */
  void init();

  /**
   * Get a particular configuration based on configuration identifier.
   * @param configurationId Configuration identifier.
   * @param userId user to query configurations.
   * @return Configuration object
   * @throws IntegrationConfigException
   */
  V1Configuration getConfigurationById(String configurationId, String userId);

  /**
   * Get a particular configuration based on configuration type.
   * @param configurationType Configuration type.
   * @param userId user to query configurations.
   * @return Configuration object
   * @throws IntegrationConfigException
   */
  V1Configuration getConfigurationByType(String configurationType, String userId);

  /**
   * Create or update a configuration.
   * @param configuration Configuration object.
   * @param userId user to query configurations.
   * @throws IntegrationConfigException
   */
  V1Configuration save(V1Configuration configuration, String userId);

  /**
   * Get a particular configuration instance based on configuration instance identifier.
   * @param configurationId Configuration identifier.
   * @param instanceId Configuration instance identifier.
   * @param userId user to query configurations.
   * @return Configuration instance object
   * @throws IntegrationConfigException
   */
  ConfigurationInstance getInstanceById(String configurationId, String instanceId, String userId)
  ;

  /**
   * Create or update a configuration instance.
   * @param instance Configuration instance object.
   * @param userId user to query configurations.
   * @throws IntegrationConfigException
   */
  ConfigurationInstance save(ConfigurationInstance instance, String userId);

}

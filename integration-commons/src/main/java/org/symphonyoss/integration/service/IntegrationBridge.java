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

import com.symphony.api.agent.model.V2MessageList;
import com.symphony.api.pod.model.ConfigurationInstance;

import org.symphonyoss.integration.Integration;

import java.util.List;

/**
 * The Integration Bridge holds utility methods to interact with the Symphony POD, sending messages
 * and providing Integration objects for use within all Integration implementations that need to
 * communicate with Symphony.
 *
 * Key services provided:
 * * Authentication proxy - each integration should be configured with credentials, but the
 * implementation never needs to deal with them.  Once bootstrapped, the integration simply receive
 * configurations and can use integration bridge services as if it's unauthenticated.  The bridge
 * itself proxies those services to the cloud with the proper authentication.
 *
 * * Send messages to a stream or a set of streams.
 */
public interface IntegrationBridge {

  /**
   * Retrieve an Integration instance. One should probably cast it into an inherited class.
   * @param integrationId the integration ID SHOULD be an implementation of Integration classname.
   * @return the Integration object.
   */
  Integration getIntegrationById(String integrationId);

  /**
   * Sends a message through Agent API.
   * @param instance the configuration instance object.
   * @param integrationUser the user of integration
   * @param message the actual message. It's expected to be already on proper format.
   */
  V2MessageList sendMessage(ConfigurationInstance instance, String integrationUser, String message);

  /**
   * Sends a message through Agent API to a list of streams.
   * @param instance the configuration instance object.
   * @param integrationUser the user of integration.
   * @param streams List of streams.
   * @param message the actual message. It's expected to be already on proper format.
   */
  V2MessageList sendMessage(ConfigurationInstance instance, String integrationUser, List<String>
      streams, String message);

  /**
   * Removes an integration based on the integration identifier.
   * @param integrationId integration identifier
   */
  void removeIntegration(String integrationId);

}

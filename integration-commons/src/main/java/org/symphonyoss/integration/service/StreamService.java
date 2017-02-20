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

import org.symphonyoss.integration.exception.RemoteApiException;
import org.symphonyoss.integration.model.config.IntegrationInstance;
import org.symphonyoss.integration.model.message.Message;
import org.symphonyoss.integration.model.stream.StreamType;
import org.symphonyoss.integration.model.stream.Stream;

import java.util.List;

/**
 * Service class to handle with the business logic related to streams.
 * Created by rsanchez on 13/05/16.
 */
public interface StreamService {

  /**
   * Retrieve the streams configured by the user
   * @param instance Integration instance
   * @return List of streams configured by the user.
   */
  List<String> getStreams(IntegrationInstance instance);

  /**
   * Retrieve the streams configured by the user
   * @param optionalProperties JSON Object that contains the list of streams
   * @return List of streams configured by the user.
   */
  List<String> getStreams(String optionalProperties);

  /**
   * Retrieve the stream type configured by user
   * @param instance Configuration instance
   * @return Stream type configured by user or StreamType.NONE if have no stream type configured.
   */
  StreamType getStreamType(IntegrationInstance instance);

  /**
   * Sends a message to a specific stream using Messages API.
   * @param integrationUser
   * @param stream the stream identifier.
   * @param messageSubmission the actual message. It's expected to be already on proper format.
   * @return
   */
  Message postMessage(String integrationUser, String stream, Message messageSubmission) throws RemoteApiException;

  /**
   * Create an instant message with an specific user.
   * @param integrationUser integration user
   * @param userId user identifier
   * @return Stream object
   * @throws RemoteApiException
   */
  Stream createIM(String integrationUser, Long userId) throws RemoteApiException;

}

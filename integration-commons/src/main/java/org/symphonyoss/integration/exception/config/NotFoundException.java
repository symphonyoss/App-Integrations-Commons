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

package org.symphonyoss.integration.exception.config;

/**
 * Returned when trying to post message but not exists configuration.
 *
 * Created by Cassiano Repache on 12/04/17.
 */
public class NotFoundException extends RemoteConfigurationException {

  public NotFoundException(String message, Throwable cause, String... solutions) {
    super(message, cause, solutions);
  }

}

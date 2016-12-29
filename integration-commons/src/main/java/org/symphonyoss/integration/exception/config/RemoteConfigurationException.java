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
 * Created by mquilzini on 31/05/16.
 */
public class RemoteConfigurationException extends IntegrationConfigException {

  public RemoteConfigurationException() {
    super("Unable to retrieve configurations.");
  }

  public RemoteConfigurationException(String message) {
    super("Unable to retrieve configurations. " + message);
  }

  public RemoteConfigurationException(Throwable cause) {
    super("Unable to retrieve configurations.", cause);
  }

}

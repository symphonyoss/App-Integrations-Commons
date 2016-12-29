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
 * Returned when trying to call external APIs and the user in use is not authorized do complete the
 * action (either by missing required entitlements or being inactive).
 *
 * Created by Milton Quilzini on 04/11/16.
 */
public class ForbiddenUserException extends RemoteConfigurationException {

  public ForbiddenUserException() {
    super();
  }

  public ForbiddenUserException(String message) {
    super(message);
  }

  public ForbiddenUserException(Throwable cause) {
    super(cause);
  }

}

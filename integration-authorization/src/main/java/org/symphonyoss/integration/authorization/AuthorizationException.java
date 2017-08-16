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

import org.symphonyoss.integration.exception.IntegrationException;

/**
 * Class used to inform exceptions regarding authorization process.
 *
 * Created by campidelli on 7/25/17.
 */
public class AuthorizationException extends IntegrationException {

  private static final String COMPONENT = "Third-party integration/app authorization.";

  public AuthorizationException(String message, Throwable cause, String... solutions) {
    super(COMPONENT, message, cause, solutions);
  }

  public AuthorizationException(String message, String... solutions) {
    super(COMPONENT, message, solutions);
  }
}


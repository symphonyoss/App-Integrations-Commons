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

import org.symphonyoss.integration.exception.IntegrationRuntimeException;

import java.util.List;

/**
 * Base exception class to report failures in the Integration Config module.
 * All checked exceptions in this module need to extend this class.
 *
 * It contains the component name: Configuration Service
 *
 * Created by rsanchez on 03/05/16.
 */
public abstract class IntegrationConfigException extends IntegrationRuntimeException {

  private static final String COMPONENT = "Configuration Service";

  public IntegrationConfigException(String message, List<String> solutions) {
    super(COMPONENT, message, solutions);
  }

  public IntegrationConfigException(String message) {
    super(COMPONENT, message);
  }

  public IntegrationConfigException(String message, List<String> solutions, Throwable cause) {
    super(COMPONENT, message, solutions, cause);
  }

  public IntegrationConfigException(String message, Throwable cause) {
    super(COMPONENT, message, cause);
  }
}

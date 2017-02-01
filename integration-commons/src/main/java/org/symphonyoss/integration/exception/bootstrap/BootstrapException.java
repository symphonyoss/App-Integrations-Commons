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

package org.symphonyoss.integration.exception.bootstrap;

import org.symphonyoss.integration.exception.IntegrationRuntimeException;

import java.util.List;

/**
 * Abstract exception to be used for all Bootstrap exceptions.
 *
 * It contains the component name: Integration Bootstrap
 *
 * Created by cmarcondes on 10/26/16.
 */
public abstract class BootstrapException extends IntegrationRuntimeException {

  private static final String COMPONENT = "Integration Bootstrap";

  public BootstrapException(String message, Exception cause, String... solutions) {
    super(COMPONENT, message, cause, solutions);
  }

  public BootstrapException(String message, String... solutions) {
    super(COMPONENT, message, solutions);
  }

  public BootstrapException(String message, Exception cause) {
    super(COMPONENT, message, cause);
  }

  public BootstrapException(String message) {
    super(COMPONENT, message);
  }
}

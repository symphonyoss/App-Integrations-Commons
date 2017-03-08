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

package org.symphonyoss.integration.exception.authentication;

/**
 * Should be thrown when a connectivity issue is identified while communicating with the required
 * service.
 *
 * Created by Milton Quilzini on 17/11/16.
 */
public class ConnectivityException extends AuthenticationException {

  protected static final String DEFAULT_MESSAGE = "Integration Bridge can't reach %s service!";

  public ConnectivityException(String serviceName) {
    super(String.format(DEFAULT_MESSAGE, serviceName));
  }

  public ConnectivityException(String serviceName, String... solutions) {
    super(String.format(DEFAULT_MESSAGE, serviceName), solutions);
  }

  public ConnectivityException(String serviceName, Throwable cause) {
    super(String.format(DEFAULT_MESSAGE, serviceName), cause);
  }

  public ConnectivityException(String serviceName, Throwable cause, String... solutions) {
    super(String.format(DEFAULT_MESSAGE, serviceName), cause, solutions);
  }
}

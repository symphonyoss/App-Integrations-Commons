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

import java.util.List;

/**
 * Should be extended to denote specific connectivity problems.
 *
 * Created by Milton Quilzini on 17/11/16.
 */
public abstract class ConnectivityException extends AuthenticationException {

  protected static final String DEFAULT_MESSAGE = "Integration Bridge can't reach %s service!";

  public ConnectivityException(String message) {
    super(message);
  }

  public ConnectivityException(String message, List<String> solutions) {
    super(message, solutions);
  }

  public ConnectivityException(String message, List<String> solutions, Throwable cause) {
    super(message, solutions, cause);
  }

  public ConnectivityException(String message, Throwable cause) {
    super(message, cause);
  }
}

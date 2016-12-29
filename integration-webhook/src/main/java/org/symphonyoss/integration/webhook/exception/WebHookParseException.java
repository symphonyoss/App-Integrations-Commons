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

package org.symphonyoss.integration.webhook.exception;

import org.symphonyoss.integration.exception.IntegrationRuntimeException;

import java.util.List;

/**
 * Integration class couldn't validate the incoming payload.
 *
 * Created by mquilzini on 17/05/16.
 */
public class WebHookParseException extends IntegrationRuntimeException {

  public WebHookParseException(String component, String message) {
    super(component, message);
  }

  public WebHookParseException(String component, String message, List<String> solutions) {
    super(component, message, solutions);
  }

  public WebHookParseException(String component, String message, List<String> solutions,
      Throwable cause) {
    super(component, message, solutions, cause);
  }

  public WebHookParseException(String component, String message, Throwable cause) {
    super(component, message, cause);
  }
}

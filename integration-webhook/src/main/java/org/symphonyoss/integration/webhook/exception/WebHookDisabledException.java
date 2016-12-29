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

import java.util.List;

/**
 * Created by Milton Quilzini on 21/11/16.
 */
public class WebHookDisabledException extends WebhookException {

  public WebHookDisabledException(String configurationType) {
    super(String.format("Webhook %s disabled!", configurationType));
  }

  public WebHookDisabledException(String message, List<String> solutions) {
    super(message, solutions);
  }

  public WebHookDisabledException(String message, List<String> solutions, Throwable cause) {
    super(message, solutions, cause);
  }
}

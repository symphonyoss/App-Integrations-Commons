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

/**
 * The content sent by third-party service can't be processed by the integration parser.
 *
 * This means the integration understands the content type of the request entity (hence a 415
 * (Unsupported Media Type) status code is inappropriate) and the syntax of the request entity is
 * correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the
 * HTTP payload.
 *
 * This exception may be used to identify the unknown events.
 *
 * Created by rsanchez on 04/05/17.
 */
public class WebHookUnprocessableEntityException extends WebhookException {

  public WebHookUnprocessableEntityException(String message, String... solutions) {
    super(message, solutions);
  }

}

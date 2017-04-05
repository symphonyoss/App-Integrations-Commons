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

package org.symphonyoss.integration.webhook.parser;

import org.symphonyoss.integration.model.config.IntegrationSettings;
import org.symphonyoss.integration.model.message.MessageMLVersion;
import org.symphonyoss.integration.webhook.WebHookPayload;

/**
 * Abstract factory responsible to create webhook parsers.
 * Created by rsanchez on 05/04/17.
 */
public interface WebHookParserFactory {

  /**
   * This factory should be used with this messageML version
   * @param version MessageML version
   * @return true if this factory accepts the messageML version, otherwise returns false
   */
  boolean accept(MessageMLVersion version);

  /**
   * Callback called when the integration settings were changed
   * @param settings Integration settings
   */
  void onConfigChange(IntegrationSettings settings);

  /**
   * Retrieve the webhook parser according to the event received as part of the payload. This
   * method shouldn't return null value. Rather than it should return a default parser.
   * @param payload Webhook payload
   * @return Webhook parser
   */
  WebHookParser getParser(WebHookPayload payload);

}

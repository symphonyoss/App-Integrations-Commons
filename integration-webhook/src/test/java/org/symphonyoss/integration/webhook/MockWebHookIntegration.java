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

package org.symphonyoss.integration.webhook;

import static org.symphonyoss.integration.messageml.MessageMLFormatConstants.MESSAGEML_END;
import static org.symphonyoss.integration.messageml.MessageMLFormatConstants.MESSAGEML_START;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Scope;
import org.symphonyoss.integration.json.JsonUtils;
import org.symphonyoss.integration.webhook.exception.WebHookParseException;

import java.io.IOException;

/**
 * Mock class for {@link WebHookIntegration}
 * Created by rsanchez on 10/08/16.
 */
@TestComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MockWebHookIntegration extends WebHookIntegration {

  @Override
  public String parse(WebHookPayload input) throws WebHookParseException {
    String formattedMessage;

    try {
      JsonNode rootNode = JsonUtils.readTree(input.getBody());
      formattedMessage = rootNode.asText();
    } catch (IOException e) {
      formattedMessage = "";
    }

    StringBuilder messageBuilder = new StringBuilder(MESSAGEML_START);
    messageBuilder.append(formattedMessage);
    messageBuilder.append(MESSAGEML_END);

    return messageBuilder.toString();
  }

}

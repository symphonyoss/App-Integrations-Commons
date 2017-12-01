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

import org.springframework.context.event.EventListener;
import org.symphonyoss.integration.event.MessageMLVersionUpdatedEventData;
import org.symphonyoss.integration.model.message.MessageMLVersion;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * Resolves the parser factory based on MessageML version.
 * Created by rsanchez on 05/04/17.
 */
public abstract class WebHookParserResolver {

  private WebHookParserFactory factory;

  /**
   * Initialize the default parser factory.
   */
  @PostConstruct
  public void init() {
    setupParserFactory(MessageMLVersion.V2);
  }

  /**
   * Setup the parser factory based on messageML version.
   */
  private void setupParserFactory(MessageMLVersion version) {
    for (WebHookParserFactory factory : getFactories()) {
      if (factory.accept(version)) {
        this.factory = factory;
        break;
      }
    }
  }

  /**
   * Handle events related to update MessageML version.
   * @param eventData MessageML version update event
   */
  @EventListener
  public void handleMessageMLVersionUpdatedEvent(MessageMLVersionUpdatedEventData eventData) {
    setupParserFactory(eventData.getVersion());
  }

  public WebHookParserFactory getFactory() {
    return factory;
  }

  protected abstract List<WebHookParserFactory> getFactories();

}

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.symphonyoss.integration.event.HealthCheckServiceEvent;
import org.symphonyoss.integration.event.MessageMLVersionUpdatedEvent;
import org.symphonyoss.integration.model.message.MessageMLVersion;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * Resolves the parser factory based on MessageML version.
 * Created by rsanchez on 05/04/17.
 */
public abstract class WebHookParserResolver {

  private static final String AGENT_SERVICE_NAME = "Agent";

  @Autowired
  private ApplicationEventPublisher publisher;

  private WebHookParserFactory factory;

  /**
   * Initialize the default parser factory.
   */
  @PostConstruct
  public void init() {
    setupParserFactory(MessageMLVersion.V1);
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
   * Dispatch health-check service event to monitor the Agent version.
   */
  public void healthCheckAgentService() {
    HealthCheckServiceEvent event = new HealthCheckServiceEvent(AGENT_SERVICE_NAME);
    publisher.publishEvent(event);
  }

  /**
   * Handle events related to update MessageML version. If the new version of MessageML is V2 I can
   * stop the scheduler to check the version, otherwise I need to reschedule the monitoring process.
   * @param event MessageML version update event
   */
  @EventListener
  public void handleMessageMLVersionUpdatedEvent(MessageMLVersionUpdatedEvent event) {
    setupParserFactory(event.getVersion());
  }

  public WebHookParserFactory getFactory() {
    return factory;
  }

  protected abstract List<WebHookParserFactory> getFactories();

}

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

package org.symphonyoss.integration.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class to delegate the metric objects creation to the specific controller.
 * Created by rsanchez on 12/12/16.
 */
@Component
public class IntegrationMetricsController {

  /**
   * Holds the list of controller to be initialized
   */
  @Autowired
  private List<IntegrationController> controllers;

  /**
   * Delegates the metric objects creation to the specific controller.
   * @param integration
   */
  public void addIntegrationTimer(String integration) {
    for (IntegrationController controller : controllers) {
      controller.initController(integration);
    }
  }
}

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

/**
 * Interface to define an specific controller that monitor information related to the integration.
 * Created by rsanchez on 22/12/16.
 */
public interface IntegrationController {


  /**
   * Initializes the metrics for an specific integration. Each controller should define which
   * metrics must monitor during the application execution.
   * @param integration Integration identifier
   */
  void initController(String integration);

}

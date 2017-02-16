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

package org.symphonyoss.integration.model.config;

import com.symphony.api.pod.model.V1Configuration;

/**
 * Settings for the integration. This class wraps the {@link V1Configuration} data.
 * Created by rsanchez on 15/02/17.
 */
public class IntegrationSettings {

  private String configurationId;

  private String type;

  private String name;

  private String description;

  private Boolean enabled;

  private Boolean visible;

  private Long owner;

  public IntegrationSettings(V1Configuration configuration) {
    if (configuration != null) {
      this.configurationId = configuration.getConfigurationId();
      this.type = configuration.getType();
      this.name = configuration.getName();
      this.description = configuration.getDescription();
      this.enabled = configuration.getEnabled();
      this.visible = configuration.getVisible();
      this.owner = configuration.getOwner();
    }
  }

  public String getConfigurationId() {
    return configurationId;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public Boolean getVisible() {
    return visible;
  }

  public Long getOwner() {
    return owner;
  }
}

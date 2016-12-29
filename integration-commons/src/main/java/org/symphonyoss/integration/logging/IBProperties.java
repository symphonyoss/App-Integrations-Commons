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

package org.symphonyoss.integration.logging;

import static com.symphony.atlas.config.SymphonyAtlas.ACCOUNT;
import static com.symphony.atlas.config.SymphonyAtlas.SECRET;
import static com.symphony.atlas.config.SymphonyAtlas.SYMPHONY_URL;

import com.symphony.atlas.IAtlas;
import com.symphony.config.ConfigurationException;

import org.symphonyoss.integration.IntegrationAtlas;

import java.util.Properties;

/**
 * Class responsible to get all properties to be able to connect in Symphony cloud
 *
 * Created by cmarcondes on 11/17/16.
 */
public class IBProperties {

  private IntegrationAtlas integrationAtlas;

  public IBProperties() {
    integrationAtlas = new IntegrationAtlas();
    integrationAtlas.init();
  }

  /**
   * Gets the properties from atlas file
   * @return Properties to connect into the cloud
   * @throws ConfigurationException - if couldn't get the properties
   */
  public Properties getProperties() {
    Properties properties = new Properties();

    IAtlas atlas = integrationAtlas.getAtlas();
    properties.put(SECRET, atlas.get(SECRET));
    properties.put(ACCOUNT, atlas.get(ACCOUNT));
    properties.put(SYMPHONY_URL, atlas.get(SYMPHONY_URL));

    return properties;
  }
}

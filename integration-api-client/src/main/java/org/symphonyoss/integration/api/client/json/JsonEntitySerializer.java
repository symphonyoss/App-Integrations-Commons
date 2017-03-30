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
 **/

package org.symphonyoss.integration.api.client.json;

import org.symphonyoss.integration.api.client.EntitySerializer;
import org.symphonyoss.integration.exception.RemoteApiException;

import javax.ws.rs.client.Entity;

/**
 * Serialize the given Java object into string entity JSON.
 *
 * Created by rsanchez on 24/03/17.
 */
public class JsonEntitySerializer implements EntitySerializer {

  /**
   * JSON helper class
   */
  private JsonUtils jsonUtils = new JsonUtils();

  /**
   * Serialize the given Java object into JSON string.
   */
  @Override
  public Entity<String> serialize(Object input) throws RemoteApiException {
    return Entity.json(jsonUtils.serialize(input));
  }

}

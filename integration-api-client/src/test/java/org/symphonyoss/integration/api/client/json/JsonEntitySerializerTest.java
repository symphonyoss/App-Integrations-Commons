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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.symphonyoss.integration.exception.RemoteApiException;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;

/**
 * Unit test for {@link JsonEntitySerializer}
 * Created by rsanchez on 27/03/17.
 */
public class JsonEntitySerializerTest {

  private JsonEntitySerializer serializer = new JsonEntitySerializer();

  @Test(expected = RemoteApiException.class)
  public void testInvalidJsonObject() throws RemoteApiException {
    serializer.serialize(new Object());
  }

  @Test
  public void testSerialize() throws RemoteApiException {
    Map<String, String> input = new HashMap<>();
    input.put("key", "value");

    Entity<String> result = serializer.serialize(input);

    Entity<String> expected = Entity.json("{\"key\":\"value\"}");

    assertEquals(expected, result);
  }

}

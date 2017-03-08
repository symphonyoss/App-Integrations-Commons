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

package org.symphonyoss.integration.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.symphonyoss.integration.exception.RemoteApiException;
import org.symphonyoss.integration.model.config.IntegrationSettings;

/**
 * Unit test for {@link JsonUtils}
 * Created by rsanchez on 23/02/17.
 */
public class JsonUtilsTest {

  private static final String MOCK_CONFIGURATION_ID = "57d6f328e4b0396198ce723d";

  private static final String MOCK_TYPE = "jiraWebHookIntegration";

  private JsonUtils utils = new JsonUtils();

  @Test
  public void testSerializeNullObject() throws RemoteApiException {
    String result = utils.serialize(null);
    assertNull(result);
  }

  @Test
  public void testSerializeInvalidObject() throws RemoteApiException {
    try {
      utils.serialize(new Object());
      fail();
    } catch (RemoteApiException e) {
      assertEquals(400, e.getCode());
    }
  }

  @Test
  public void testSerializeObject() throws RemoteApiException {
    IntegrationSettings settings = new IntegrationSettings();
    settings.setConfigurationId(MOCK_CONFIGURATION_ID);
    settings.setType(MOCK_TYPE);
    settings.setEnabled(Boolean.TRUE);
    settings.setVisible(Boolean.TRUE);

    String expected =
        "{\"configurationId\":\"57d6f328e4b0396198ce723d\",\"type\":\"jiraWebHookIntegration\","
            + "\"enabled\":true,\"visible\":true}";

    String result = utils.serialize(settings);

    assertEquals(expected, result);
  }

  @Test
  public void testDeserializeNullObject() throws RemoteApiException {
    try {
      utils.deserialize(null, IntegrationSettings.class);
      fail();
    } catch (RemoteApiException e) {
      assertEquals(500, e.getCode());
    }
  }

  @Test
  public void testDeserializeEmptyString() throws RemoteApiException {
    try {
      utils.deserialize("", IntegrationSettings.class);
      fail();
    } catch (RemoteApiException e) {
      assertEquals(500, e.getCode());
    }
  }

  @Test
  public void testDeserializeInvalidJSON() throws RemoteApiException {
    try {
      utils.deserialize("{\"id\"}", IntegrationSettings.class);
      fail();
    } catch (RemoteApiException e) {
      assertEquals(500, e.getCode());
    }
  }

  @Test
  public void testDeserializeToString() throws RemoteApiException {
    String expected = "{\"id\": 123}";
    String result = utils.deserialize(expected, String.class);
    assertEquals(expected, result);
  }

  @Test
  public void testDeserialize() throws RemoteApiException {
    String input = "{ \"configurationId\": \"57d6f328e4b0396198ce723d\", \"type\": "
        + "\"jiraWebHookIntegration\", \"enabled\": true, \"visible\": false }";

    IntegrationSettings result = utils.deserialize(input, IntegrationSettings.class);

    assertEquals(MOCK_CONFIGURATION_ID, result.getConfigurationId());
    assertEquals(MOCK_TYPE, result.getType());
    assertTrue(result.getEnabled());
    assertFalse(result.getVisible());
  }
}

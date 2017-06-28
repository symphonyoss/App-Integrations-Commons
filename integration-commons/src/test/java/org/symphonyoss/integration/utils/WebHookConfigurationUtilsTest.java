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

package org.symphonyoss.integration.utils;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;
import org.symphonyoss.integration.json.JsonUtils;
import org.symphonyoss.integration.model.config.IntegrationInstance;
import org.symphonyoss.integration.model.stream.StreamType;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Unit tests for {@link WebHookConfigurationUtils}
 * Created by rsanchez on 13/10/16.
 */
public class WebHookConfigurationUtilsTest {

  private static final String OPTIONAL_PROPERTIES = "{ \"lastPostedDate\": 1, \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream2\"] }";

  @Test
  public void testNullOptionalProperties() throws IOException {
    assertEquals(StreamType.NONE, WebHookConfigurationUtils.getStreamType(null));
  }

  @Test(expected = IOException.class)
  public void testInvalidOptionalProperties() throws IOException {
    WebHookConfigurationUtils.getStreamType("");
  }

  @Test
  public void testNullStreamType() throws IOException {
    assertEquals(StreamType.NONE, WebHookConfigurationUtils.getStreamType("{}"));
  }

  @Test
  public void testInvalidStreamType() throws IOException {
    assertEquals(StreamType.NONE, WebHookConfigurationUtils.getStreamType("{ \"streamType\": "
        + "\"TEST\"}"));
  }

  @Test
  public void testStreamType() throws IOException {
    assertEquals(StreamType.IM, WebHookConfigurationUtils.getStreamType("{ \"streamType\": "
        + "\"IM\"}"));
    assertEquals(StreamType.CHATROOM, WebHookConfigurationUtils.getStreamType("{ \"streamType\": "
        + "\"CHATROOM\"}"));
  }

  @Test
  public void testToJsonString() throws IOException {
    JsonNode jsonFile = JsonUtils.readTree(getClass().getClassLoader().getResourceAsStream("JSONUtils.json"));

    Assert.assertEquals("{\"field1\":\"value1\",\"field2\":\"value2\"}", WebHookConfigurationUtils.toJsonString(jsonFile));
  }

  @Test
  public void testFromJsonString() throws IOException {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JSONUtils.json");
    JsonNode jsonNode = JsonUtils.readTree(inputStream);
    String jsonTest = JsonUtils.writeValueAsString(jsonNode);
    ObjectNode objectNode = (ObjectNode) jsonNode;

    Assert.assertEquals(objectNode, WebHookConfigurationUtils.fromJsonString(jsonTest));
  }

  @Test
  public void testGetStreams() throws IOException {
    ArrayList<String> expected = new ArrayList<>();
    expected.add("stream1");
    expected.add("stream2");

    Assert.assertEquals(expected, WebHookConfigurationUtils.getStreams(OPTIONAL_PROPERTIES));
  }
}

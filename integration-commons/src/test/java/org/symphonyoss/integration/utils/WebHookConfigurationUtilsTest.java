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
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.symphonyoss.integration.json.JsonUtils;
import org.symphonyoss.integration.model.stream.StreamType;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    assertEquals("{\"field1\":\"value1\",\"field2\":\"value2\"}", WebHookConfigurationUtils.toJsonString(jsonFile));
  }

  @Test
  public void testFromJsonString() throws IOException {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JSONUtils.json");
    JsonNode jsonNode = JsonUtils.readTree(inputStream);
    String jsonTest = JsonUtils.writeValueAsString(jsonNode);
    ObjectNode objectNode = (ObjectNode) jsonNode;

    assertEquals(objectNode, WebHookConfigurationUtils.fromJsonString(jsonTest));
  }

  @Test
  public void testGetStreams() throws IOException {
    ArrayList<String> expected = new ArrayList<>();
    expected.add("stream1");
    expected.add("stream2");

    assertEquals(expected, WebHookConfigurationUtils.getStreams(OPTIONAL_PROPERTIES));
  }

  @Test
  public void testGetSupportedNotificationsNullOptionalProperties() throws IOException {
    List<String> supportedNotifications = WebHookConfigurationUtils.getSupportedNotifications(null);
    assertTrue(supportedNotifications.isEmpty());
  }

  @Test
  public void testGetSupportedNotifications() throws IOException {
    String optionalProperties = "{ \"notifications\": [ \"test1\", \"test2\" ] }";

    List<String> supportedNotifications = WebHookConfigurationUtils.getSupportedNotifications(optionalProperties);
    assertEquals(2, supportedNotifications.size());
    assertEquals("test1", supportedNotifications.get(0));
    assertEquals("test2", supportedNotifications.get(1));
  }

  @Test
  public void testEmptyStreams() throws IOException {
    ObjectNode optionalProperties = WebHookConfigurationUtils.setStreams(OPTIONAL_PROPERTIES,
        Collections.<String>emptyList());

    String expectedResult = "{\"lastPostedDate\": 1, \"owner\": \"owner\", \"streams\": [] }";
    assertEquals(WebHookConfigurationUtils.fromJsonString(expectedResult), optionalProperties);
  }

  @Test
  public void testStreams() throws IOException {
    List<String> expectedStreams = new ArrayList<>();
    expectedStreams.add("stream3");
    expectedStreams.add("stream4");

    ObjectNode optionalProperties = WebHookConfigurationUtils.setStreams(OPTIONAL_PROPERTIES, expectedStreams);

    String expectedResult =
        "{\"lastPostedDate\": 1, \"owner\": \"owner\", \"streams\": [ \"stream3\", \"stream4\"] }";
    assertEquals(WebHookConfigurationUtils.fromJsonString(expectedResult), optionalProperties);
  }

  @Test
  public void testEmptyRemovedStreams() throws IOException {
    List<String> expectedStreams = new ArrayList<>();
    expectedStreams.add("stream1");
    expectedStreams.add("stream3");

    ObjectNode optionalProperties = WebHookConfigurationUtils.setStreams(OPTIONAL_PROPERTIES, expectedStreams);
    optionalProperties = WebHookConfigurationUtils.setRemovedStreams(optionalProperties, Collections.<String>emptyList());

    String expectedResult =
        "{\"lastPostedDate\": 1, \"owner\": \"owner\", \"streams\": [ \"stream1\", \"stream3\"], "
            + " \"removed_streams\": [] }";
    assertEquals(WebHookConfigurationUtils.fromJsonString(expectedResult), optionalProperties);
  }

  @Test
  public void testRemovedStreams() throws IOException {
    List<String> expectedStreams = new ArrayList<>();
    expectedStreams.add("stream3");
    expectedStreams.add("stream4");

    List<String> expectedRemovedStreams = new ArrayList<>();
    expectedRemovedStreams.add("stream1");
    expectedRemovedStreams.add("stream2");

    ObjectNode optionalProperties = WebHookConfigurationUtils.setStreams(OPTIONAL_PROPERTIES, expectedStreams);
    optionalProperties = WebHookConfigurationUtils.setRemovedStreams(optionalProperties, expectedRemovedStreams);

    String expectedResult =
        "{\"lastPostedDate\": 1, \"owner\": \"owner\", \"streams\": [ \"stream3\", \"stream4\" ], "
            + " \"removed_streams\": [ \"stream1\", \"stream2\" ] }";

    assertEquals(WebHookConfigurationUtils.fromJsonString(expectedResult), optionalProperties);
  }

}

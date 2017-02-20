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

import org.junit.Test;
import org.symphonyoss.integration.model.stream.StreamType;

import java.io.IOException;

/**
 * Unit tests for {@link WebHookConfigurationUtils}
 * Created by rsanchez on 13/10/16.
 */
public class WebHookConfigurationUtilsTest {

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
}

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

package org.symphonyoss.integration.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Holds an {@link ObjectMapper} instance and exposes it through
 * basic methods, so we get to reuse the same instance across multiple integrations.
 *
 * Created by Milton Quilzini on 19/09/16.
 */
public class JsonUtils {

  private JsonUtils() {
  }

  private static ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Wraps the method from ObjectMapper
   * {@link ObjectMapper#readTree(String)}.
   */
  public static JsonNode readTree(String content) throws IOException {
    return objectMapper.readTree(content);
  }

  /**
   * Wraps the method from ObjectMapper
   * {@link ObjectMapper#readTree(InputStream)}.
   */
  public static JsonNode readTree(InputStream content) throws IOException {
    return objectMapper.readTree(content);
  }

  /**
   * Wraps the method from ObjectMapper
   * {@link ObjectMapper#writeValueAsString(Object)}.
   */
  public static String writeValueAsString(Object value) throws JsonProcessingException {
    return objectMapper.writeValueAsString(value);
  }

  /**
   * Read value from a JSON object and convert to another class
   *
   * @param value Object that represents JSON object
   * @param clazz Class to be converted
   * @return
   * @throws IOException
   */
  public static <T> T readValue(Object value, Class<T> clazz) throws IOException {
    String json = objectMapper.writeValueAsString(value);
    return objectMapper.readValue(json, clazz);
  }

}

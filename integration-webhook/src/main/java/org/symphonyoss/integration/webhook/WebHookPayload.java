/**
 * Copyright 2016-2017 Symphony Integrations - Symphony LLC
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.symphonyoss.integration.webhook;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstracts a Webhook payload's attributes, including its body, request headers and parameters.
 * <p>
 * Created by Robson Sanchez on 17/05/16.
 */
public class WebHookPayload {

  /**
   * Header attribute used to get the payload's content type.
   */
  private static final String CONTENT_TYPE = "content-type";

  /**
   * Holds parameters from one's request.
   */
  private Map<String, String> parameters = new HashMap<>();

  /**
   * Holds headers from one's request.
   */
  private Map<String, String> headers = new HashMap<>();

  /**
   * Payload body.
   */
  private String body;

  /**
   * Initializes the class attributes.
   * @param parameters from the request.
   * @param headers from the request.
   * @param body from the payload.
   */
  public WebHookPayload(Map<String, String> parameters, Map<String, String> headers, String body) {
    this.parameters = parameters;
    this.headers = headers;
    this.body = body;
  }

  /**
   * Returns the payload parameters.
   * @return request parameters.
   */
  public Map<String, String> getParameters() {
    return parameters;
  }

  /**
   * Returns the payload body.
   * @return the payload body.
   */
  public String getBody() {
    return body;
  }

  /**
   * Returns the request headers.
   * @return request headers.
   */
  public Map<String, String> getHeaders() {
    return headers;
  }

  /**
   * Add a new parameter
   * @param key Parameter key
   * @param value Parameter value
   */
  public void addParameter(String key, String value) {
    parameters.put(key, value);
  }

  /**
   * Returns the content type.
   * @return the content type.
   */
  public MediaType getContentType() {
    String contentType = null;
    if (headers != null) {
      contentType = headers.get(CONTENT_TYPE);
    }
    if (!StringUtils.isEmpty(contentType)) {
      try {
        return MediaType.valueOf(contentType);
      } catch (Exception e) {
        // Passed content type is invalid, we use default one
        return MediaType.WILDCARD_TYPE;
      }
    }
    return MediaType.WILDCARD_TYPE;
  }
}

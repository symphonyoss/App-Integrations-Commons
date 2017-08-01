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

package org.symphonyoss.integration.authorization;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstracts some authorization payload's attributes, including its body, request headers and
 * parameters.
 * Created by campidelli on 31-jul-17.
 */
public class AuthorizationPayload {

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
  public AuthorizationPayload(Map<String, String> parameters, Map<String, String> headers,
      String body) {
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
}

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

package org.symphonyoss.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represent connection info structure to external services, like the POD itself, Agent and Key
 * Manager.
 * Created by Milton Quilzini on 15/11/16.
 */
public class ConnectionInfo {
  @JsonProperty("host")
  private String host;

  @JsonProperty("port")
  private String port;

  @JsonProperty("auth_port")
  private String authPort;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getAuthPort() {
    return authPort;
  }

  public void setAuthPort(String authPort) {
    this.authPort = authPort;
  }
}

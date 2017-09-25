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

package org.symphonyoss.integration.model.yaml;

/**
 * Represent connection info structure to external services, like the POD itself, Agent and Key
 * Manager.
 * Created by Evandro Carrenho on 24/04/17.
 */
public class HttpClientConfig {

  /**
   * Default timeout for socket read.
   */
  public static final int DEFAULT_READ_TIMEOUT = 4000;

  /**
   * Default timeout for socket connection.
   */
  public static final int DEFAULT_CONNECT_TIMEOUT = 2000;

  /**
   * Max timeout for socket read.
   */
  public static final int MAX_READ_TIMEOUT = 30000;

  /**
   * Max timeout for socket connection.
   */
  public static final int MAX_CONNECT_TIMEOUT = 10000;

  /**
   * Connection pool - default total connections.
   */
  public static final int DEFAULT_TOTAL_CONNECTIONS = 60;

  /**
   * Connection pool - default total connections per route.
   */
  public static final int DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE = 20;

  /**
   * Connection pool - max total connections.
   */
  public static final int MAX_TOTAL_CONNECTIONS = 400;

  /**
   * Connection pool - max total connections per route.
   */
  public static final int MAX_TOTAL_CONNECTIONS_PER_ROUTE = 200;

  private Integer connectTimeout = DEFAULT_CONNECT_TIMEOUT;

  private Integer readTimeout = DEFAULT_READ_TIMEOUT;

  private Integer maxConnections = DEFAULT_TOTAL_CONNECTIONS;

  private Integer maxConnectionsPerRoute = DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE;

  public Integer getConnectTimeout() {
    return connectTimeout;
  }

  public void setConnectTimeout(Integer connectTimeout) {
    if (connectTimeout == null || connectTimeout <= 0) {
      connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    } else if (connectTimeout > MAX_CONNECT_TIMEOUT) {
      connectTimeout = MAX_CONNECT_TIMEOUT;
    }
    this.connectTimeout = connectTimeout;
  }

  public Integer getReadTimeout() {
    return readTimeout;
  }

  public void setReadTimeout(Integer readTimeout) {
    if (readTimeout == null || readTimeout <= 0) {
      readTimeout = DEFAULT_READ_TIMEOUT;
    } else if (readTimeout > MAX_READ_TIMEOUT) {
      readTimeout = MAX_READ_TIMEOUT;
    }
    this.readTimeout = readTimeout;
  }

  public Integer getMaxConnections() {
    return maxConnections;
  }

  public void setMaxConnections(Integer maxConnections) {
    if (maxConnections == null || maxConnections <= 0) {
      maxConnections = DEFAULT_TOTAL_CONNECTIONS;
    } else if (maxConnections > MAX_TOTAL_CONNECTIONS) {
      maxConnections = MAX_TOTAL_CONNECTIONS;
    }
    this.maxConnections = maxConnections;
  }

  public Integer getMaxConnectionsPerRoute() {
    return maxConnectionsPerRoute;
  }

  public void setMaxConnectionsPerRoute(Integer maxConnectionsPerRoute) {
    if (maxConnectionsPerRoute == null || maxConnectionsPerRoute <= 0) {
      maxConnectionsPerRoute = DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE;
    } else if (maxConnectionsPerRoute > MAX_TOTAL_CONNECTIONS_PER_ROUTE) {
      maxConnectionsPerRoute = MAX_TOTAL_CONNECTIONS_PER_ROUTE;
    }
    this.maxConnectionsPerRoute = maxConnectionsPerRoute;
  }

  @Override
  public String toString() {
    return "HttpClientConfig{" +
        "connectTimeout='" + getConnectTimeout() + '\'' +
        ", readTimeout='" + getReadTimeout() + '\'' +
        ", maxConnections='" + getMaxConnections() + '\'' +
        ", maxConnectionsPerRoute='" + getMaxConnectionsPerRoute() + '\'' +
        '}';
  }
}

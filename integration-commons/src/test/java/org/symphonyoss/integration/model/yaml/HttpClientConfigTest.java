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

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class to validate {@link HttpClientConfig}
 * Created by Evandro Carrenho on 24/04/16.
 */
public class HttpClientConfigTest {

  public static final String EXPECTED_DEFAULT_INITIAL_VALUES =
      "HttpClientConfig{connectTimeout='6000', readTimeout='4000', maxConnections='60', "
          + "maxConnectionsPerRoute='20'}";

  @Test
  public void testDefaultInitialValues() {
    HttpClientConfig httpClientConfig = new HttpClientConfig();

    assertEquals(httpClientConfig.getReadTimeout(), (Integer) HttpClientConfig.MIN_READ_TIMEOUT);
    assertEquals(httpClientConfig.getConnectTimeout(), (Integer) HttpClientConfig.MIN_CONNECT_TIMEOUT);
    assertEquals(httpClientConfig.getMaxConnections(), (Integer) HttpClientConfig.DEFAULT_TOTAL_CONNECTIONS);
    assertEquals(httpClientConfig.getMaxConnectionsPerRoute(), (Integer) HttpClientConfig.DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE);

    Assert.assertEquals(EXPECTED_DEFAULT_INITIAL_VALUES, httpClientConfig.toString());
  }

  @Test
  public void testAssignmentOfCustomValuesWithinRange() {
    HttpClientConfig httpClientConfig = new HttpClientConfig();
    httpClientConfig.setReadTimeout(HttpClientConfig.MIN_READ_TIMEOUT + 3);
    httpClientConfig.setConnectTimeout(HttpClientConfig.MIN_CONNECT_TIMEOUT + 3);
    httpClientConfig.setMaxConnections(HttpClientConfig.DEFAULT_TOTAL_CONNECTIONS - 1);
    httpClientConfig.setMaxConnectionsPerRoute(
        HttpClientConfig.DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE - 1);


    assertEquals(
        httpClientConfig.getReadTimeout(), (Integer) (HttpClientConfig.MIN_READ_TIMEOUT + 3));
    assertEquals(
        httpClientConfig.getConnectTimeout(), (Integer) (HttpClientConfig.MIN_CONNECT_TIMEOUT + 3));
    assertEquals(httpClientConfig.getMaxConnections(), (Integer) (HttpClientConfig.DEFAULT_TOTAL_CONNECTIONS - 1));
    assertEquals(httpClientConfig.getMaxConnectionsPerRoute(), (Integer) (HttpClientConfig.DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE - 1));
  }

  @Test
  public void testDefaultValuesOnNullAssignment() {
    HttpClientConfig httpClientConfig = new HttpClientConfig();
    httpClientConfig.setReadTimeout(null);
    httpClientConfig.setConnectTimeout(null);
    httpClientConfig.setMaxConnections(null);
    httpClientConfig.setMaxConnectionsPerRoute(null);


    assertEquals(httpClientConfig.getReadTimeout(), (Integer) HttpClientConfig.MIN_READ_TIMEOUT);
    assertEquals(httpClientConfig.getConnectTimeout(), (Integer) HttpClientConfig.MIN_CONNECT_TIMEOUT);
    assertEquals(httpClientConfig.getMaxConnections(), (Integer) HttpClientConfig.DEFAULT_TOTAL_CONNECTIONS);
    assertEquals(httpClientConfig.getMaxConnectionsPerRoute(), (Integer) HttpClientConfig.DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE);
  }

  @Test
  public void testDefaultValuesOnZeroAssignment() {
    HttpClientConfig httpClientConfig = new HttpClientConfig();
    httpClientConfig.setReadTimeout(0);
    httpClientConfig.setConnectTimeout(0);
    httpClientConfig.setMaxConnections(0);
    httpClientConfig.setMaxConnectionsPerRoute(0);

    assertEquals(httpClientConfig.getReadTimeout(), (Integer) HttpClientConfig.MIN_READ_TIMEOUT);
    assertEquals(httpClientConfig.getConnectTimeout(), (Integer) HttpClientConfig.MIN_CONNECT_TIMEOUT);
    assertEquals(httpClientConfig.getMaxConnections(), (Integer) HttpClientConfig.DEFAULT_TOTAL_CONNECTIONS);
    assertEquals(httpClientConfig.getMaxConnectionsPerRoute(), (Integer) HttpClientConfig.DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE);
  }

  @Test
  public void testDefaultValuesOnNegativeAssignment() {
    HttpClientConfig httpClientConfig = new HttpClientConfig();
    httpClientConfig.setReadTimeout(-1);
    httpClientConfig.setConnectTimeout(-1);
    httpClientConfig.setMaxConnections(-1);
    httpClientConfig.setMaxConnectionsPerRoute(-1);

    assertEquals(httpClientConfig.getReadTimeout(), (Integer) HttpClientConfig.MIN_READ_TIMEOUT);
    assertEquals(httpClientConfig.getConnectTimeout(), (Integer) HttpClientConfig.MIN_CONNECT_TIMEOUT);
    assertEquals(httpClientConfig.getMaxConnections(), (Integer) HttpClientConfig.DEFAULT_TOTAL_CONNECTIONS);
    assertEquals(httpClientConfig.getMaxConnectionsPerRoute(), (Integer) HttpClientConfig.DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE);
  }

  @Test
  public void testMaxValues() {
    HttpClientConfig httpClientConfig = new HttpClientConfig();
    httpClientConfig.setConnectTimeout(HttpClientConfig.MAX_CONNECT_TIMEOUT + 1);
    httpClientConfig.setReadTimeout(HttpClientConfig.MAX_READ_TIMEOUT + 1);
    httpClientConfig.setMaxConnections(HttpClientConfig.MAX_TOTAL_CONNECTIONS + 1);
    httpClientConfig.setMaxConnectionsPerRoute(HttpClientConfig.MAX_TOTAL_CONNECTIONS_PER_ROUTE + 1);

    assertEquals(httpClientConfig.getReadTimeout(), (Integer) HttpClientConfig.MAX_READ_TIMEOUT);
    assertEquals(httpClientConfig.getConnectTimeout(), (Integer) HttpClientConfig.MAX_CONNECT_TIMEOUT);
    assertEquals(httpClientConfig.getMaxConnections(), (Integer) HttpClientConfig.MAX_TOTAL_CONNECTIONS);
    assertEquals(httpClientConfig.getMaxConnectionsPerRoute(), (Integer) HttpClientConfig.MAX_TOTAL_CONNECTIONS_PER_ROUTE);
  }

  @Test
  public void testReadTimeoutLessThanConnectTimeout() {
    HttpClientConfig httpClientConfig = new HttpClientConfig();
    httpClientConfig.setConnectTimeout(6000);
    httpClientConfig.setReadTimeout(8000);
    assertEquals(httpClientConfig.getReadTimeout(), (Integer) 6000);
  }

}

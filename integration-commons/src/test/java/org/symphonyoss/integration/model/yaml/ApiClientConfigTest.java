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
 * Test class to validate {@link ApiClientConfig}
 * Created by Evandro Carrenho on 24/04/16.
 */
public class ApiClientConfigTest {

  public static final String EXPECTED_DEFAULT_INITIAL_VALUES =
      "ApiClientConfig{connectTimeout='2000', readTimeout='4000', maxConnections='60', "
          + "maxConnectionsPerRoute='20'}";

  @Test
  public void testDefaultInitialValues() {
    ApiClientConfig apiClientConfig = new ApiClientConfig();

    assertEquals(apiClientConfig.getReadTimeout(), (Integer) ApiClientConfig.DEFAULT_READ_TIMEOUT);
    assertEquals(apiClientConfig.getConnectTimeout(), (Integer) ApiClientConfig.DEFAULT_CONNECT_TIMEOUT);
    assertEquals(apiClientConfig.getMaxConnections(), (Integer) ApiClientConfig.DEFAULT_TOTAL_CONNECTIONS);
    assertEquals(apiClientConfig.getMaxConnectionsPerRoute(), (Integer) ApiClientConfig.DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE);

    Assert.assertEquals(EXPECTED_DEFAULT_INITIAL_VALUES, apiClientConfig.toString());
  }

  @Test
  public void testAssignmentOfCustomValuesWithinRange() {
    ApiClientConfig apiClientConfig = new ApiClientConfig();
    apiClientConfig.setReadTimeout(ApiClientConfig.DEFAULT_READ_TIMEOUT - 1);
    apiClientConfig.setConnectTimeout(ApiClientConfig.DEFAULT_CONNECT_TIMEOUT - 1);
    apiClientConfig.setMaxConnections(ApiClientConfig.DEFAULT_TOTAL_CONNECTIONS - 1);
    apiClientConfig.setMaxConnectionsPerRoute(ApiClientConfig.DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE - 1);


    assertEquals(apiClientConfig.getReadTimeout(), (Integer) (ApiClientConfig.DEFAULT_READ_TIMEOUT - 1));
    assertEquals(apiClientConfig.getConnectTimeout(), (Integer) (ApiClientConfig.DEFAULT_CONNECT_TIMEOUT - 1));
    assertEquals(apiClientConfig.getMaxConnections(), (Integer) (ApiClientConfig.DEFAULT_TOTAL_CONNECTIONS - 1));
    assertEquals(apiClientConfig.getMaxConnectionsPerRoute(), (Integer) (ApiClientConfig.DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE - 1));
  }

  @Test
  public void testDefaultValuesOnNullAssignment() {
    ApiClientConfig apiClientConfig = new ApiClientConfig();
    apiClientConfig.setReadTimeout(null);
    apiClientConfig.setConnectTimeout(null);
    apiClientConfig.setMaxConnections(null);
    apiClientConfig.setMaxConnectionsPerRoute(null);


    assertEquals(apiClientConfig.getReadTimeout(), (Integer) ApiClientConfig.DEFAULT_READ_TIMEOUT);
    assertEquals(apiClientConfig.getConnectTimeout(), (Integer) ApiClientConfig.DEFAULT_CONNECT_TIMEOUT);
    assertEquals(apiClientConfig.getMaxConnections(), (Integer) ApiClientConfig.DEFAULT_TOTAL_CONNECTIONS);
    assertEquals(apiClientConfig.getMaxConnectionsPerRoute(), (Integer) ApiClientConfig.DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE);
  }

  @Test
  public void testDefaultValuesOnZeroAssignment() {
    ApiClientConfig apiClientConfig = new ApiClientConfig();
    apiClientConfig.setReadTimeout(0);
    apiClientConfig.setConnectTimeout(0);
    apiClientConfig.setMaxConnections(0);
    apiClientConfig.setMaxConnectionsPerRoute(0);

    assertEquals(apiClientConfig.getReadTimeout(), (Integer) ApiClientConfig.DEFAULT_READ_TIMEOUT);
    assertEquals(apiClientConfig.getConnectTimeout(), (Integer) ApiClientConfig.DEFAULT_CONNECT_TIMEOUT);
    assertEquals(apiClientConfig.getMaxConnections(), (Integer) ApiClientConfig.DEFAULT_TOTAL_CONNECTIONS);
    assertEquals(apiClientConfig.getMaxConnectionsPerRoute(), (Integer) ApiClientConfig.DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE);
  }

  @Test
  public void testDefaultValuesOnNegativeAssignment() {
    ApiClientConfig apiClientConfig = new ApiClientConfig();
    apiClientConfig.setReadTimeout(-1);
    apiClientConfig.setConnectTimeout(-1);
    apiClientConfig.setMaxConnections(-1);
    apiClientConfig.setMaxConnectionsPerRoute(-1);

    assertEquals(apiClientConfig.getReadTimeout(), (Integer) ApiClientConfig.DEFAULT_READ_TIMEOUT);
    assertEquals(apiClientConfig.getConnectTimeout(), (Integer) ApiClientConfig.DEFAULT_CONNECT_TIMEOUT);
    assertEquals(apiClientConfig.getMaxConnections(), (Integer) ApiClientConfig.DEFAULT_TOTAL_CONNECTIONS);
    assertEquals(apiClientConfig.getMaxConnectionsPerRoute(), (Integer) ApiClientConfig.DEFAULT_TOTAL_CONNECTIONS_PER_ROUTE);
  }

  @Test
  public void testMaxValues() {
    ApiClientConfig apiClientConfig = new ApiClientConfig();
    apiClientConfig.setReadTimeout(ApiClientConfig.MAX_READ_TIMEOUT + 1);
    apiClientConfig.setConnectTimeout(ApiClientConfig.MAX_CONNECT_TIMEOUT + 1);
    apiClientConfig.setMaxConnections(ApiClientConfig.MAX_TOTAL_CONNECTIONS + 1);
    apiClientConfig.setMaxConnectionsPerRoute(ApiClientConfig.MAX_TOTAL_CONNECTIONS_PER_ROUTE + 1);

    assertEquals(apiClientConfig.getReadTimeout(), (Integer) ApiClientConfig.MAX_READ_TIMEOUT);
    assertEquals(apiClientConfig.getConnectTimeout(), (Integer) ApiClientConfig.MAX_CONNECT_TIMEOUT);
    assertEquals(apiClientConfig.getMaxConnections(), (Integer) ApiClientConfig.MAX_TOTAL_CONNECTIONS);
    assertEquals(apiClientConfig.getMaxConnectionsPerRoute(), (Integer) ApiClientConfig.MAX_TOTAL_CONNECTIONS_PER_ROUTE);
  }

}

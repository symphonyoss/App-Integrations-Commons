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
 **/

package org.symphonyoss.integration.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.symphonyoss.integration.api.client.json.JsonEntitySerializer;
import org.symphonyoss.integration.api.client.metrics.ApiMetricsController;
import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.authentication.api.enums.ServiceName;
import org.symphonyoss.integration.exception.RemoteApiException;
import sun.net.www.http.HttpClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Unit test for {@link SymphonyApiClient}
 * Created by campidelli on 6/19/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SymphonyApiClientTest {

  private static ServiceName SERVICE_NAME = ServiceName.POD;

  private static final String MOCK_SESSION_TOKEN = "480d9f271e54d02ea835154fb57628290da817d1c";

  private static final String PATH = "/path";

  private static final String SESSION_TOKEN_HEADER = "sessionToken";

  @Mock
  private ApiMetricsController metricsController;

  @Mock
  private AuthenticationProxy proxy;

  @Mock
  private Client client;

  @Mock
  private WebTarget target;

  @Mock
  private Invocation.Builder invocationBuilder;

  @Mock
  private Response response;

  private SymphonyApiClient apiClient;

  @Before
  public void init() {
    apiClient = new MockSymphonyApiClient(SERVICE_NAME);

    ReflectionTestUtils.setField(apiClient, "metricsController", metricsController);
    ReflectionTestUtils.setField(apiClient, "authenticationProxy", proxy);

    doReturn(client).when(proxy).httpClientForSessionToken(MOCK_SESSION_TOKEN, SERVICE_NAME);
    doReturn(target).when(client).target(apiClient.getBasePath());
    doReturn(target).when(target).path(PATH);
    doReturn(invocationBuilder).when(target).request();
    doReturn(invocationBuilder).when(invocationBuilder).header(anyString(), anyString());
    doReturn(Boolean.TRUE).when(response).hasEntity();
  }

  @Test
  public void testInit() {
    HttpApiClient client = apiClient.getClient();
    assertNull(client);

    apiClient.init();

    client = apiClient.getClient();
    assertNotNull(client);
  }

  @Test
  public void testDoGet() throws RemoteApiException {
    doReturn(response).when(invocationBuilder).get();
    doReturn(Response.Status.OK).when(response).getStatusInfo();
    doReturn("{ \"result\": \"OK\" }").when(response).readEntity(String.class);

    apiClient.init();

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    Map<String, Object> result =
        apiClient.doGet(PATH, headerParams, Collections.<String, String>emptyMap(), Map.class);

    assertFalse(result.isEmpty());
    assertTrue(result.containsKey("result"));
    assertEquals("OK", result.get("result"));
  }

  @Test
  public void testDoPost() throws RemoteApiException {
    doReturn(response).when(invocationBuilder).post(any(Entity.class));
    doReturn(Response.Status.OK).when(response).getStatusInfo();
    doReturn("{ \"result\": \"OK\" }").when(response).readEntity(String.class);

    apiClient.init();

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    Map<String, Object> result =
        apiClient.doPost(PATH, headerParams, Collections.<String, String>emptyMap(), "{}",
            Map.class);

    assertFalse(result.isEmpty());
    assertTrue(result.containsKey("result"));
    assertEquals("OK", result.get("result"));
  }

  @Test
  public void testDoPut() throws RemoteApiException {
    doReturn(response).when(invocationBuilder).put(any(Entity.class));
    doReturn(Response.Status.OK).when(response).getStatusInfo();
    doReturn("{ \"result\": \"OK\" }").when(response).readEntity(String.class);

    apiClient.init();

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    Map<String, Object> result =
        apiClient.doPut(PATH, headerParams, Collections.<String, String>emptyMap(), "{}",
            Map.class);

    assertFalse(result.isEmpty());
    assertTrue(result.containsKey("result"));
    assertEquals("OK", result.get("result"));
  }

  @Test
  public void testDoDelete() throws RemoteApiException {
    doReturn(response).when(invocationBuilder).delete();
    doReturn(Response.Status.OK).when(response).getStatusInfo();
    doReturn("{ \"result\": \"OK\" }").when(response).readEntity(String.class);

    apiClient.init();

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    Map<String, Object> result =
        apiClient.doDelete(PATH, headerParams, Collections.<String, String>emptyMap(), Map.class);

    assertFalse(result.isEmpty());
    assertTrue(result.containsKey("result"));
    assertEquals("OK", result.get("result"));
  }

  @Test
  public void testEscapeString() {
    apiClient.init();
    assertEquals("test%20space", apiClient.escapeString("test space"));
  }

  @Test
  public void testClientForContext() {
    apiClient.init();

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    Client result =
        apiClient.getClientForContext(Collections.<String, String>emptyMap(), headerParams);

    assertEquals(client, result);
  }

}

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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.authentication.AuthenticationToken;
import org.symphonyoss.integration.exception.RemoteApiException;
import org.symphonyoss.integration.model.config.IntegrationSettings;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for {@link ReAuthenticationApiClient}
 * Created by rsanchez on 23/02/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReAuthenticationApiClientTest {

  private static final String SESSION_TOKEN_HEADER = "sessionToken";

  private static final String MOCK_PATH = "/v1/configuration/create";

  private static final String MOCK_SESSION_TOKEN = "480d9f271e54d02ea835154fb57628290da817d1c";

  private static final String MOCK_NEW_SESSION_TOKEN = "591f8g361e54d02ea835154fb57628290da817d1c";

  private static final String MOCK_KM_TOKEN = "602g9h451e54d02ea835154fb57628290da817d1c";

  private static final String MOCK_CONFIGURATION_ID = "57d6f328e4b0396198ce723d";

  private static final String MOCK_TYPE = "jiraWebHookIntegration";

  @Mock
  private AuthenticationProxy proxy;

  @Mock
  private HttpApiClient apiClient;

  private ReAuthenticationApiClient reAuthApiClient;

  @Before
  public void init() {
    this.reAuthApiClient = new ReAuthenticationApiClient(proxy, apiClient);
  }

  @Test(expected = RemoteApiException.class)
  public void testDoGetNullSessionToken() throws RemoteApiException {
    RemoteApiException remoteApiException = new RemoteApiException(401, "Unauthorized");

    doThrow(remoteApiException).when(apiClient)
        .doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), IntegrationSettings.class);

    reAuthApiClient.doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), IntegrationSettings.class);
  }

  @Test(expected = RemoteApiException.class)
  public void testDoGetInvalidSessionToken() throws RemoteApiException {
    RemoteApiException remoteApiException = new RemoteApiException(401, "Unauthorized");

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    doThrow(remoteApiException).when(apiClient)
        .doGet(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
            IntegrationSettings.class);

    doThrow(remoteApiException).when(proxy)
        .reAuthSessionOrThrow(MOCK_SESSION_TOKEN, remoteApiException);

    reAuthApiClient.doGet(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
        IntegrationSettings.class);
  }

  @Test
  public void testDoGetSuccess() throws RemoteApiException {
    RemoteApiException remoteApiException = new RemoteApiException(401, "Unauthorized");

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    doThrow(remoteApiException).when(apiClient)
        .doGet(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
            IntegrationSettings.class);

    AuthenticationToken token = new AuthenticationToken(MOCK_NEW_SESSION_TOKEN, MOCK_KM_TOKEN);
    doReturn(token).when(proxy).reAuthSessionOrThrow(MOCK_SESSION_TOKEN, remoteApiException);

    IntegrationSettings expected = new IntegrationSettings();
    expected.setConfigurationId(MOCK_CONFIGURATION_ID);
    expected.setType(MOCK_TYPE);
    expected.setEnabled(Boolean.TRUE);
    expected.setVisible(Boolean.TRUE);

    Map<String, String> newParams = new HashMap<>();
    newParams.put(SESSION_TOKEN_HEADER, MOCK_NEW_SESSION_TOKEN);

    doReturn(expected).when(apiClient)
        .doGet(MOCK_PATH, newParams, Collections.<String, String>emptyMap(),
            IntegrationSettings.class);

    IntegrationSettings result =
        reAuthApiClient.doGet(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
            IntegrationSettings.class);

    assertEquals(expected, result);
  }

  @Test(expected = RemoteApiException.class)
  public void testDoPostNullSessionToken() throws RemoteApiException {
    RemoteApiException remoteApiException = new RemoteApiException(401, "Unauthorized");

    doThrow(remoteApiException).when(apiClient)
        .doPost(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), null, IntegrationSettings.class);

    reAuthApiClient.doPost(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), null, IntegrationSettings.class);
  }

  @Test(expected = RemoteApiException.class)
  public void testDoPostInvalidSessionToken() throws RemoteApiException {
    RemoteApiException remoteApiException = new RemoteApiException(401, "Unauthorized");

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    doThrow(remoteApiException).when(apiClient)
        .doPost(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(), null,
            IntegrationSettings.class);

    doThrow(remoteApiException).when(proxy)
        .reAuthSessionOrThrow(MOCK_SESSION_TOKEN, remoteApiException);

    reAuthApiClient.doPost(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(), null,
        IntegrationSettings.class);
  }

  @Test
  public void testDoPostSuccess() throws RemoteApiException {
    RemoteApiException remoteApiException = new RemoteApiException(401, "Unauthorized");

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    doThrow(remoteApiException).when(apiClient)
        .doPost(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(), null,
            IntegrationSettings.class);

    AuthenticationToken token = new AuthenticationToken(MOCK_NEW_SESSION_TOKEN, MOCK_KM_TOKEN);
    doReturn(token).when(proxy).reAuthSessionOrThrow(MOCK_SESSION_TOKEN, remoteApiException);

    IntegrationSettings expected = new IntegrationSettings();
    expected.setConfigurationId(MOCK_CONFIGURATION_ID);
    expected.setType(MOCK_TYPE);
    expected.setEnabled(Boolean.TRUE);
    expected.setVisible(Boolean.TRUE);

    Map<String, String> newParams = new HashMap<>();
    newParams.put(SESSION_TOKEN_HEADER, MOCK_NEW_SESSION_TOKEN);

    doReturn(expected).when(apiClient)
        .doPost(MOCK_PATH, newParams, Collections.<String, String>emptyMap(), null,
            IntegrationSettings.class);

    IntegrationSettings result =
        reAuthApiClient.doPost(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
            null, IntegrationSettings.class);

    assertEquals(expected, result);
  }

  @Test(expected = RemoteApiException.class)
  public void testDoPutNullSessionToken() throws RemoteApiException {
    RemoteApiException remoteApiException = new RemoteApiException(401, "Unauthorized");

    doThrow(remoteApiException).when(apiClient)
        .doPut(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), null, IntegrationSettings.class);

    reAuthApiClient.doPut(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), null, IntegrationSettings.class);
  }

  @Test(expected = RemoteApiException.class)
  public void testDoPutInvalidSessionToken() throws RemoteApiException {
    RemoteApiException remoteApiException = new RemoteApiException(401, "Unauthorized");

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    doThrow(remoteApiException).when(apiClient)
        .doPut(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(), null,
            IntegrationSettings.class);

    doThrow(remoteApiException).when(proxy)
        .reAuthSessionOrThrow(MOCK_SESSION_TOKEN, remoteApiException);

    reAuthApiClient.doPut(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(), null,
        IntegrationSettings.class);
  }

  @Test
  public void testDoPutSuccess() throws RemoteApiException {
    RemoteApiException remoteApiException = new RemoteApiException(401, "Unauthorized");

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    doThrow(remoteApiException).when(apiClient)
        .doPut(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(), null,
            IntegrationSettings.class);

    AuthenticationToken token = new AuthenticationToken(MOCK_NEW_SESSION_TOKEN, MOCK_KM_TOKEN);
    doReturn(token).when(proxy).reAuthSessionOrThrow(MOCK_SESSION_TOKEN, remoteApiException);

    IntegrationSettings expected = new IntegrationSettings();
    expected.setConfigurationId(MOCK_CONFIGURATION_ID);
    expected.setType(MOCK_TYPE);
    expected.setEnabled(Boolean.TRUE);
    expected.setVisible(Boolean.TRUE);

    Map<String, String> newParams = new HashMap<>();
    newParams.put(SESSION_TOKEN_HEADER, MOCK_NEW_SESSION_TOKEN);

    doReturn(expected).when(apiClient)
        .doPut(MOCK_PATH, newParams, Collections.<String, String>emptyMap(), null,
            IntegrationSettings.class);

    IntegrationSettings result =
        reAuthApiClient.doPut(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
            null, IntegrationSettings.class);

    assertEquals(expected, result);
  }

  @Test(expected = RemoteApiException.class)
  public void testDoDeleteNullSessionToken() throws RemoteApiException {
    RemoteApiException remoteApiException = new RemoteApiException(401, "Unauthorized");

    doThrow(remoteApiException).when(apiClient)
        .doDelete(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), IntegrationSettings.class);

    reAuthApiClient.doDelete(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), IntegrationSettings.class);
  }

  @Test(expected = RemoteApiException.class)
  public void testDoDeleteInvalidSessionToken() throws RemoteApiException {
    RemoteApiException remoteApiException = new RemoteApiException(401, "Unauthorized");

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    doThrow(remoteApiException).when(apiClient)
        .doDelete(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
            IntegrationSettings.class);

    doThrow(remoteApiException).when(proxy)
        .reAuthSessionOrThrow(MOCK_SESSION_TOKEN, remoteApiException);

    reAuthApiClient.doDelete(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
        IntegrationSettings.class);
  }

  @Test
  public void testDoDeleteSuccess() throws RemoteApiException {
    RemoteApiException remoteApiException = new RemoteApiException(401, "Unauthorized");

    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    doThrow(remoteApiException).when(apiClient)
        .doDelete(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
            IntegrationSettings.class);

    AuthenticationToken token = new AuthenticationToken(MOCK_NEW_SESSION_TOKEN, MOCK_KM_TOKEN);
    doReturn(token).when(proxy).reAuthSessionOrThrow(MOCK_SESSION_TOKEN, remoteApiException);

    IntegrationSettings expected = new IntegrationSettings();
    expected.setConfigurationId(MOCK_CONFIGURATION_ID);
    expected.setType(MOCK_TYPE);
    expected.setEnabled(Boolean.TRUE);
    expected.setVisible(Boolean.TRUE);

    Map<String, String> newParams = new HashMap<>();
    newParams.put(SESSION_TOKEN_HEADER, MOCK_NEW_SESSION_TOKEN);

    doReturn(expected).when(apiClient)
        .doDelete(MOCK_PATH, newParams, Collections.<String, String>emptyMap(),
            IntegrationSettings.class);

    IntegrationSettings result =
        reAuthApiClient.doDelete(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
            IntegrationSettings.class);

    assertEquals(expected, result);
  }
}

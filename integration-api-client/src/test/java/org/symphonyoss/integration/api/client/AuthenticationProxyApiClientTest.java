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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.authentication.AuthenticationProxy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;

/**
 * Unit test for {@link AuthenticationProxyApiClient}
 * Created by rsanchez on 23/02/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationProxyApiClientTest {

  private static final String SESSION_TOKEN_HEADER = "sessionToken";

  private static final String USER_SESSION_HEADER = "userSession";

  private static final String MOCK_SESSION_TOKEN = "480d9f271e54d02ea835154fb57628290da817d1c";

  private static final String MOCK_USERNAME = "jiraWebHookIntegration";

  @Mock
  private AuthenticationProxy proxy;

  @Mock
  private Client userClient;

  @Mock
  private Client sessionClient;

  @Mock
  private EntitySerializer serializer;

  private AuthenticationProxyApiClient proxyApiClient;

  @Before
  public void init() {
    this.proxyApiClient = new AuthenticationProxyApiClient(serializer, proxy);
    doReturn(userClient).when(proxy).httpClientForUser(MOCK_USERNAME);
    doReturn(sessionClient).when(proxy).httpClientForSessionToken(MOCK_SESSION_TOKEN);
  }

  @Test
  public void testClientBasedOnUsername() {
    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(USER_SESSION_HEADER, MOCK_USERNAME);

    Client result =
        proxyApiClient.getClientForContext(Collections.<String, String>emptyMap(), headerParams);
    assertEquals(userClient, result);
  }

  @Test
  public void testClientBasedOnSessionToken() {
    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SESSION_TOKEN_HEADER, MOCK_SESSION_TOKEN);

    Client result =
        proxyApiClient.getClientForContext(Collections.<String, String>emptyMap(), headerParams);
    assertEquals(sessionClient, result);
  }
}

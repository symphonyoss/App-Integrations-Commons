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
import org.symphonyoss.integration.authentication.api.AppAuthenticationProxy;
import org.symphonyoss.integration.authentication.api.enums.ServiceName;


import java.util.Collections;
import java.util.Map;

import javax.ws.rs.client.Client;

/**
 * Unit tests for {@link AppAuthenticationProxyApiClient}
 *
 * Created by rsanchez on 09/08/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppAuthenticationProxyApiClientTest {

  private static final String APP_ID = "testApp";

  private static final String APP_ID_HEADER = "appId";

  private static final ServiceName SERVICE_NAME = ServiceName.POD;

  @Mock
  private AppAuthenticationProxy proxy;

  @Mock
  private EntitySerializer serializer;

  @Mock
  private Client client;

  private AppAuthenticationProxyApiClient apiClient;

  @Before
  public void init() {
    this.apiClient = new AppAuthenticationProxyApiClient(serializer, proxy, SERVICE_NAME);
  }

  @Test
  public void testGetClientForContext() {
    doReturn(client).when(proxy).httpClientForApplication(APP_ID, SERVICE_NAME);

    Map<String, String> headers = Collections.singletonMap(APP_ID_HEADER, APP_ID);

    Client result = apiClient.getClientForContext(Collections.<String, String>emptyMap(), headers);
    assertEquals(client, result);
  }

}

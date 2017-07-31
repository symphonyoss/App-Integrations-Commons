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

package org.symphonyoss.integration.api.client;

import static org.mockito.Mockito.doThrow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.exception.RemoteApiException;
import org.symphonyoss.integration.authentication.exception.ConnectivityException;
import org.symphonyoss.integration.model.config.IntegrationSettings;

import java.io.IOException;
import java.util.Collections;

import javax.ws.rs.ProcessingException;

/**
 * Unit test for {@link ConnectivityApiClientDecorator}
 * Created by rsanchez on 23/02/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConnectivityApiClientDecoratorTest {

  private static final String MOCK_PATH = "/v1/configuration/create";

  private static final String MOCK_SERVICE = "Mock Service";

  @Mock
  private HttpApiClient apiClient;

  private ConnectivityApiClientDecorator decorator;

  @Before
  public void init() {
    this.decorator = new ConnectivityApiClientDecorator(MOCK_SERVICE, apiClient);
  }

  @Test(expected = ProcessingException.class)
  public void testDoGetProcessingException() throws RemoteApiException {
    ProcessingException exception = new ProcessingException(new RuntimeException());

    doThrow(exception).when(apiClient)
        .doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), IntegrationSettings.class);

    decorator.doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), IntegrationSettings.class);
  }

  @Test(expected = ConnectivityException.class)
  public void testDoGetConnectivityException() throws RemoteApiException {
    ProcessingException exception = new ProcessingException(new IOException());

    doThrow(exception).when(apiClient)
        .doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), IntegrationSettings.class);

    decorator.doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), IntegrationSettings.class);
  }

  @Test(expected = ProcessingException.class)
  public void testDoPostProcessingException() throws RemoteApiException {
    ProcessingException exception = new ProcessingException(new RuntimeException());

    doThrow(exception).when(apiClient)
        .doPost(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), null, IntegrationSettings.class);

    decorator.doPost(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), null, IntegrationSettings.class);
  }

  @Test(expected = ConnectivityException.class)
  public void testDoPostConnectivityException() throws RemoteApiException {
    ProcessingException exception = new ProcessingException(new IOException());

    doThrow(exception).when(apiClient)
        .doPost(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), null, IntegrationSettings.class);

    decorator.doPost(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), null, IntegrationSettings.class);
  }

  @Test(expected = ProcessingException.class)
  public void testDoPutProcessingException() throws RemoteApiException {
    ProcessingException exception = new ProcessingException(new RuntimeException());

    doThrow(exception).when(apiClient)
        .doPut(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), null, IntegrationSettings.class);

    decorator.doPut(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), null, IntegrationSettings.class);
  }

  @Test(expected = ConnectivityException.class)
  public void testDoPutConnectivityException() throws RemoteApiException {
    ProcessingException exception = new ProcessingException(new IOException());

    doThrow(exception).when(apiClient)
        .doPut(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), null, IntegrationSettings.class);

    decorator.doPut(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), null, IntegrationSettings.class);
  }

  @Test(expected = ProcessingException.class)
  public void testDoDeleteProcessingException() throws RemoteApiException {
    ProcessingException exception = new ProcessingException(new RuntimeException());

    doThrow(exception).when(apiClient)
        .doDelete(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), IntegrationSettings.class);

    decorator.doDelete(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), IntegrationSettings.class);
  }

  @Test(expected = ConnectivityException.class)
  public void testDoDeleteConnectivityException() throws RemoteApiException {
    ProcessingException exception = new ProcessingException(new IOException());

    doThrow(exception).when(apiClient)
        .doDelete(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), IntegrationSettings.class);

    decorator.doDelete(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), IntegrationSettings.class);
  }
}

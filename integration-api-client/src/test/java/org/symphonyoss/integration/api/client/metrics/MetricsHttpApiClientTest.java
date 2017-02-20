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

package org.symphonyoss.integration.api.client.metrics;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.codahale.metrics.Timer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.api.client.HttpApiClient;
import org.symphonyoss.integration.exception.RemoteApiException;
import org.symphonyoss.integration.model.config.IntegrationSettings;

import java.util.Collections;

/**
 * Unit test for {@link MetricsHttpApiClient}
 * Created by rsanchez on 23/02/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MetricsHttpApiClientTest {

  private static final String MOCK_PATH = "/v1/configuration/create";

  @Mock
  private ApiMetricsController metricsController;

  @Mock
  private HttpApiClient apiClient;

  @Mock
  private Timer.Context context;

  private MetricsHttpApiClient metricsHttpApiClient;

  @Before
  public void init() {
    this.metricsHttpApiClient = new MetricsHttpApiClient(metricsController, apiClient);
    doReturn(context).when(metricsController).startApiCall(MOCK_PATH);
  }

  @Test
  public void testDoGetFail() throws RemoteApiException {
    doThrow(RemoteApiException.class).when(apiClient)
        .doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), IntegrationSettings.class);

    try {
      metricsHttpApiClient.doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
          Collections.<String, String>emptyMap(), IntegrationSettings.class);
      fail();
    } catch (RemoteApiException e) {
      verify(metricsController, times(1)).startApiCall(MOCK_PATH);
      verify(metricsController, times(0)).finishApiCall(context, MOCK_PATH, true);
      verify(metricsController, times(1)).finishApiCall(context, MOCK_PATH, false);
    }
  }

  @Test
  public void testDoGetSuccess() throws RemoteApiException {
    metricsHttpApiClient.doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), IntegrationSettings.class);

    verify(metricsController, times(1)).startApiCall(MOCK_PATH);
    verify(metricsController, times(0)).finishApiCall(context, MOCK_PATH, false);
    verify(metricsController, times(1)).finishApiCall(context, MOCK_PATH, true);
  }

  @Test
  public void testDoPostFail() throws RemoteApiException {
    doThrow(RemoteApiException.class).when(apiClient)
        .doPost(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), null, IntegrationSettings.class);

    try {
      metricsHttpApiClient.doPost(MOCK_PATH, Collections.<String, String>emptyMap(),
          Collections.<String, String>emptyMap(), null, IntegrationSettings.class);
      fail();
    } catch (RemoteApiException e) {
      verify(metricsController, times(1)).startApiCall(MOCK_PATH);
      verify(metricsController, times(0)).finishApiCall(context, MOCK_PATH, true);
      verify(metricsController, times(1)).finishApiCall(context, MOCK_PATH, false);
    }
  }

  @Test
  public void testDoPostSuccess() throws RemoteApiException {
    metricsHttpApiClient.doPost(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), null, IntegrationSettings.class);

    verify(metricsController, times(1)).startApiCall(MOCK_PATH);
    verify(metricsController, times(0)).finishApiCall(context, MOCK_PATH, false);
    verify(metricsController, times(1)).finishApiCall(context, MOCK_PATH, true);
  }

  @Test
  public void testDoPutFail() throws RemoteApiException {
    doThrow(RemoteApiException.class).when(apiClient)
        .doPut(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), null, IntegrationSettings.class);

    try {
      metricsHttpApiClient.doPut(MOCK_PATH, Collections.<String, String>emptyMap(),
          Collections.<String, String>emptyMap(), null, IntegrationSettings.class);
      fail();
    } catch (RemoteApiException e) {
      verify(metricsController, times(1)).startApiCall(MOCK_PATH);
      verify(metricsController, times(0)).finishApiCall(context, MOCK_PATH, true);
      verify(metricsController, times(1)).finishApiCall(context, MOCK_PATH, false);
    }
  }

  @Test
  public void testDoPutSuccess() throws RemoteApiException {
    metricsHttpApiClient.doPut(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), null, IntegrationSettings.class);

    verify(metricsController, times(1)).startApiCall(MOCK_PATH);
    verify(metricsController, times(0)).finishApiCall(context, MOCK_PATH, false);
    verify(metricsController, times(1)).finishApiCall(context, MOCK_PATH, true);
  }

  @Test
  public void testDoDeleteFail() throws RemoteApiException {
    doThrow(RemoteApiException.class).when(apiClient)
        .doDelete(MOCK_PATH, Collections.<String, String>emptyMap(),
            Collections.<String, String>emptyMap(), IntegrationSettings.class);

    try {
      metricsHttpApiClient.doDelete(MOCK_PATH, Collections.<String, String>emptyMap(),
          Collections.<String, String>emptyMap(), IntegrationSettings.class);
      fail();
    } catch (RemoteApiException e) {
      verify(metricsController, times(1)).startApiCall(MOCK_PATH);
      verify(metricsController, times(0)).finishApiCall(context, MOCK_PATH, true);
      verify(metricsController, times(1)).finishApiCall(context, MOCK_PATH, false);
    }
  }

  @Test
  public void testDoDeleteSuccess() throws RemoteApiException {
    metricsHttpApiClient.doDelete(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), IntegrationSettings.class);

    verify(metricsController, times(1)).startApiCall(MOCK_PATH);
    verify(metricsController, times(0)).finishApiCall(context, MOCK_PATH, false);
    verify(metricsController, times(1)).finishApiCall(context, MOCK_PATH, true);
  }

}

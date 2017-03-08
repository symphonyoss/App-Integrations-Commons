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

package org.symphonyoss.integration.api.client.trace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.symphonyoss.integration.logging.DistributedTracingUtils.TRACE_ID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.MDC;
import org.symphonyoss.integration.api.client.HttpApiClient;
import org.symphonyoss.integration.exception.RemoteApiException;
import org.symphonyoss.integration.model.config.IntegrationSettings;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Unit test for {@link TraceLoggingApiClient}
 * Created by rsanchez on 23/02/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TraceLoggingApiClientTest {

  private static final String MOCK_PATH = "/v1/configuration/create";

  @Mock
  private HttpApiClient apiClient;

  private TraceLoggingApiClient traceLoggingApiClient;

  private String traceId;

  @Before
  public void init() {
    this.traceId = UUID.randomUUID().toString();
    this.traceLoggingApiClient = new TraceLoggingApiClient(apiClient);

    MDC.clear();
  }

  @Test
  public void testDoGetEmptyTrace() throws RemoteApiException {
    Map<String, String> headerParams = new HashMap<>();

    traceLoggingApiClient.doGet(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
        IntegrationSettings.class);

    assertNull(headerParams.get(TRACE_ID));
  }

  @Test
  public void testDoGetTrace() throws RemoteApiException {
    Map<String, String> headerParams = new HashMap<>();

    MDC.put(TRACE_ID, traceId);

    traceLoggingApiClient.doGet(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
        IntegrationSettings.class);

    assertEquals(traceId, headerParams.get(TRACE_ID));
  }

  @Test
  public void testDoPostEmptyTrace() throws RemoteApiException {
    Map<String, String> headerParams = new HashMap<>();

    traceLoggingApiClient.doPost(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
        null, IntegrationSettings.class);

    assertNull(headerParams.get(TRACE_ID));
  }

  @Test
  public void testDoPostTrace() throws RemoteApiException {
    Map<String, String> headerParams = new HashMap<>();

    MDC.put(TRACE_ID, traceId);

    traceLoggingApiClient.doPost(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
        null, IntegrationSettings.class);

    assertEquals(traceId, headerParams.get(TRACE_ID));
  }

  @Test
  public void testDoPutEmptyTrace() throws RemoteApiException {
    Map<String, String> headerParams = new HashMap<>();

    traceLoggingApiClient.doPut(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
        null, IntegrationSettings.class);

    assertNull(headerParams.get(TRACE_ID));
  }

  @Test
  public void testDoPutTrace() throws RemoteApiException {
    Map<String, String> headerParams = new HashMap<>();

    MDC.put(TRACE_ID, traceId);

    traceLoggingApiClient.doPut(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
        null, IntegrationSettings.class);

    assertEquals(traceId, headerParams.get(TRACE_ID));
  }

  @Test
  public void testDoDeleteEmptyTrace() throws RemoteApiException {
    Map<String, String> headerParams = new HashMap<>();

    traceLoggingApiClient.doDelete(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
        IntegrationSettings.class);

    assertNull(headerParams.get(TRACE_ID));
  }

  @Test
  public void testDoDeleteTrace() throws RemoteApiException {
    Map<String, String> headerParams = new HashMap<>();

    MDC.put(TRACE_ID, traceId);

    traceLoggingApiClient.doDelete(MOCK_PATH, headerParams, Collections.<String, String>emptyMap(),
        IntegrationSettings.class);

    assertEquals(traceId, headerParams.get(TRACE_ID));
  }

}

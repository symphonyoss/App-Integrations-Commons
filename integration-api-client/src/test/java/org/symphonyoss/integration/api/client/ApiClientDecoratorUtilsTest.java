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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.symphonyoss.integration.logging.DistributedTracingUtils.TRACE_ID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Unit test for {@link ApiClientDecoratorUtils}
 * Created by rsanchez on 23/02/17.
 */
public class ApiClientDecoratorUtilsTest {

  @Before
  public void init() {
    MDC.clear();
  }

  @Test
  public void testEmptyTraceId() {
    Map<String, String> headerParams = new HashMap<>();
    ApiClientDecoratorUtils.setHeaderTraceId(headerParams);
    assertNull(headerParams.get(TRACE_ID));
  }

  @Test
  public void testTraceId() {
    String traceId = UUID.randomUUID().toString();
    MDC.put(TRACE_ID, traceId);

    Map<String, String> headerParams = new HashMap<>();
    ApiClientDecoratorUtils.setHeaderTraceId(headerParams);
    assertEquals(traceId, headerParams.get(TRACE_ID));
  }

}

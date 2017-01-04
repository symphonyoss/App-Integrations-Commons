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

package org.symphonyoss.integration.webhook.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Unit tests to validate {@link ParserMetricsController}
 * Created by rsanchez on 14/12/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ParserMetricsControllerTest {

  private static final String TEST_INTEGRATION = "testIntegration";

  private static final String OTHER_INTEGRATION = "otherIntegration";

  @Spy
  private MetricRegistry metricsRegistry = new MetricRegistry();

  @Spy
  private ConcurrentMap<String, Timer> timerByParser = new ConcurrentHashMap<>();

  @Spy
  private ConcurrentMap<String, Counter> parserSuccessCounters = new ConcurrentHashMap<>();

  @Spy
  private ConcurrentMap<String, Counter> parserFailCounters = new ConcurrentHashMap<>();

  @InjectMocks
  private ParserMetricsController controller = new ParserMetricsController();

  @Test
  public void testParserExecution() {
    Timer.Context nullContext = controller.startParserExecution(TEST_INTEGRATION);
    assertNull(nullContext);

    controller.initController(TEST_INTEGRATION);

    Timer.Context testContextSuccess = controller.startParserExecution(TEST_INTEGRATION);
    Timer.Context testContextFailed = controller.startParserExecution(TEST_INTEGRATION);

    assertNotNull(testContextSuccess);
    assertNotNull(testContextFailed);

    controller.finishParserExecution(null, OTHER_INTEGRATION, true);

    controller.finishParserExecution(testContextSuccess, TEST_INTEGRATION, true);
    controller.finishParserExecution(testContextFailed, TEST_INTEGRATION, false);

    assertEquals(2, timerByParser.get(TEST_INTEGRATION).getCount());
    assertEquals(1, parserSuccessCounters.get(TEST_INTEGRATION).getCount());
    assertEquals(1, parserFailCounters.get(TEST_INTEGRATION).getCount());
  }
}

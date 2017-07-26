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

package org.symphonyoss.integration.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.symphonyoss.integration.logging.DistributedTracingUtils.MAX_TRACE_ID_SIZE;
import static org.symphonyoss.integration.logging.DistributedTracingUtils.TRACE_ID_SEPARATOR;
import static org.symphonyoss.integration.logging.DistributedTracingUtils.TRACE_ID_SIZE;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test for {@link DistributedTracingUtils}

 * Created by Evandro Carrenho on 05/24/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class DistributedTracingUtilsTest {

  @Before
  public void clearMDC() {
    DistributedTracingUtils.clearMDC();
    assertTrue(StringUtils.isBlank(DistributedTracingUtils.getMDC()));
  }

  @Test
  public void testSetMDCWithBaseId() {
    // Sets the 1st MDC id and asserts only one is present
    DistributedTracingUtils.setMDC();
    String previousMdc = assertOnlyOneIdIsPresent();

    // Sets the 2nd MDC id and asserts the current MDC contains the 1st id
    DistributedTracingUtils.setMDC(previousMdc);
    previousMdc = assertStartsWith(previousMdc);

    // Sets the 3rd MDC id and asserts the current MDC contains the 1st and 2nd id's
    DistributedTracingUtils.setMDC(previousMdc);
    previousMdc = assertStartsWith(previousMdc);
    assertEquals(MAX_TRACE_ID_SIZE, previousMdc.length());

    // Sets the 4th MDC id and asserts the current MDC contains the 2nd and 3rd id's
    DistributedTracingUtils.setMDC(previousMdc);
    previousMdc = assertStartsWith(StringUtils.substringAfter(previousMdc, TRACE_ID_SEPARATOR));
    assertEquals(MAX_TRACE_ID_SIZE, previousMdc.length());

  }

  @Test
  public void testSetMDCMultipleTimes() {
    DistributedTracingUtils.setMDC();
    String previousMdc = assertOnlyOneIdIsPresent();
    DistributedTracingUtils.setMDC();
    assertOnlyOneIdIsPresent();
    assertNotEquals(previousMdc, DistributedTracingUtils.getMDC());
  }

  @Test
  public void testSetMDCMultipleTimesWithBlankBaseId() {
    DistributedTracingUtils.setMDC(null);
    String previousMdc = assertOnlyOneIdIsPresent();
    DistributedTracingUtils.setMDC(StringUtils.EMPTY);
    assertOnlyOneIdIsPresent();
    assertNotEquals(previousMdc, DistributedTracingUtils.getMDC());
  }

  private String assertOnlyOneIdIsPresent() {
    String mdc = DistributedTracingUtils.getMDC();
    assertEquals(TRACE_ID_SIZE, mdc.length());
    assertFalse(StringUtils.contains(mdc, TRACE_ID_SEPARATOR));
    return mdc;
  }

  private String assertStartsWith(String prefixMdc) {
    String mdc = DistributedTracingUtils.getMDC();
    assertTrue(StringUtils.startsWith(mdc, prefixMdc + TRACE_ID_SEPARATOR));
    return mdc;
  }

}

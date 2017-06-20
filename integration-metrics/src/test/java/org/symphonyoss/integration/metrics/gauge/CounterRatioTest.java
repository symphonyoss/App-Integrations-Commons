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

package org.symphonyoss.integration.metrics.gauge;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.doReturn;

import com.codahale.metrics.Counting;
import com.codahale.metrics.RatioGauge;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test for {@link CounterRatio}
 * Created by campidelli on 6/20/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CounterRatioTest {

  @Mock
  private Counting numeratorMeter;

  @Mock
  private Counting denominatorMeter;

  private CounterRatio counterRatio;

  @Before
  public void init() {
    counterRatio = new CounterRatio(numeratorMeter, denominatorMeter);
  }

  @Test
  public void testGetRatio() {
    doReturn(2L).when(numeratorMeter).getCount();
    doReturn(4L).when(denominatorMeter).getCount();

    RatioGauge.Ratio ratio = counterRatio.getRatio();
    double result = ratio.getValue();

    assertEquals(0.5, result);
  }

}

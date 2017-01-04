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

import com.codahale.metrics.Counting;
import com.codahale.metrics.RatioGauge;

/**
 * Calcute the ratio of an specific event
 * Created by rsanchez on 12/12/16.
 */
public class CounterRatio extends RatioGauge {

  /**
   * Ratio numerator
   */
  private final Counting numeratorMeter;

  /**
   * Ratio denominator
   */
  private final Counting denominatorMeter;

  /**
   * Constructor to create a gauge which is the ratio between two numbers.
   * @param numeratorMeter Ratio numerator
   * @param denominatorMeter Ratio denominator
   */
  public CounterRatio(Counting numeratorMeter, Counting denominatorMeter) {
    this.numeratorMeter = numeratorMeter;
    this.denominatorMeter = denominatorMeter;
  }

  @Override
  protected Ratio getRatio() {
    return Ratio.of(numeratorMeter.getCount(), denominatorMeter.getCount());
  }

}

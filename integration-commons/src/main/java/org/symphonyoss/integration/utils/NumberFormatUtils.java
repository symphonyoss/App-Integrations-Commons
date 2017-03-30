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

package org.symphonyoss.integration.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * Utility class to deal with number formatting.
 *
 * Created by crepache on 30/03/17.
 */
public class NumberFormatUtils {

  /**
   * Formats a number (passed in a string parameter) with separators for thousands, millions etc.
   * @param locale indicates the format that should be applied to the number.
   * @param value contains the number to be formatted (as a string).
   * @return formatted value
   */
  public static String formatValueWithLocale(Locale locale, String value) {
    BigDecimal valueBigDecimal = new BigDecimal(value);

    DecimalFormat decimalFormat = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(locale));

    decimalFormat.setMinimumFractionDigits(2);
    decimalFormat.setParseBigDecimal (true);

    return decimalFormat.format(valueBigDecimal);
  }

}

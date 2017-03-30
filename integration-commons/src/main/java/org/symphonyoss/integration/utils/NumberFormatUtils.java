package org.symphonyoss.integration.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * Utility class to deal with Numbers
 *
 * Created by crepache on 30/03/17.
 */
public class NumberFormatUtils {

  /**
   * Format String value with Locale
   * @param locale constant
   * @param value String
   * @return value format
   */
  public static String formatValueWithLocale(Locale locale, String value) {
    BigDecimal valueBigDecimal = new BigDecimal(value);

    DecimalFormat decimalFormat = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(locale));

    decimalFormat.setMinimumFractionDigits(2);
    decimalFormat.setParseBigDecimal (true);

    return decimalFormat.format(valueBigDecimal);
  }

}

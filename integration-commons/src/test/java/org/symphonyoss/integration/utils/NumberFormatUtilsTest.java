package org.symphonyoss.integration.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * Unit tests for {@link NumberFormatUtils}.
 * Created by crepache on 21/06/17.
 */
public class NumberFormatUtilsTest {

  @Test
  public void testNumberFormatUtils() {
    Assert.assertEquals("1.00", NumberFormatUtils.formatValueWithLocale(Locale.US, "1"));
    Assert.assertEquals("1,000.00", NumberFormatUtils.formatValueWithLocale(Locale.US, "1000"));
    Assert.assertEquals("10,000,000,000.00", NumberFormatUtils.formatValueWithLocale(Locale.US, "10000000000"));
  }

  @Test(expected = NumberFormatException.class)
  public void testInvalidNumber() {
    NumberFormatUtils.formatValueWithLocale(Locale.US, "teste");
  }

}

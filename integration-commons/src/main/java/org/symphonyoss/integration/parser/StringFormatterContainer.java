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

package org.symphonyoss.integration.parser;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Class used to encapsulate the logic from {@code String.format} and its parameters.
 * It can be used when both parameters and the string to format need to be carried together.
 * The class is also flexible to allow the change of any of its properties.
 */
public class StringFormatterContainer {
  private String formatString;
  private Map<String, String> values;

  public StringFormatterContainer(String formatString, Map<String, String> values) {
    this.formatString = formatString;
    this.values = values;
  }

  /**
   * This method iterates over the {@code values} entrySet and replaces the occurrence of each key
   * for its corresponding value.
   * The substitution is done for one occurrence, i.e., the method String.replace is used to do the
   * interpolation.
   * @return The string with the values in the {@code values} map interpolated in the {@code
   * formatString} string
   */
  public String format() {
    if (StringUtils.isNotEmpty(formatString)) {
      for (Map.Entry<String, String> entry : values.entrySet()) {
        formatString = formatString.replace(entry.getKey(), entry.getValue());
      }
      return formatString;
    }
    return StringUtils.EMPTY;
  }

  public String getFormatString() {
    return formatString;
  }

  public void setFormatString(String formatString) {
    this.formatString = formatString;
  }

  public Map<String, String> getValues() {
    return values;
  }

  public void setValues(Map<String, String> values) {
    this.values = values;
  }
}

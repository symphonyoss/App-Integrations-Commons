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

import java.util.List;

/**
 * Class used to encapsulate the logic from {@code String.format} and its parameters.
 * It can be used when both parameters and the string to format need to be carried together.
 * The class is also flexible to allow the change of any of its properties.
 */
public class StringFormatterContainer {
  private String formatString;
  private List<String> values;

  public StringFormatterContainer(String formatString, List<String> values) {
    this.formatString = formatString;
    this.values = values;
  }

  public String format() {
    if (StringUtils.isNotEmpty(formatString)) {
      return String.format(formatString, values.toArray());
    }
    return StringUtils.EMPTY;
  }

  public String getFormatString() {
    return formatString;
  }

  public void setFormatString(String formatString) {
    this.formatString = formatString;
  }

  public List<String> getValues() {
    return values;
  }

  public void setValues(List<String> values) {
    this.values = values;
  }
}

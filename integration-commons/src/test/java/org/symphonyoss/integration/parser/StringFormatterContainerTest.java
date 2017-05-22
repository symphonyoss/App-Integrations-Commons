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

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * Created by apimentel on 22/05/17.
 */
public class StringFormatterContainerTest {

  private String expected;

  private String testFormat(String formatString, List<String> values, String expected) {
    this.expected = expected;

    StringFormatterContainer formatterContainer =
        new StringFormatterContainer(formatString, values);

    return formatterContainer.format();
  }

  @Test
  public void format() throws Exception {
    String result = testFormat("valid string, with %s, %s parameters", Arrays.asList("1", "2"),
        "valid string, with 1, 2 parameters");
    assertEquals(expected, result);
  }

  @Test
  public void formatInvalidFormatString() throws Exception {
    String result = testFormat("valid string, with %s parameters", Arrays.asList("1", "2"),
        "valid string, with 1 parameters");
    assertEquals(expected, result);
  }

  @Test(expected = IllegalFormatException.class)
  public void formatInvalidArguments() throws Exception {
    testFormat("valid string, with %s, %s parameters", Arrays.asList("1"), null);
  }

}
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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.IllegalFormatException;
import java.util.LinkedHashMap;

/**
 * Created by apimentel on 22/05/17.
 */
public class StringFormatterContainerTest {

  private static final String KEY_WALRUS = "fe587af6-3ff5-11e7-a919-92ebcb67fe33";
  private static final String KEY_PLUTO = "fe587d6c-3ff5-11e7-a919-92ebcb67fe33";
  private static final String VALUE_WALRUS = "I'm the walrus";
  private static final String VALUE_PLUTO = "Who am I?";

  private static final String FORMAT_STRING_TWO_PARAMETERS =
      "The real quote was:" + KEY_WALRUS + "; or: " + KEY_PLUTO;
  private static final String FORMAT_STRING_ONE_PARAMETER = "The real quote was: " + KEY_WALRUS;
  private static final String EXPECTED_TWO_VALUES =
      "The real quote was:" + VALUE_WALRUS + "; or: " + VALUE_PLUTO;
  ;
  private static final String EXPECTED_ONE_VALUE = "The real quote was: " + VALUE_WALRUS;;
  private String expected;
  private LinkedHashMap<String, String> values = null;

  @Before
  public void setUp() {
    values = new LinkedHashMap<>();
    values.put(KEY_WALRUS, VALUE_WALRUS);
    values.put(KEY_PLUTO, VALUE_PLUTO);
  }

  private String testFormat(String formatString, LinkedHashMap<String, String> values,
      String expected) {
    this.expected = expected;

    StringFormatterContainer formatterContainer =
        new StringFormatterContainer(formatString, values);

    return formatterContainer.format();
  }

  @Test
  public void format() throws Exception {
    String result = testFormat(FORMAT_STRING_TWO_PARAMETERS, values, EXPECTED_TWO_VALUES);
    assertEquals(expected, result);
  }

  @Test
  public void formatInvalidFormatString() throws Exception {
    String result = testFormat(FORMAT_STRING_ONE_PARAMETER, values, EXPECTED_ONE_VALUE);
    assertEquals(expected, result);
  }

  @Test(expected = IllegalFormatException.class)
  public void formatInvalidArguments() throws Exception {
    testFormat(FORMAT_STRING_TWO_PARAMETERS, values, null);
  }

}
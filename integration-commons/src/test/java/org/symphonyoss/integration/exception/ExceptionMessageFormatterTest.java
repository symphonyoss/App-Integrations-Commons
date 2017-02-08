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

package org.symphonyoss.integration.exception;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.text.StrBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for {@link ExceptionMessageFormatter}
 *
 * Created by cmarcondes on 10/27/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExceptionMessageFormatterTest {


  private static final String COMPONENT = "Component: ";
  private static final String MESSAGE = "Message: ";
  private static final String SOLUTIONS = "Solutions: ";
  private static final String STACKTRACE = "Stack trace: ";
  private static final String BREAK_LINE = "\n";

  private static final String SOLUTION1 = "My solution 1";
  private static final String SOLUTION2 = "My solution 2";
  private static final String COMPONENT_NAME = "Unit Test";
  private static final String STR_MESSAGE = "Something whent wrong";
  private static final String UNKNOWN = "Unknown";
  private static final String NONE = "None";
  private static final String NO_SOLUTION_MESSAGE =
      "No solution has been cataloged for troubleshooting this problem.";
  private static final String[] SOLUTIONS_ARRAY = {SOLUTION1, SOLUTION2};

  @Test
  public void testMessageExceptionWithThrowable() {
    String exceptionMessage = "Something is null";
    NullPointerException exception = new NullPointerException(exceptionMessage);
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE, exception, SOLUTIONS_ARRAY);

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(COMPONENT_NAME)
        .append(MESSAGE).appendln(STR_MESSAGE)
        .appendln(SOLUTIONS)
        .appendWithSeparators(SOLUTIONS_ARRAY, BREAK_LINE).appendNewLine()
        .append(STACKTRACE).appendln(exceptionMessage);

    assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithThrowableOneSolution() {
    String exceptionMessage = "Something is null";
    NullPointerException exception = new NullPointerException(exceptionMessage);
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE, exception, SOLUTION1);

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(COMPONENT_NAME)
        .append(MESSAGE).appendln(STR_MESSAGE)
        .appendln(SOLUTIONS)
        .appendln(SOLUTION1)
        .append(STACKTRACE).appendln(exceptionMessage);

    assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithoutThrowable() {
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE,
        SOLUTION1, SOLUTION2);

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(COMPONENT_NAME)
        .append(MESSAGE).appendln(STR_MESSAGE)
        .appendln(SOLUTIONS)
        .appendWithSeparators(SOLUTIONS_ARRAY, BREAK_LINE).appendNewLine();

    assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithoutThrowableOneSolution() {
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE, SOLUTION1);

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(COMPONENT_NAME)
        .append(MESSAGE).appendln(STR_MESSAGE)
        .appendln(SOLUTIONS)
        .appendln(SOLUTION1);

    assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithoutSolutions() {
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE);

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(COMPONENT_NAME)
        .append(MESSAGE).appendln(STR_MESSAGE)
        .appendln(SOLUTIONS)
        .appendln(NO_SOLUTION_MESSAGE);

    assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithoutVariables() {
    String actual = ExceptionMessageFormatter.format("", "");

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(UNKNOWN)
        .append(MESSAGE).appendln(NONE)
        .appendln(SOLUTIONS)
        .appendln(NO_SOLUTION_MESSAGE);

    assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithVariablesNull() {
    String actual = ExceptionMessageFormatter.format(null, null);

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(UNKNOWN)
        .append(MESSAGE).appendln(NONE)
        .appendln(SOLUTIONS)
        .appendln(NO_SOLUTION_MESSAGE);

    assertEquals(expected.toString(), actual);
  }

}

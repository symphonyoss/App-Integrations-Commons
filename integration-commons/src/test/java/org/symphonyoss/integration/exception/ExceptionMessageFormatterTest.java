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

import org.apache.commons.lang3.text.StrBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for {@link ExceptionMessageFormatter}
 *
 * Created by cmarcondes on 10/27/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExceptionMessageFormatterTest {


  private static final String COMPONENT = "Component: ";
  private static final String MESSAGE = "Message: ";
  private static final String ROOT_CAUSE = "Root cause: ";
  private static final String SOLUTIONS = "Solutions: ";
  private static final String STACKTRACE = "Stack trace: ";
  private static final String BREAK_LINE = "\n";

  private static final String SOLUTION = "My solution";
  private static final List<String> MY_SOLUTIONS = Arrays.asList(SOLUTION);
  private static final String COMPONENT_NAME = "Unit Test";
  private static final String STR_MESSAGE = "Something whent wrong";
  private static final String UNKNOWN = "Unknown";
  private static final String NONE = "None";
  private static final String NO_SOLUTION_MESSAGE =
      "No solution has been cataloged for troubleshooting this problem.";

  @Test
  public void testMessageExceptionWithThroable() {
    String exceptionMessage = "Something is null";
    NullPointerException exception = new NullPointerException(exceptionMessage);
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE, MY_SOLUTIONS,
        exception
    );

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(COMPONENT_NAME)
        .append(MESSAGE).appendln(STR_MESSAGE)
        .appendln(SOLUTIONS)
        .appendWithSeparators(MY_SOLUTIONS, BREAK_LINE).appendNewLine()
        .append(STACKTRACE).appendln(exceptionMessage);

    Assert.assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithThroableOneSolution() {
    String exceptionMessage = "Something is null";
    NullPointerException exception = new NullPointerException(exceptionMessage);
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE, SOLUTION,
        exception
    );

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(COMPONENT_NAME)
        .append(MESSAGE).appendln(STR_MESSAGE)
        .appendln(SOLUTIONS)
        .appendln(SOLUTION)
        .append(STACKTRACE).appendln(exceptionMessage);

    Assert.assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithoutThroable() {
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE,
        MY_SOLUTIONS);

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(COMPONENT_NAME)
        .append(MESSAGE).appendln(STR_MESSAGE)
        .appendln(SOLUTIONS)
        .appendWithSeparators(MY_SOLUTIONS, BREAK_LINE).appendNewLine();

    Assert.assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithoutThroableOneSolution() {
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE, SOLUTION);

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(COMPONENT_NAME)
        .append(MESSAGE).appendln(STR_MESSAGE)
        .appendln(SOLUTIONS)
        .appendln(SOLUTION);

    Assert.assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithoutSolutions() {
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE);

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(COMPONENT_NAME)
        .append(MESSAGE).appendln(STR_MESSAGE)
        .appendln(SOLUTIONS)
        .appendln(NO_SOLUTION_MESSAGE);

    Assert.assertEquals(actual, expected.toString());
  }

  @Test
  public void testMessageExceptionWithoutVariables() {
    String actual = ExceptionMessageFormatter.format("", "");

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(UNKNOWN)
        .append(MESSAGE).appendln(NONE)
        .appendln(SOLUTIONS)
        .appendln(NO_SOLUTION_MESSAGE);

    Assert.assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithVariablesNull() {
    String actual = ExceptionMessageFormatter.format(null, null);

    StrBuilder expected = new StrBuilder(BREAK_LINE)
        .append(COMPONENT).appendln(UNKNOWN)
        .append(MESSAGE).appendln(NONE)
        .appendln(SOLUTIONS)
        .appendln(NO_SOLUTION_MESSAGE);

    Assert.assertEquals(expected.toString(), actual);
  }

}

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

  private static final String SOLUTION1 = "My solutions 1";
  private static final String SOLUTION2 = "My solutions 2";
  private static final String COMPONENT_NAME = "Unit Test";
  private static final String STR_MESSAGE = "Something whent wrong";
  private static final String UNKNOWN = "Unknown";
  private static final String NONE = "None";
  private static final String[] SOLUTIONS_ARRAY = {SOLUTION1, SOLUTION2};

  @Test
  public void testMessageExceptionWithThrowable() {
    String exceptionMessage = "Something is null";
    NullPointerException exception = new NullPointerException(exceptionMessage);
    String actual =
        ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE, exception, SOLUTIONS_ARRAY);

    String expected = new ExpectedMessageBuilder()
        .component(COMPONENT_NAME)
        .message(STR_MESSAGE)
        .solutions(SOLUTIONS_ARRAY)
        .stackTrace(exceptionMessage)
        .build();

    assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithThrowableOneSolution() {
    String exceptionMessage = "Something is null";
    NullPointerException exception = new NullPointerException(exceptionMessage);
    String actual =
        ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE, exception, SOLUTION1);

    String expected = new ExpectedMessageBuilder()
        .component(COMPONENT_NAME)
        .message(STR_MESSAGE)
        .solutions(SOLUTION1)
        .stackTrace(exceptionMessage)
        .build();

    assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithoutThrowable() {
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE,
        SOLUTION1, SOLUTION2);

    String expected = new ExpectedMessageBuilder()
        .component(COMPONENT_NAME)
        .message(STR_MESSAGE)
        .solutions(SOLUTIONS_ARRAY)
        .build();

    assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithoutThrowableOneSolution() {
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE, SOLUTION1);

    String expected = new ExpectedMessageBuilder()
        .component(COMPONENT_NAME)
        .message(STR_MESSAGE)
        .solutions(SOLUTION1)
        .build();

    assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithoutSolutions() {
    String actual = ExceptionMessageFormatter.format(COMPONENT_NAME, STR_MESSAGE);

    String expected = new ExpectedMessageBuilder()
        .component(COMPONENT_NAME)
        .message(STR_MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithoutVariables() {
    String actual = ExceptionMessageFormatter.format("", "");

    String expected = new ExpectedMessageBuilder()
        .component(UNKNOWN)
        .message(NONE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    assertEquals(expected.toString(), actual);
  }

  @Test
  public void testMessageExceptionWithVariablesNull() {
    String actual = ExceptionMessageFormatter.format(null, null);

    String expected = new ExpectedMessageBuilder()
        .component(UNKNOWN)
        .message(NONE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    assertEquals(expected.toString(), actual);
  }

}

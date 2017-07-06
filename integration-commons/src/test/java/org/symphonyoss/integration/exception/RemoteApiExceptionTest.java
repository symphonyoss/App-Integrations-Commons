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

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Unit tests for {@link RemoteApiException}.
 * Created by crepache on 23/06/17.
 */
public class RemoteApiExceptionTest {

  private static final String COMMONS = "Commons";
  private static final String MESSAGE = "MESSAGE";
  private static final String NONE = "None";
  private static final String SOLUTION = "SOLUTION";

  private int code = 0;
  private Exception e = new Exception();

  @Test
  public void testRemoteApiExceptionWithCodeAndMessage() {
    RemoteApiException exception = new RemoteApiException(code, MESSAGE);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    assertEquals(expectedCode, 0);
    assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithCodeAndException() {
    RemoteApiException exception = new RemoteApiException(code, e);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(NONE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(StringUtils.EMPTY)
        .build();

    assertEquals(expectedCode, 0);
    assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithExceptionAndMessage() {
    RemoteApiException exception = new RemoteApiException(code, MESSAGE, e);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(StringUtils.EMPTY)
        .build();

    assertEquals(expectedCode, 0);
    assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithCodeAndExceptionAndSolution() {
    RemoteApiException exception = new RemoteApiException(code, e, ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(NONE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(StringUtils.EMPTY)
        .build();

    assertEquals(expectedCode, 0);
    assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithExceptionAndMessageAndSolution() {
    RemoteApiException exception = new RemoteApiException(code, MESSAGE, e, SOLUTION);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(MESSAGE)
        .solutions(SOLUTION)
        .stackTrace(StringUtils.EMPTY)
        .build();

    assertEquals(expectedCode, 0);
    assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithMessageAndSolution() {
    RemoteApiException exception = new RemoteApiException(code, MESSAGE, SOLUTION);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(MESSAGE)
        .solutions(SOLUTION)
        .build();

    assertEquals(expectedCode, 0);
    assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithSolution() {
    RemoteApiException exception = new RemoteApiException(code, MESSAGE, ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION);
    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .build();

    assertEquals(expectedCode, 0);
    assertEquals(expectedMessage, resultMessage);
  }

  @Test
  public void testRemoteApiExceptionWithExceptionAndSolution() {
    RemoteApiException exception = new RemoteApiException(code, MESSAGE, e,
        ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION);

    int expectedCode = exception.getCode();
    String resultMessage = exception.getMessage();
    String expectedMessage = new ExpectedMessageBuilder()
        .component(COMMONS)
        .message(MESSAGE)
        .solutions(ExpectedMessageBuilder.EXPECTED_SOLUTION_NO_SOLUTION)
        .stackTrace(StringUtils.EMPTY)
        .build();

    assertEquals(expectedCode, 0);
    assertEquals(expectedMessage, resultMessage);
  }

}

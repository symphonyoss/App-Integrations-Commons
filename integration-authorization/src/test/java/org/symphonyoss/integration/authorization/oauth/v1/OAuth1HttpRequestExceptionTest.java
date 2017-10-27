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

package org.symphonyoss.integration.authorization.oauth.v1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.symphonyoss.integration.exception.ExpectedMessageBuilder;

/**
 * Unit tests for {@link OAuth1HttpRequestException}
 * Created by hamitay on 8/17/17.
 */

public class OAuth1HttpRequestExceptionTest {

  private static final String COMPONENT = "Third-party integration/app authorization.";

  private static final String MESSAGE = "message";

  private static final String NO_SOLUTION =
      "No solution has been cataloged for troubleshooting this problem.";

  private static final int CODE = 400;

  @Test
  public void testOAuth1HttpRequestException() {
    OAuth1HttpRequestException exception = new OAuth1HttpRequestException("message", 400);
    String resultMessage = exception.getMessage();
    String expectedMessage =
        new ExpectedMessageBuilder().component(COMPONENT)
            .message(MESSAGE)
            .solutions(NO_SOLUTION)
            .build();
    int resultCode = exception.getCode();

    assertEquals(expectedMessage, resultMessage);
    assertEquals(CODE, resultCode);
  }
}

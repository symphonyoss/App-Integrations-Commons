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

package org.symphonyoss.integration.webhook;

import com.symphony.api.auth.client.ApiException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.exception.authentication.ForbiddenAuthException;
import org.symphonyoss.integration.exception.authentication.UnauthorizedUserException;
import org.symphonyoss.integration.exception.authentication.UnexpectedAuthException;
import org.symphonyoss.integration.exception.bootstrap.RetryLifecycleException;

/**
 * Test class responsible to test the flows in the {@link WebHookExceptionHandler}.
 * Created by rsanchez on 02/08/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class WebHookExceptionHandlerTest {

  @InjectMocks
  private WebHookExceptionHandler exceptionHandler = new WebHookExceptionHandler();

  @Test(expected = UnexpectedAuthException.class)
  public void testHandleAuthException() {
    exceptionHandler.handleAuthException(new Exception());
  }

  @Test(expected = ForbiddenAuthException.class)
  public void testFailOnCreateForbiddenException() throws ApiException {
    ApiException exception = new ApiException(403, "");
    exceptionHandler.handleAuthenticationApiException("", exception);
  }

  @Test(expected = UnauthorizedUserException.class)
  public void testFailOnCreateUnauthorizedException() throws ApiException {
    ApiException exception = new ApiException(401, "");
    exceptionHandler.handleAuthenticationApiException("", exception);
  }

  @Test(expected = RetryLifecycleException.class)
  public void testFailOnCreateApiException() throws ApiException {
    ApiException exception = new ApiException(500, "");
    exceptionHandler.handleAuthenticationApiException("", exception);
  }

}

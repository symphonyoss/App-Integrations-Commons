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

import org.springframework.stereotype.Component;
import org.symphonyoss.integration.exception.ExceptionHandler;
import org.symphonyoss.integration.exception.RemoteApiException;
import org.symphonyoss.integration.exception.authentication.ForbiddenAuthException;
import org.symphonyoss.integration.exception.authentication.UnauthorizedUserException;
import org.symphonyoss.integration.exception.authentication.UnexpectedAuthException;
import org.symphonyoss.integration.exception.bootstrap.RetryLifecycleException;

/**
 * Created by rsanchez on 02/08/16.
 */
@Component
public class WebHookExceptionHandler extends ExceptionHandler {

  public void handleAuthenticationApiException(String user, RemoteApiException e) {
    int code = e.getCode();
    if (unauthorizedError(code)) {
      throw new UnauthorizedUserException("Certificate authentication is unauthorized for the requested user", e);
    } else if (forbiddenError(code)) {
      throw new ForbiddenAuthException("Certificate authentication is forbidden for the requested user", e);
    } else {
      throwRetryException(e);
    }
  }

  public void handleAuthException(Exception e) {
    throw new UnexpectedAuthException("Failed to process certificate login", e);
  }

  private void throwRetryException(Throwable cause) {
    throw new RetryLifecycleException("Unexpected error when authenticating", cause);
  }

}

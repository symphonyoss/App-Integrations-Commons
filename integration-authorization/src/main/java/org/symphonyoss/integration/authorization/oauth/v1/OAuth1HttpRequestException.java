/**
 * Copyright 2016-2017 Symphony Integrations - Symphony LLC
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.symphonyoss.integration.authorization.oauth.v1;

import org.symphonyoss.integration.exception.IntegrationException;

/**
 * Checked exception to report HTTP error response during the API call to an external system using
 * OAuth1 protocol.
 *
 * Created by alexandre-silva-daitan on 14/08/17.
 */
public class OAuth1HttpRequestException extends IntegrationException {

  private static final String COMPONENT = "Third-party integration";
  private int code;

  public OAuth1HttpRequestException(String message, int code) {
    super(COMPONENT, message);
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}


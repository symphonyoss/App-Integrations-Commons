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

package org.symphonyoss.integration.authentication;

import org.springframework.util.StringUtils;

/**
 * Keep the session token and key manager token.
 *
 * Created by rsanchez on 16/05/16.
 */
public class AuthenticationToken {

  public static final String VOID_SESSION_TOKEN = "void";

  public static final String VOID_KM_TOKEN = "void";

  public static final AuthenticationToken VOID_AUTH_TOKEN = new AuthenticationToken();

  private String sessionToken = VOID_SESSION_TOKEN;

  private String keyManagerToken = VOID_KM_TOKEN;

  private long authenticationTime;

  private AuthenticationToken() {
  }

  public AuthenticationToken(String sessionToken, String keyManagerToken) {
    if (!StringUtils.isEmpty(sessionToken) && !StringUtils.isEmpty(keyManagerToken)) {
      this.sessionToken = sessionToken;
      this.keyManagerToken = keyManagerToken;
      this.authenticationTime = System.currentTimeMillis();
    }
  }

  public String getSessionToken() {
    return sessionToken;
  }

  public String getKeyManagerToken() {
    return keyManagerToken;
  }

  public long getAuthenticationTime() {
    return authenticationTime;
  }

}

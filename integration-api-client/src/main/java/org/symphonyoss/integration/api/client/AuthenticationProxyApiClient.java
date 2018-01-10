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
 **/

package org.symphonyoss.integration.api.client;

import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.authentication.api.enums.ServiceName;

import java.util.Map;

import javax.ws.rs.client.Client;

/**
 * HTTP client class responsible to retrieve the HTTP client based on sessionToken header.
 * Created by rsanchez on 21/02/17.
 */
public class AuthenticationProxyApiClient extends SimpleHttpApiClient {

  private static final String SESSION_TOKEN_HEADER = "sessionToken";

  private static final String USER_SESSION_HEADER = "userSession";

  private AuthenticationProxy proxy;

  private ServiceName serviceName;

  public AuthenticationProxyApiClient(EntitySerializer serializer, AuthenticationProxy proxy,
      ServiceName serviceName) {
    super(serializer);
    this.proxy = proxy;
    this.serviceName = serviceName;
  }

  @Override
  public Client getClientForContext(Map<String, String> queryParams,
      Map<String, String> headerParams) {
    final String sessionToken = headerParams.get(SESSION_TOKEN_HEADER);

    if (sessionToken == null) {
      final String userId = headerParams.get(USER_SESSION_HEADER);
      return proxy.httpClientForUser(userId, serviceName);
    }

    return proxy.httpClientForSessionToken(sessionToken, serviceName);

  }

}

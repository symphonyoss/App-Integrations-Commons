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
import org.symphonyoss.integration.authentication.AuthenticationToken;
import org.symphonyoss.integration.exception.RemoteApiException;

import java.util.Map;

import javax.ws.rs.client.Client;

/**
 * Decorator class to reauthenticate the user when required.
 * Created by rsanchez on 21/02/17.
 */
public class ReAuthenticationApiClient extends HttpApiClientDecorator {

  private static final String SESSION_TOKEN_HEADER = "sessionToken";

  private AuthenticationProxy proxy;

  public ReAuthenticationApiClient(AuthenticationProxy proxy, HttpApiClient apiClient) {
    super(apiClient);
    this.proxy = proxy;
  }

  @Override
  public <T> T doGet(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Class<T> returnType) throws RemoteApiException {
    try {
      return apiClient.doGet(path, headerParams, queryParams, returnType);
    } catch (RemoteApiException e) {
      final String sessionToken = headerParams.get(SESSION_TOKEN_HEADER);

      if (sessionToken == null) {
        throw e;
      }

      try {
        AuthenticationToken token = proxy.reAuthSessionOrThrow(sessionToken, e);
        headerParams.put(SESSION_TOKEN_HEADER, token.getSessionToken());

        return apiClient.doGet(path, headerParams, queryParams, returnType);
      } catch (RemoteApiException e1) {
        throw e;
      }
    }
  }

  @Override
  public <T> T doPost(String path, Map<String, String> headerParams,
      Map<String, String> queryParams, Object payload, Class<T> returnType)
      throws RemoteApiException {
    try {
      return apiClient.doPost(path, headerParams, queryParams, payload, returnType);
    } catch (RemoteApiException e) {
      final String sessionToken = headerParams.get(SESSION_TOKEN_HEADER);

      if (sessionToken == null) {
        throw e;
      }

      try {
        AuthenticationToken token = proxy.reAuthSessionOrThrow(sessionToken, e);
        headerParams.put(SESSION_TOKEN_HEADER, token.getSessionToken());

        return apiClient.doPost(path, headerParams, queryParams, payload, returnType);
      } catch (RemoteApiException e1) {
        throw e;
      }
    }
  }

  @Override
  public <T> T doPut(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Object payload, Class<T> returnType) throws RemoteApiException {
    try {
      return apiClient.doPut(path, headerParams, queryParams, payload, returnType);
    } catch (RemoteApiException e) {
      final String sessionToken = headerParams.get(SESSION_TOKEN_HEADER);

      if (sessionToken == null) {
        throw e;
      }

      try {
        AuthenticationToken token = proxy.reAuthSessionOrThrow(sessionToken, e);
        headerParams.put(SESSION_TOKEN_HEADER, token.getSessionToken());

        return apiClient.doPut(path, headerParams, queryParams, payload, returnType);
      } catch (RemoteApiException e1) {
        throw e;
      }
    }
  }

  @Override
  public <T> T doDelete(String path, Map<String, String> headerParams,
      Map<String, String> queryParams, Class<T> returnType) throws RemoteApiException {
    try {
      return apiClient.doDelete(path, headerParams, queryParams, returnType);
    } catch (RemoteApiException e) {
      final String sessionToken = headerParams.get(SESSION_TOKEN_HEADER);

      if (sessionToken == null) {
        throw e;
      }

      try {
        AuthenticationToken token = proxy.reAuthSessionOrThrow(sessionToken, e);
        headerParams.put(SESSION_TOKEN_HEADER, token.getSessionToken());

        return apiClient.doDelete(path, headerParams, queryParams, returnType);
      } catch (RemoteApiException e1) {
        throw e;
      }
    }
  }

}

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

package org.symphonyoss.integration.api.client;

import org.symphonyoss.integration.exception.RemoteApiException;

import java.util.Map;

import javax.ws.rs.client.Client;

/**
 * Base decorator class to HTTP API client
 * Created by rsanchez on 21/02/17.
 */
public class HttpApiClientDecorator implements HttpApiClient {

  protected HttpApiClient apiClient;

  public HttpApiClientDecorator(HttpApiClient apiClient) {
    this.apiClient = apiClient;
  }

  @Override
  public String escapeString(String str) {
    return apiClient.escapeString(str);
  }

  @Override
  public <T> T doGet(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Class<T> returnType) throws RemoteApiException {
    return apiClient.doGet(path, headerParams, queryParams, returnType);
  }

  @Override
  public <T> T doPost(String path, Map<String, String> headerParams,
      Map<String, String> queryParams, Object payload, Class<T> returnType)
      throws RemoteApiException {
    return apiClient.doPost(path, headerParams, queryParams, payload, returnType);
  }

  @Override
  public <T> T doPut(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Object payload, Class<T> returnType) throws RemoteApiException {
    return apiClient.doPut(path, headerParams, queryParams, payload, returnType);
  }

  @Override
  public <T> T doDelete(String path, Map<String, String> headerParams,
      Map<String, String> queryParams, Class<T> returnType) throws RemoteApiException {
    return apiClient.doDelete(path, headerParams, queryParams, returnType);
  }

  @Override
  public Client getClientForContext(Map<String, String> queryParams,
      Map<String, String> headerParams) {
    return apiClient.getClientForContext(queryParams, headerParams);
  }
}

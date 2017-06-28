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
import org.symphonyoss.integration.exception.authentication.ConnectivityException;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.ProcessingException;

/**
 * Base decoratar class to deal with connectivity issues.
 * Created by rsanchez on 21/02/17.
 */
public class ConnectivityApiClientDecorator extends HttpApiClientDecorator {

  private String serviceName;

  private static final String COMPONENT = "Connectivity Api Client";

  public ConnectivityApiClientDecorator(String serviceName, HttpApiClient apiClient) {
    super(apiClient);
    this.serviceName = serviceName;
  }

  @Override
  public <T> T doGet(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Class<T> returnType) throws RemoteApiException {
    try {
      return apiClient.doGet(path, headerParams, queryParams, returnType);
    } catch (ProcessingException e) {
      throw getException(e);
    }
  }

  @Override
  public <T> T doPost(String path, Map<String, String> headerParams,
      Map<String, String> queryParams, Object payload, Class<T> returnType)
      throws RemoteApiException {
    try {
      return apiClient.doPost(path, headerParams, queryParams, payload, returnType);
    } catch (ProcessingException e) {
      throw getException(e);
    }
  }

  @Override
  public <T> T doPut(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Object payload, Class<T> returnType) throws RemoteApiException {
    try {
      return apiClient.doPut(path, headerParams, queryParams, payload, returnType);
    } catch (ProcessingException e) {
      throw getException(e);
    }
  }

  @Override
  public <T> T doDelete(String path, Map<String, String> headerParams,
      Map<String, String> queryParams, Class<T> returnType) throws RemoteApiException {
    try {
      return apiClient.doDelete(path, headerParams, queryParams, returnType);
    } catch (ProcessingException e) {
      throw getException(e);
    }
  }

  /**
   * Returns the exception should be thrown by the HTTP client. If the root cause is a
   * connectivity exception should be thrown a {@link ConnectivityException}. Otherwise, should
   * be thrown a {@link ProcessingException}.
   * @param e Root cause
   * @return Runtime exception
   */
  private RuntimeException getException(ProcessingException e) {
    if (IOException.class.isInstance(e.getCause())) {
      return getConnectivityException(e);
    } else {
      return e;
    }
  }

  /**
   * Retrieve a specific connectivity exception for each required service.
   * @param e Root cause
   * @return Specific connectivity exception
   */
  protected ConnectivityException getConnectivityException(ProcessingException e) {
    return new ConnectivityException(COMPONENT, serviceName, e);
  }

}

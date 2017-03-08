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

package org.symphonyoss.integration.api.client.metrics;

import com.codahale.metrics.Timer;
import org.symphonyoss.integration.api.client.HttpApiClient;
import org.symphonyoss.integration.api.client.HttpApiClientDecorator;
import org.symphonyoss.integration.exception.RemoteApiException;

import java.util.Map;

/**
 * Decorator HTTP client to calculate the metrics related to API calls.
 * Created by rsanchez on 21/02/17.
 */
public class MetricsHttpApiClient extends HttpApiClientDecorator {

  private ApiMetricsController metricsController;

  public MetricsHttpApiClient(ApiMetricsController controller, HttpApiClient apiClient) {
    super(apiClient);
    this.metricsController = controller;
  }

  @Override
  public <T> T doGet(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Class<T> returnType) throws RemoteApiException {
    Timer.Context context = null;
    boolean success = false;

    try {
      context = metricsController.startApiCall(path);

      T result = apiClient.doGet(path, headerParams, queryParams, returnType);
      success = true;

      return result;
    } finally {
      metricsController.finishApiCall(context, path, success);
    }
  }

  @Override
  public <T> T doPost(String path, Map<String, String> headerParams,
      Map<String, String> queryParams, Object payload, Class<T> returnType)
      throws RemoteApiException {
    Timer.Context context = null;
    boolean success = false;

    try {
      context = metricsController.startApiCall(path);

      T result = apiClient.doPost(path, headerParams, queryParams, payload, returnType);
      success = true;

      return result;
    } finally {
      metricsController.finishApiCall(context, path, success);
    }
  }

  @Override
  public <T> T doPut(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Object payload, Class<T> returnType) throws RemoteApiException {
    Timer.Context context = null;
    boolean success = false;

    try {
      context = metricsController.startApiCall(path);

      T result = apiClient.doPut(path, headerParams, queryParams, payload, returnType);
      success = true;

      return result;
    } finally {
      metricsController.finishApiCall(context, path, success);
    }
  }

  @Override
  public <T> T doDelete(String path, Map<String, String> headerParams,
      Map<String, String> queryParams, Class<T> returnType) throws RemoteApiException {
    Timer.Context context = null;
    boolean success = false;

    try {
      context = metricsController.startApiCall(path);

      T result = apiClient.doDelete(path, headerParams, queryParams, returnType);
      success = true;

      return result;
    } finally {
      metricsController.finishApiCall(context, path, success);
    }
  }

}

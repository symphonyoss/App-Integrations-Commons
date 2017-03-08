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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.symphonyoss.integration.exception.RemoteApiException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * HTTP API client to call JSON based APIs.
 * It's adjusted to the necessities present at Integrations Core use cases, providing extension points to add behavior.
 * Subclasses can override the protected methods: getClientForContext to include its own client building strategy,
 * depending on the API being called.
 *
 * Created by Milton Quilzini on 16/01/17.
 */
public class JsonHttpApiClient implements HttpApiClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(JsonHttpApiClient.class);

  /**
   * JSON helper class
   */
  private JsonUtils jsonUtils = new JsonUtils();

  /**
   * HTTP Base path
   */
  private String basePath;

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  @Override
  public String escapeString(String str) {
    try {
      return URLEncoder.encode(str, "utf8").replaceAll("\\+", "%20");
    } catch (UnsupportedEncodingException e) {
      LOGGER.debug(
          String.format("Couldn't escape string parameter %s due to encoding issues.", str), e);
      return str;
    }
  }

  @Override
  public <T> T doGet(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Class<T> returnType) throws RemoteApiException {
    Client client = getClientForContext(queryParams, headerParams);
    Invocation.Builder invocationBuilder =
        getInvocationBuilder(path, client, queryParams, headerParams);

    Response response = invocationBuilder.get();
    return handleResponse(returnType, response);
  }

  @Override
  public <T> T doPost(String path, Map<String, String> headerParams,
      Map<String, String> queryParams, Object payload, Class<T> returnType)
      throws RemoteApiException {
    Client client = getClientForContext(queryParams, headerParams);
    Invocation.Builder invocationBuilder =
        getInvocationBuilder(path, client, queryParams, headerParams);

    Response response = invocationBuilder.post(serialize(payload));
    return handleResponse(returnType, response);
  }

  @Override
  public <T> T doPut(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Object payload, Class<T> returnType) throws RemoteApiException {
    Client client = getClientForContext(queryParams, headerParams);
    Invocation.Builder invocationBuilder =
        getInvocationBuilder(path, client, queryParams, headerParams);

    Response response = invocationBuilder.put(serialize(payload));
    return handleResponse(returnType, response);
  }

  @Override
  public <T> T doDelete(String path, Map<String, String> headerParams,
      Map<String, String> queryParams, Class<T> returnType) throws RemoteApiException {
    Client client = getClientForContext(queryParams, headerParams);
    Invocation.Builder invocationBuilder =
        getInvocationBuilder(path, client, queryParams, headerParams);

    Response response = invocationBuilder.delete();
    return handleResponse(returnType, response);
  }

  @Override
  public Client getClientForContext(Map<String, String> queryParams, Map<String, String> headerParams) {
    return ClientBuilder.newClient();
  }

  /**
   * Retrieves the builder to perform the HTTP requests
   * @param path Resource path
   * @param client HTTP client
   * @param queryParams Query parameters
   * @param headerParams Header parameters
   * @return Invocation builder object
   */
  private Invocation.Builder getInvocationBuilder(String path, Client client,
      Map<String, String> queryParams, Map<String, String> headerParams) {
    WebTarget target = buildWebTarget(path, client, queryParams);
    Invocation.Builder invocationBuilder = target.request().accept(MediaType.APPLICATION_JSON);

    for (Map.Entry<String, String> entry : headerParams.entrySet()) {
      if (entry.getValue() != null) {
        invocationBuilder = invocationBuilder.header(entry.getKey(), entry.getValue());
      }
    }

    return invocationBuilder;
  }

  /**
   * Build the resource target.
   * @param path Resource path
   * @param client HTTP client
   * @param queryParams Query parameters
   * @return Resource target
   */
  private WebTarget buildWebTarget(String path, Client client, Map<String, String> queryParams) {
    WebTarget target = client.target(this.basePath).path(path);

    if (queryParams != null) {
      for (Map.Entry<String, String> queryParam : queryParams.entrySet()) {
        if (queryParam.getValue() != null) {
          target = target.queryParam(queryParam.getKey(), queryParam.getValue());
        }
      }
    }

    return target;
  }

  /**
   * Handles the HTTP response
   * @param returnType Expected return type
   * @param response HTTP response
   * @return HTTP response payload wrapped in the expected type or null if have no response
   * @throws RemoteApiException
   */
  private <T> T handleResponse(Class<T> returnType, Response response) throws RemoteApiException {
    if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
      return null;
    } else if (response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
      if (returnType == null) {
        return null;
      } else {
        return deserialize(response, returnType);
      }
    } else {
      String message = "Failed to call API";

      if (response.hasEntity()) {
        try {
          String respBody = String.valueOf(response.readEntity(String.class));
          message = respBody;
        } catch (RuntimeException e) {
          LOGGER.debug("Couldn't parse the response entity.", e);
        }
      }

      throw new RemoteApiException(response.getStatus(), message);
    }
  }

  /**
   * Serialize the given Java object into string entity JSON.
   */
  public Entity<String> serialize(Object obj) throws RemoteApiException {
    return Entity.json(jsonUtils.serialize(obj));
  }

  /**
   * Deserialize response JSON body to Java object referenced by returnType T.
   */
  public <T> T deserialize(Response response, Class<T> returnType) throws RemoteApiException {
    String body = response.hasEntity() ? response.readEntity(String.class) : StringUtils.EMPTY;
    return jsonUtils.deserialize(body, returnType);
  }

}

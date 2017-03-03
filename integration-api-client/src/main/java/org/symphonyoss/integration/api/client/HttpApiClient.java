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
 * Interface to be implemented by the low-level HTTP Api client.
 * Created by rsanchez on 20/02/17.
 */
public interface HttpApiClient {

  /**
   * Escapes the given string to be used as URL query value.
   * @param str he string to be escaped. Must be an UTF-8 string.
   * @return the escaped string, if possible.
   */
  String escapeString(String str);

  /**
   * Performs GET HTTP request.
   * @param path Resource path
   * @param headerParams Header parameters
   * @param queryParams Query parameters
   * @param returnType Expected type
   * @return Response payload
   */
  <T> T doGet(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Class<T> returnType) throws RemoteApiException;

  /**
   * Performs POST HTTP request.
   * @param path Resource path
   * @param headerParams Header parameters
   * @param queryParams Query parameters
   * @param payload Body payload
   * @param returnType Expected type
   * @return Response payload
   */
  <T> T doPost(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Object payload, Class<T> returnType) throws RemoteApiException;

  /**
   * Performs PUT HTTP request.
   * @param path Resource path
   * @param headerParams Header parameters
   * @param queryParams Query parameters
   * @param payload Body payload
   * @param returnType Expected type
   * @return Response payload
   */
  <T> T doPut(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Object payload, Class<T> returnType) throws RemoteApiException;

  /**
   * Performs DELETE HTTP request.
   * @param path Resource path
   * @param headerParams Header parameters
   * @param queryParams Query parameters
   * @param returnType Expected type
   * @return Response payload
   */
  <T> T doDelete(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Class<T> returnType) throws RemoteApiException;

  /**
   * Returns the proper client to call the API.<br/>
   * One can override this method to provide a client constructed accordingly to specific
   * application needs,
   * e.g. setting up a different "keystore" before each call.
   * @param queryParams the query parameters being used in the call.
   * @param headerParams the header parameters being used in the call.
   * @return return an adequately constructed client.
   */
  Client getClientForContext(Map<String, String> queryParams, Map<String, String> headerParams);

}

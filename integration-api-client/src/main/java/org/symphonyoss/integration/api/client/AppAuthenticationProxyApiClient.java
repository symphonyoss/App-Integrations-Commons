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

import org.symphonyoss.integration.authentication.api.AppAuthenticationProxy;

import java.util.Map;

import javax.ws.rs.client.Client;

/**
 * HTTP client class responsible for retrieving the HTTP client based on application ID header.
 *
 * Created by rsanchez on 21/02/17.
 */
public class AppAuthenticationProxyApiClient extends SimpleHttpApiClient {

  private static final String APP_ID_HEADER = "appId";

  private AppAuthenticationProxy proxy;

  private String serviceName;

  public AppAuthenticationProxyApiClient(EntitySerializer serializer,
      AppAuthenticationProxy proxy, String serviceName) {
    super(serializer);
    this.serviceName = serviceName;
    this.proxy = proxy;
  }

  @Override
  public Client getClientForContext(Map<String, String> queryParams,
      Map<String, String> headerParams) {
    final String appId = headerParams.get(APP_ID_HEADER);
    return proxy.httpClientForApplication(appId, serviceName);
  }

}

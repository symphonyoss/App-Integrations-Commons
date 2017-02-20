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

import org.symphonyoss.integration.exception.authentication.ConnectivityException;

import javax.ws.rs.ProcessingException;

/**
 * Mock class to test {@link ConnectivityApiClientDecorator}
 * Created by rsanchez on 23/02/17.
 */
public class MockConnectivityApiClientDecorator extends ConnectivityApiClientDecorator {

  public MockConnectivityApiClientDecorator(HttpApiClient apiClient) {
    super(apiClient);
  }

  @Override
  protected ConnectivityException getConnectivityException(ProcessingException e) {
    return new MockConnectivityException("Connectivity issue");
  }

  public static class MockConnectivityException extends ConnectivityException {

    public MockConnectivityException(String message) {
      super(message);
    }

  }

}

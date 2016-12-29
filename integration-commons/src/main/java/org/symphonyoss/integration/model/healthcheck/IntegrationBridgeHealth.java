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

package org.symphonyoss.integration.model.healthcheck;

import java.util.ArrayList;
import java.util.List;


/**
 * Health information about the Integration Bridge.
 * Holds its general status (and details about it), version, connectivity state with other services and detailed info
 * about every integration installed.
 *
 * Created by Milton Quilzini on 01/12/16.
 */
public class IntegrationBridgeHealth {

  private StatusEnum status;
  private String version;
  private String message;
  private IntegrationBridgeHealthConnectivity connectivity;
  private List<IntegrationHealth> applications = new ArrayList<>();

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public IntegrationBridgeHealthConnectivity getConnectivity() {
    return connectivity;
  }

  public void setConnectivity(IntegrationBridgeHealthConnectivity connectivity) {
    this.connectivity = connectivity;
  }

  public List<IntegrationHealth> getApplications() {
    return applications;
  }

  public void setApplications(List<IntegrationHealth> applications) {
    this.applications = applications;
  }

  public enum StatusEnum {
    UP,
    DOWN,
    INACTIVE;
  }
}

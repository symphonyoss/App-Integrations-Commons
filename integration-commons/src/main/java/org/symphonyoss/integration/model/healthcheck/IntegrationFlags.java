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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Part of an {@link IntegrationHealth}, it holds flags to indicate whether the specified integration is:
 * 1. Successfully provisioned.
 * 2. Its configurator is properly installed.
 * 3. Its certificate is properly installed.
 * 4. Its user is authenticated with Symphony.
 *
 * Created by Milton Quilzini on 01/12/16.
 */
public class IntegrationFlags {

  private ValueEnum parserInstalled;
  private ValueEnum userCertificateInstalled;
  private ValueEnum appCertificateInstalled;
  private ValueEnum userAuthenticated;

  @JsonProperty("parser_installed")
  public ValueEnum getParserInstalled() {
    return parserInstalled;
  }

  public void setParserInstalled(ValueEnum parserInstalled) {
    this.parserInstalled = parserInstalled;
  }

  @JsonProperty("user_certificate_installed")
  public ValueEnum getUserCertificateInstalled() {
    return userCertificateInstalled;
  }

  public void setUserCertificateInstalled(ValueEnum userCertificateInstalled) {
    this.userCertificateInstalled = userCertificateInstalled;
  }

  @JsonProperty("app_certificate_installed")
  public ValueEnum getAppCertificateInstalled() {
    return appCertificateInstalled;
  }

  public void setAppCertificateInstalled(ValueEnum appCertificateInstalled) {
    this.appCertificateInstalled = appCertificateInstalled;
  }

  @JsonProperty("user_authenticated")
  public ValueEnum getUserAuthenticated() {
    return userAuthenticated;
  }

  public void setUserAuthenticated(ValueEnum userAuthenticated) {
    this.userAuthenticated = userAuthenticated;
  }

  public enum ValueEnum {
    OK,
    NOK,
    NOT_APPLICABLE;
  }

  @JsonIgnore
  public boolean isUp() {
    return parserInstalled == ValueEnum.OK && userCertificateInstalled == ValueEnum.OK
        && userAuthenticated == ValueEnum.OK
        && (appCertificateInstalled == ValueEnum.OK
        || appCertificateInstalled == ValueEnum.NOT_APPLICABLE);
  }
}

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

package org.symphonyoss.integration.model.yaml;

/**
 * Represents the application keystore information.
 * Created by rsanchez on 27/12/16.
 */
public class Keystore {

  public static final String DEFAULT_KEYSTORE_TYPE = "pkcs12";

  public static final String DEFAULT_KEYSTORE_TYPE_SUFFIX = ".p12";

  public static final String DEFAULT_KEYSTORE_PASSWORD = "changeit";

  private String file;

  private String password;

  private String type;

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public String getPassword() {
    if (password == null) {
      return DEFAULT_KEYSTORE_PASSWORD;
    }

    return password.trim();
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getType() {
    if (type == null) {
      return DEFAULT_KEYSTORE_TYPE;
    }

    return type.trim();
  }

  public void setType(String type) {
    this.type = type;
  }
}

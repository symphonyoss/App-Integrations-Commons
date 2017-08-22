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

package org.symphonyoss.integration.authorization;

/**
 * Unique key to query the user authorization data.
 *
 * Created by rsanchez on 14/08/17.
 */
public class UserAuthorizationDataKey {

  private String configurationId;

  private String url;

  private Long userId;

  public UserAuthorizationDataKey(String configurationId, String url, Long userId) {
    this.configurationId = configurationId;
    this.url = url;
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }

    UserAuthorizationDataKey that = (UserAuthorizationDataKey) o;

    if (configurationId != null ? !configurationId.equals(that.configurationId)
        : that.configurationId != null) { return false; }
    if (url != null ? !url.equals(that.url) : that.url != null) { return false; }
    return userId != null ? userId.equals(that.userId) : that.userId == null;

  }

  @Override
  public int hashCode() {
    int result = configurationId != null ? configurationId.hashCode() : 0;
    result = 31 * result + (url != null ? url.hashCode() : 0);
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    return result;
  }
}

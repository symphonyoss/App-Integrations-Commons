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

import static org.symphonyoss.integration.model.yaml.ApplicationState.UNKNOWN;

import java.util.List;
import java.util.Set;

/**
 * Applications to be provisioned on the POD and Integration Bridge. Each app entry indicates the
 * deployment state of the application, its configuration on Symphony application store, and the
 * path for its RPM file.
 *
 * Created by rsanchez on 18/10/16.
 */
public class Application {

  private String id;

  private String component;

  private String name;

  private String description;

  private String context;

  private String publisher;

  private boolean enabled;

  private boolean visible;

  private ApplicationState state;

  private List<AllowedOrigin> allowedOrigins;

  private Keystore keystore;

  private String url;

  private String avatar;

  private String avatarUrl;

  private String username;

  private WhiteList whiteList = new WhiteList();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getComponent() {
    return component;
  }

  public void setComponent(String component) {
    this.component = component;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public ApplicationState getState() {
    if (state == null) {
      return UNKNOWN;
    }

    return state;
  }

  public void setState(ApplicationState state) {
    this.state = state;
  }

  public List<AllowedOrigin> getAllowedOrigins() {
    return allowedOrigins;
  }

  public void setAllowedOrigins(
      List<AllowedOrigin> allowedOrigins) {
    this.allowedOrigins = allowedOrigins;
  }

  public Keystore getKeystore() {
    return keystore;
  }

  public void setKeystore(Keystore keystore) {
    this.keystore = keystore;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Get the whitelist based on YAML file settings.
   * @return Application whitelist
   */
  public Set<String> getWhiteList() {
    return whiteList.getWhiteList(allowedOrigins);
  }
}

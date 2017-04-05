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

package org.symphonyoss.integration.model.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by rsanchez on 21/02/17.
 */
public class Message {

  public enum FormatEnum {
    TEXT("TEXT"),
    MESSAGEML("MESSAGEML");

    private String value;

    FormatEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

  private Long timestamp;

  private String message;

  private FormatEnum format;

  private String data;

  private MessageMLVersion version;

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public FormatEnum getFormat() {
    return format;
  }

  public void setFormat(FormatEnum format) {
    this.format = format;
  }

  @JsonIgnore
  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  @JsonIgnore
  public MessageMLVersion getVersion() {
    return version;
  }

  public void setVersion(MessageMLVersion version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "Message{" +
        "timestamp=" + timestamp +
        ", message='" + message + '\'' +
        ", format=" + format +
        ", data='" + data + '\'' +
        ", version=" + version +
        '}';
  }

}

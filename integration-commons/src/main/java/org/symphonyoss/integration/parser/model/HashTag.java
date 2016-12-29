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

package org.symphonyoss.integration.parser.model;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.symphonyoss.integration.messageml.MessageMLFormatConstants
    .MESSAGEML_HASHTAG_FORMAT;

import org.apache.commons.lang3.StringUtils;

/**
 * Abstracts a Symphony HashTag inside a message.
 *
 * Created by Milton Quilzini on 11/10/16.
 */
public class HashTag {
  private String hashtag;

  public HashTag(String hashtag) {
    if (isBlank(hashtag)) {
      this.hashtag = StringUtils.EMPTY;
    } else {
      this.hashtag = String.format(MESSAGEML_HASHTAG_FORMAT, hashtag);
    }
  }

  @Override
  public String toString() {
    return hashtag;
  }
}
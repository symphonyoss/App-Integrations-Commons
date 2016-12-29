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

package org.symphonyoss.integration.parser;

import static org.symphonyoss.integration.parser.ParserUtils.CR_LF_OR_LF;
import static org.symphonyoss.integration.parser.ParserUtils.MESSAGEML_LINEBREAK;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Safe strings should be used when creating presentationML content containing information received
 * from a webhook. All markups received from a webhook should be escaped in order not to be
 * interpreted by the browser. Allowing markups from 3rd parties would be a security breach.
 *
 * Created by ecarrenho on 9/29/16.
 */
public final class SafeString {

  /**
   * An empty safe string
   */
  public static final SafeString EMPTY_SAFE_STRING = new SafeString();

  private String string = new String();

  /**
   * This constructor can only be used by the safe string itself, as the safe string content
   * can not be set from the outside.
   */
  private SafeString() {
  }

  /**
   * Creates a safe string with the provided content. All markups will be XML escaped.
   * @param string Content to be escaped and set as a safe string.
   */
  public SafeString(String string) {
    this.string = StringEscapeUtils.escapeXml10(string);
  }

  /**
   * Replaces "\n" characters by "<br/>" markups. The markups will not be escaped, as they have
   * been safely inserted by safe string itself.
   */
  public void replaceLineBreaks() {
    string = string.replaceAll(CR_LF_OR_LF, MESSAGEML_LINEBREAK);
  }

  /**
   * Replaces a substring with a new substring.
   * @param oldSring The substring to be replaced.
   * @param newSting The new value for the substring.
   */
  public void safeReplace(SafeString oldSring, SafeString newSting) {
    string = string.replace(oldSring.string, newSting.string);
  }

  /**
   * Returns the safe string content as a standard string.
   * @return Safe string content as standard string.
   */
  public String toString() {
    return string;
  }

  /**
   * Utility method to create a safe string.
   * @param string The safe string content.
   * @return A new SafeString instance.
   */
  static SafeString newSafeString(String string) {
    SafeString newSafeString = new SafeString();
    newSafeString.string = string;
    return newSafeString;
  }
}

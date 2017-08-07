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

package org.symphonyoss.integration.authorization.oauth.v1;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.authorization.AuthorizationPayload;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit tests for {@link AuthorizationPayload}.
 * Created by campidelli on 01-aug-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthorizationPayloadTest {

  private Map<String, String> parameters = new HashMap<>();
  private Map<String, String> headers = new HashMap<>();

  @Test
  public void testConstructor() {
    AuthorizationPayload ap = new AuthorizationPayload(parameters, headers, StringUtils.EMPTY);
    assertEquals(parameters, ap.getParameters());
    assertEquals(headers, ap.getHeaders());
    assertEquals(StringUtils.EMPTY, ap.getBody());
  }
}

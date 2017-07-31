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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link UserAuthorizationData}
 * Created by rsanchez on 31/07/17.
 */
public class UserAuthorizationDataTest {

  private static final Long MOCK_USER_ID = 123456L;

  private static final String MOCK_URL = "test.symphony.com";

  @Test
  public void testModel() {
    UserAuthorizationData data1 = new UserAuthorizationData();
    data1.setUserId(MOCK_USER_ID);
    data1.setUrl(MOCK_URL);

    UserAuthorizationData data2 = new UserAuthorizationData(MOCK_USER_ID, MOCK_URL);

    assertEquals(data1, data2);
    assertEquals(data1.hashCode(), data2.hashCode());
  }

}

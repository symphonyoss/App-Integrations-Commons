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
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for {@link UserAuthorizationDataKey}
 * Created by hamitay on 8/17/17.
 */

public class UserAuthorizationDataKeyTest {

  private static final String CONFIGURATION_ID = "configurationId";

  private static final String URL = "url";

  private static final Long USER_ID = new Long(12345);

  @Test
  public void testEquals() {
    UserAuthorizationDataKey dataKey1 =
        new UserAuthorizationDataKey(CONFIGURATION_ID, URL, USER_ID);
    UserAuthorizationDataKey dataKey2 =
        new UserAuthorizationDataKey(CONFIGURATION_ID, URL, USER_ID);

    assertTrue(dataKey1.equals(dataKey1));
    assertTrue(dataKey1.equals(dataKey2));
  }

  @Test
  public void testHashcode() {
    UserAuthorizationDataKey dataKey =
        new UserAuthorizationDataKey(CONFIGURATION_ID, URL, USER_ID);

    int expectedHashCode = CONFIGURATION_ID.hashCode();
    expectedHashCode = 31 * expectedHashCode + (URL.hashCode());
    expectedHashCode = 31 * expectedHashCode + (USER_ID.hashCode());

    assertEquals(expectedHashCode, dataKey.hashCode());
  }
}

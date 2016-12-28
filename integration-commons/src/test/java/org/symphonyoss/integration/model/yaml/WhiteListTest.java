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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Test class to validate {@link WhiteList}
 * Created by rsanchez on 18/11/16.
 */
public class WhiteListTest {

  @Test
  public void testEmptyWhiteList() {
    WhiteList whiteList = new WhiteList();

    Set<String> result = whiteList.getWhiteList(null);

    assertNotNull(result);
    assertTrue(result.isEmpty());

    whiteList = new WhiteList();

    Set<String> emptyWhiteList = whiteList.getWhiteList(Collections.EMPTY_LIST);

    assertNotNull(emptyWhiteList);
    assertTrue(emptyWhiteList.isEmpty());
  }

  @Test
  public void testWhiteList() {
    List<AllowedOrigin> originList = new ArrayList<>();

    AllowedOrigin origin1 = new AllowedOrigin();
    origin1.setHost("squid-104-1.sc1.uc-inf.net");
    origin1.setAddress("165.254.226.119");

    AllowedOrigin origin2 = new AllowedOrigin();
    origin2.setAddress("107.23.104.0/31");

    originList.add(origin1);
    originList.add(origin2);

    WhiteList whiteList = new WhiteList();

    Set<String> integrationWhiteList = whiteList.getWhiteList(originList);
    assertNotNull(integrationWhiteList);
    assertEquals(4, integrationWhiteList.size());

    assertTrue(integrationWhiteList.contains("squid-104-1.sc1.uc-inf.net"));
    assertTrue(integrationWhiteList.contains("165.254.226.119"));
    assertTrue(integrationWhiteList.contains("107.23.104.0"));
    assertTrue(integrationWhiteList.contains("107.23.104.1"));
  }

}

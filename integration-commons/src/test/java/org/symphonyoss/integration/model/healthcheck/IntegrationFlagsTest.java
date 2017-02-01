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

package org.symphonyoss.integration.model.healthcheck;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for {@link IntegrationFlags}
 * Created by rsanchez on 01/02/17.
 */
public class IntegrationFlagsTest {

  @Test
  public void testFlags() {
    IntegrationFlags flags = new IntegrationFlags();
    assertFalse(flags.isUp());

    flags.setParserInstalled(IntegrationFlags.ValueEnum.NOK);
    flags.setConfiguratorInstalled(IntegrationFlags.ValueEnum.NOK);
    flags.setCertificateInstalled(IntegrationFlags.ValueEnum.NOK);
    flags.setUserAuthenticated(IntegrationFlags.ValueEnum.NOK);
    assertFalse(flags.isUp());

    flags.setParserInstalled(IntegrationFlags.ValueEnum.OK);
    assertFalse(flags.isUp());

    flags.setConfiguratorInstalled(IntegrationFlags.ValueEnum.OK);
    assertFalse(flags.isUp());

    flags.setCertificateInstalled(IntegrationFlags.ValueEnum.OK);
    assertFalse(flags.isUp());

    flags.setUserAuthenticated(IntegrationFlags.ValueEnum.OK);
    assertTrue(flags.isUp());
  }

}

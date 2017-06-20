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

package config;

import org.junit.Assert;
import org.junit.Test;
import org.symphonyoss.integration.model.config.IntegrationInstance;

/**
 * Unit test for {@link IntegrationInstanceTest}
 * Created by crepache on 20/06/17.
 */
public class IntegrationInstanceTest {

  private static final String MOCK_INSTANCE_ID = "57e2f006e4b0176038a81b18";
  private static final String MOCK_CONFIG_ID = "57d6f328e4b0396198ce723d";
  private static final String MOCK_INSTANCE_NAME = "mockInstanceName";
  private static final String MOCKE_CREATOR_ID = "mockeCreatorId";
  private static final Long CREATED_DATE = new Long(1462371116436L);
  private static final String MOCK_CREATOR_NAME = "mockCreatorName";
  private static final Long LAST_MODIFIED_DATE = new Long(1462371116436L);
  private static final String OPTIONAL_PROPERTIES = "{\"streams\":[\"t7uufOOl8JXeDcamEVLvSn___qvMMOjEdA\",\"JHbxCfFqwResXmyVn3VGr3___qvU3O\"]}";

  @Test
  public void testCreateIntegrationInstance() {
    IntegrationInstance integrationInstance = new IntegrationInstance();

    integrationInstance.setInstanceId(MOCK_INSTANCE_ID);
    integrationInstance.setConfigurationId(MOCK_CONFIG_ID);
    integrationInstance.setName(MOCK_INSTANCE_NAME);
    integrationInstance.setCreatorId(MOCKE_CREATOR_ID);
    integrationInstance.setCreatedDate(CREATED_DATE);
    integrationInstance.setCreatorName(MOCK_CREATOR_NAME);
    integrationInstance.setLastModifiedDate(LAST_MODIFIED_DATE);
    integrationInstance.setOptionalProperties(OPTIONAL_PROPERTIES);
    integrationInstance.setActive(Boolean.TRUE);

    Assert.assertEquals(MOCK_INSTANCE_ID, integrationInstance.getInstanceId());
    Assert.assertEquals(MOCK_CONFIG_ID, integrationInstance.getConfigurationId());
    Assert.assertEquals(MOCK_INSTANCE_NAME, integrationInstance.getName());
    Assert.assertEquals(MOCKE_CREATOR_ID, integrationInstance.getCreatorId());
    Assert.assertEquals(CREATED_DATE, integrationInstance.getCreatedDate());
    Assert.assertEquals(MOCK_CREATOR_NAME, integrationInstance.getCreatorName());
    Assert.assertEquals(LAST_MODIFIED_DATE, integrationInstance.getLastModifiedDate());
    Assert.assertEquals(OPTIONAL_PROPERTIES, integrationInstance.getOptionalProperties());
    Assert.assertTrue(integrationInstance.getActive());
  }

}

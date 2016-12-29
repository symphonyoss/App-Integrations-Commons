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

package org.symphonyoss.integration.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for {@link Entity}
 * Created by cmarcondes on 11/17/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class EntityTest {

  @Test
  public void testGetEntityByTypeNull() {
    Entity entity = EntityBuilder.forNestedEntity("integration", "jira").build();
    Entity nestedEntity = entity.getEntityByType(null);
    Assert.assertNull(nestedEntity);
  }

  @Test
  public void testGetEntityByTypeEmpty() {
    Entity entity = EntityBuilder.forNestedEntity("integration", "jira").build();
    Entity nestedEntity = entity.getEntityByType("");
    Assert.assertNull(nestedEntity);
  }

  @Test
  public void testGetEntityByTypeWithoutNestedEntity() {
    Entity entity = EntityBuilder.forNestedEntity("integration", "jira").build();
    Entity nestedEntity = entity.getEntityByType("user");
    Assert.assertNull(nestedEntity);
  }

  @Test
  public void testGetEntityByType() {
    Entity user = EntityBuilder.forNestedEntity("integration", "name", "user")
        .attribute("name", "caue").build();

    Entity mainEntity =
        EntityBuilder.forNestedEntity("integration", "name", "user").nestedEntity(user).build();

    Entity nestedEntity = mainEntity.getEntityByType("user");
    Assert.assertEquals("com.symphony.integration.integration.user", nestedEntity.getType());
  }

  @Test
  public void testGetEntityByNameNull() {
    Entity entity = EntityBuilder.forNestedEntity("integration", "name", "user").build();
    Entity nestedEntity = entity.getEntityByName(null);
    Assert.assertNull(nestedEntity);
  }

  @Test
  public void testGetEntityByNameEmpty() {
    Entity entity = EntityBuilder.forNestedEntity("integration", "name", "user").build();
    Entity nestedEntity = entity.getEntityByName("");
    Assert.assertNull(nestedEntity);
  }

  @Test
  public void testGetEntityByNameWithoutNestedEntity() {
    Entity entity = EntityBuilder.forNestedEntity("integration", "name", "user").build();
    Entity nestedEntity = entity.getEntityByName("user");
    Assert.assertNull(nestedEntity);
  }

  @Test
  public void testGetEntityByName() {
    Entity user = EntityBuilder.forNestedEntity("integration", "name", "user")
        .attribute("name", "caue").build();

    Entity mainEntity =
        EntityBuilder.forNestedEntity("integration", "name", "user").nestedEntity(user).build();

    Entity nestedEntity = mainEntity.getEntityByName("name");
    Assert.assertEquals("com.symphony.integration.integration.user", nestedEntity.getType());
  }

  @Test
  public void testGetAttributeNull() {
    Entity entity = EntityBuilder.forNestedEntity("integration", "user").build();
    String attributeValue = entity.getAttributeValue(null);
    Assert.assertNull(attributeValue);
  }

  @Test
  public void testGetAttributeEmpty() {
    Entity entity = EntityBuilder.forNestedEntity("integration", "user").build();
    String attributeValue = entity.getAttributeValue("");
    Assert.assertNull(attributeValue);
  }

  @Test
  public void testGetAttributeWithoutAttribute() {
    Entity entity = EntityBuilder.forNestedEntity("integration", "user").build();
    String attributeValue = entity.getAttributeValue("attr");
    Assert.assertNull(attributeValue);
  }

  @Test
  public void testGetAttribute() {
    Entity entity =
        EntityBuilder.forNestedEntity("integration", "user").attribute("attr", "myAttr").build();
    String attributeValue = entity.getAttributeValue("attr");
    Assert.assertEquals("myAttr", attributeValue);
  }

}

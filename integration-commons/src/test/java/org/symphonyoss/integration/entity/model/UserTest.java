package org.symphonyoss.integration.entity.model;

import org.junit.Assert;
import org.junit.Test;
import org.symphonyoss.integration.entity.Entity;

/**
 * Unit tests for {@link User}.
 * Created by crepache on 21/06/17.
 */
public class UserTest {

  private static final Long MOCK_ID = 1234L;

  private static final String MOCK_USER_NAME = "crepachesymphonytest";

  private static final String MOCK_DISPLAY_NAME = "Cassiano";

  private static final String MOCK_EMAIL = "crepache@symphony.com";

  private static final String INTEGRATION_NAME = "integrationName";

  private static final String ENTITY_NAME = "entityName";

  private User mockUser() {
    User user = new User();
    user.setId(MOCK_ID);
    user.setUserName(MOCK_USER_NAME);
    user.setDisplayName(MOCK_DISPLAY_NAME);
    user.setEmailAddress(MOCK_EMAIL);

    return user;
  }

  @Test
  public void testUser() {
    User user = mockUser();

    Assert.assertEquals(MOCK_ID, user.getId());
    Assert.assertEquals(MOCK_USER_NAME, user.getUsername());
    Assert.assertEquals(MOCK_DISPLAY_NAME, user.getDisplayName());
    Assert.assertEquals(MOCK_EMAIL, user.getEmailAddress());
  }

  @Test
  public void testMentionEntityUser() {
    User user = mockUser();

    Entity entityUser = user.getMentionEntity(INTEGRATION_NAME);
    Assert.assertEquals(user.getId(), new Long(entityUser.getAttributeValue(EntityConstants.USER_ID)));
    Assert.assertEquals(user.getDisplayName(), entityUser.getAttributeValue(EntityConstants.NAME_ENTITY_FIELD));
  }

  @Test
  public void testMentionEntityWhenUserIdNull() {
    User user = mockUser();
    user.setId(null);

    Assert.assertNull(user.getMentionEntity(INTEGRATION_NAME));
  }

  @Test
  public void testToEntity() {
    User user = mockUser();

    Entity entity = user.toEntity(INTEGRATION_NAME, ENTITY_NAME);

    Assert.assertEquals("entityName", entity.getName());

    Assert.assertEquals(user.getId(), new Long(entity.getEntities().get(0).getAttributeValue(EntityConstants.USER_ID)));
    Assert.assertEquals(user.getDisplayName(), entity.getEntities().get(0).getAttributeValue(EntityConstants.NAME_ENTITY_FIELD));

    Assert.assertEquals(user.getUsername(), entity.getAttributeValue(EntityConstants.USERNAME_ENTITY_FIELD));
    Assert.assertEquals(user.getEmailAddress(), entity.getAttributeValue(EntityConstants.EMAIL_ADDRESS_ENTITY_FIELD));
    Assert.assertEquals(user.getDisplayName(), entity.getAttributeValue(EntityConstants.DISPLAY_NAME_ENTITY_FIELD));
  }

  @Test
  public void testToEntityWhenUserIdNull() {
    User user = mockUser();
    user.setId(null);

    Entity entity = user.toEntity(INTEGRATION_NAME, ENTITY_NAME);

    Assert.assertEquals(user.getUsername(), entity.getAttributeValue(EntityConstants.USERNAME_ENTITY_FIELD));
    Assert.assertEquals(user.getEmailAddress(), entity.getAttributeValue(EntityConstants.EMAIL_ADDRESS_ENTITY_FIELD));
    Assert.assertEquals(user.getDisplayName(), entity.getAttributeValue(EntityConstants.DISPLAY_NAME_ENTITY_FIELD));
    Assert.assertEquals(0, entity.getEntities().size());
  }

}
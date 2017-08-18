package org.symphonyoss.integration.authentication.api.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.UUID;

/**
 * Unit tests for {@link JwtUser}
 *
 * Created by campidelli on 18/08/17.
 */
public class JwtUserTest {

  @Test
  public void testModel() {
    JwtUser user = new JwtUser();
    user.setId("id");
    user.setEmailAddress("emailAddress");
    user.setUsername("username");
    user.setFirstName("firstName");
    user.setLastName("lastName");
    user.setDisplayName("displayName");
    user.setTitle("title");
    user.setCompany("company");
    user.setCompanyId("companyId");
    user.setLocation("location");
    user.setAvatarUrl("avatarUrl");
    user.setAvatarSmallUrl("avatarSmallUrl");

    JwtUser user2 = new JwtUser();
    user2.setId(user.getId());
    user2.setEmailAddress(user.getEmailAddress());
    user2.setUsername(user.getUsername());
    user2.setFirstName(user.getFirstName());
    user2.setLastName(user.getLastName());
    user2.setDisplayName(user.getDisplayName());
    user2.setTitle(user.getTitle());
    user2.setCompany(user.getCompany());
    user2.setCompanyId(user.getCompanyId());
    user2.setLocation(user.getLocation());
    user2.setAvatarUrl(user.getAvatarUrl());
    user2.setAvatarSmallUrl(user.getAvatarSmallUrl());

    assertEquals(user, user2);
  }

}

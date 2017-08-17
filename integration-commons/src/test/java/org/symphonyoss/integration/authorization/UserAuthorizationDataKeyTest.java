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

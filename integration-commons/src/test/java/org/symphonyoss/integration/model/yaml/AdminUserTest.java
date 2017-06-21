package org.symphonyoss.integration.model.yaml;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link AdminUser}
 * Created by crepache on 21/06/17.
 */
public class AdminUserTest {

  private static final String KEYSTORE_FILE = "keystoreFile";

  private static final String KEYSTORE_PASSWORD = "keystorePassword";

  @Test
  public void testAdminUser() {
    AdminUser adminUser = new AdminUser();
    adminUser.setKeystoreFile(KEYSTORE_FILE);
    adminUser.setKeystorePassword(KEYSTORE_PASSWORD);

    Assert.assertEquals(KEYSTORE_FILE, adminUser.getKeystoreFile());
    Assert.assertEquals(KEYSTORE_PASSWORD, adminUser.getKeystorePassword());
  }

}

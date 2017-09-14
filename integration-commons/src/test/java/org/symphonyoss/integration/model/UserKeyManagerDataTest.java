package org.symphonyoss.integration.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test of UserKeyManagerData class.
 * Created by campidelli on 9/14/17.
 */
public class UserKeyManagerDataTest {

  private static final String STATUS = "status";
  private static final Long USER_ID = 123L;
  private static final String PUBLIC_KEY = "publicKey";
  private static final String PRIVATE_KEY = "privateKey";
  private static final String CERTIFICATE = "certificate";
  private static final String PUBLIC_KEY_SIGNATURE = "publicKeySignature";
  private static final String PRIVATE_KEY_SIGNATURE = "privateKeySignature";

  @Test
  public void testUserKeyManagerData() {
    UserKeyManagerData userKeyManagerData = new UserKeyManagerData();

    userKeyManagerData.setStatus(STATUS);
    userKeyManagerData.setUserId(USER_ID);
    userKeyManagerData.setPublicKey(PUBLIC_KEY);
    userKeyManagerData.setPrivateKey(PRIVATE_KEY);
    userKeyManagerData.setCertificate(CERTIFICATE);
    userKeyManagerData.setPublicKeySignature(PUBLIC_KEY_SIGNATURE);
    userKeyManagerData.setPrivateKeySignature(PRIVATE_KEY_SIGNATURE);

    Assert.assertEquals(STATUS, userKeyManagerData.getStatus());
    Assert.assertEquals(USER_ID, userKeyManagerData.getUserId());
    Assert.assertEquals(PUBLIC_KEY, userKeyManagerData.getPublicKey());
    Assert.assertEquals(PRIVATE_KEY, userKeyManagerData.getPrivateKey());
    Assert.assertEquals(CERTIFICATE, userKeyManagerData.getCertificate());
    Assert.assertEquals(PUBLIC_KEY_SIGNATURE, userKeyManagerData.getPublicKeySignature());
    Assert.assertEquals(PRIVATE_KEY_SIGNATURE, userKeyManagerData.getPrivateKeySignature());
  }
}

package org.symphonyoss.integration.authentication;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test class to validate {@link AuthenticationToken}
 * Created by crepache on 21/06/17.
 */
public class AuthenticationTokenTest {

  private static final String SESSION_TOKEN = "S-TOKEN";

  private static final String KM_TOKEN = "KM-TOKEN";

  private static final String VOID_SESSION_TOKEN = "void";

  public static final String VOID_KM_TOKEN = "void";

  @Test
  public void testAuthenticationToken() {
    AuthenticationToken authenticationToken = new AuthenticationToken(SESSION_TOKEN, KM_TOKEN);

    Assert.assertEquals(SESSION_TOKEN, authenticationToken.getSessionToken());
    Assert.assertEquals(KM_TOKEN, authenticationToken.getKeyManagerToken());
    Assert.assertNotNull(authenticationToken.getAuthenticationTime());
  }

  @Test
  public void testAuthenticationTokenDefault() {
    AuthenticationToken authenticationToken = AuthenticationToken.VOID_AUTH_TOKEN;

    Assert.assertEquals(VOID_SESSION_TOKEN, authenticationToken.getSessionToken());
    Assert.assertEquals(VOID_KM_TOKEN, authenticationToken.getKeyManagerToken());
  }
}

package org.symphonyoss.integration.authentication.api.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.UUID;

/**
 * Unit tests for {@link AppToken}
 *
 * Created by rsanchez on 09/08/17.
 */
public class AppTokenTest {

  private static final String APP_ID = "testApp";

  @Test
  public void testModel() {
    String appToken = UUID.randomUUID().toString();
    String symphonyToken = UUID.randomUUID().toString();

    AppToken token = new AppToken();
    token.setAppId(APP_ID);
    token.setAppToken(appToken);
    token.setSymphonyToken(symphonyToken);

    assertEquals(APP_ID, token.getAppId());
    assertEquals(appToken, token.getAppToken());
    assertEquals(symphonyToken, token.getSymphonyToken());
  }

}

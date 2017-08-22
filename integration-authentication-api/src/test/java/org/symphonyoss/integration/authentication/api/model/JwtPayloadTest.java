package org.symphonyoss.integration.authentication.api.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Unit tests for {@link JwtPayload}
 *
 * Created by campidelli on 18/08/17.
 */
public class JwtPayloadTest {

  @Test
  public void testModel() {
    JwtPayload payload = new JwtPayload();
    payload.setApplicationId("appId");
    payload.setCompanyName("companyName");
    payload.setUserId("12345");
    payload.setExpirationDateInSeconds(new Date().getTime() / 1000);
    payload.setUser(new JwtUser());

    JwtPayload payload2 = new JwtPayload(payload.getApplicationId(), payload.getCompanyName(),
        payload.getUserId(), payload.getExpirationDateInSeconds(), payload.getUser());

    assertEquals(payload, payload2);
  }

}

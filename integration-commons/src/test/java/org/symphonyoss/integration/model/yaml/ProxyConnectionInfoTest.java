package org.symphonyoss.integration.model.yaml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for {@link ProxyConnectionInfo}
 * Created by hamitay on 15/11/17.
 */
public class ProxyConnectionInfoTest {

  public static String USER = "user";
  public static String PASSWORD = "password";
  public static String URI = "URI";
  public static String EXPECTED_PROXY_CONNECTION_INFO =
      "ProxyConnectionInfo{uri='URI', user=user', password=password'}";

  @Test
  public void testProxyConnectionInfo() {
    ProxyConnectionInfo proxy = new ProxyConnectionInfo();
    proxy.setUser(USER);
    proxy.setPassword(PASSWORD);
    proxy.setURI(URI);

    assertEquals(USER, proxy.getUser());
    assertEquals(PASSWORD, proxy.getPassword());
    assertEquals(URI, proxy.getURI());
    assertEquals(EXPECTED_PROXY_CONNECTION_INFO, proxy.toString());
  }
}

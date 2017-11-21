package org.symphonyoss.integration.model.yaml;

import org.junit.Assert;
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

    Assert.assertEquals(USER, proxy.getUser());
    Assert.assertEquals(PASSWORD, proxy.getPassword());
    Assert.assertEquals(URI, proxy.getURI());
    Assert.assertEquals(EXPECTED_PROXY_CONNECTION_INFO, proxy.toString());
  }
}

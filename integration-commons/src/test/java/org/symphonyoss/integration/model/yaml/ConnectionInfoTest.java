package org.symphonyoss.integration.model.yaml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for {@link ConnectionInfo}
 * Created by crepache on 21/06/17.
 */
public class ConnectionInfoTest {

  public static final String PORT = "8080";
  public static final String MIN_VERSION = "0.47.0";
  public static final String HOST = "127.0.0.1";
  public static final String EXPECTED_CONNECTION_INFO =
      "ConnectionInfo{host='127.0.0.1', port='8080', proxy='ProxyConnectionInfo{uri='127.0.0.1', "
          + "user=user', password=password'}}";
  public static final String PROXY_USER = "user";
  public static final String PROXY_PASSWORD = "password";

  @Test
  public void testConnectionInfo() {
    ConnectionInfo connectionInfo = new ConnectionInfo();
    connectionInfo.setHost(HOST);
    connectionInfo.setMinVersion(MIN_VERSION);
    connectionInfo.setPort(PORT);

    ProxyConnectionInfo expectedProxy = new ProxyConnectionInfo();
    expectedProxy.setUser(PROXY_USER);
    expectedProxy.setPassword(PROXY_PASSWORD);
    expectedProxy.setURI(HOST);

    connectionInfo.setProxy(expectedProxy);

    assertEquals(HOST, connectionInfo.getHost());
    assertEquals(MIN_VERSION, connectionInfo.getMinVersion());
    assertEquals(PORT, connectionInfo.getPort());
    assertEquals(EXPECTED_CONNECTION_INFO, connectionInfo.toString());
    assertEquals(expectedProxy.getUser(), connectionInfo.getProxy().getUser());
    assertEquals(expectedProxy.getPassword(), connectionInfo.getProxy().getPassword());
    assertEquals(expectedProxy.getURI(), connectionInfo.getProxy().getURI());
  }
}

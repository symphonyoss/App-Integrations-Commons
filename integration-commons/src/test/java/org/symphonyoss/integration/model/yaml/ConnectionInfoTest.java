package org.symphonyoss.integration.model.yaml;

import org.junit.Assert;
import org.junit.Test;

import java.net.URI;

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
    connectionInfo.setProxyPassword(PROXY_PASSWORD);
    connectionInfo.setProxyUser(PROXY_USER);
    connectionInfo.setProxyUri(HOST);

    ProxyConnectionInfo expectedProxy = new ProxyConnectionInfo();
    expectedProxy.setUser(PROXY_USER);
    expectedProxy.setPassword(PROXY_PASSWORD);
    expectedProxy.setURI(HOST);

    Assert.assertEquals(HOST, connectionInfo.getHost());
    Assert.assertEquals(MIN_VERSION, connectionInfo.getMinVersion());
    Assert.assertEquals(PORT, connectionInfo.getPort());
    Assert.assertEquals(EXPECTED_CONNECTION_INFO, connectionInfo.toString());
    Assert.assertEquals(expectedProxy.getUser(), connectionInfo.getProxy().getUser());
    Assert.assertEquals(expectedProxy.getPassword(), connectionInfo.getProxy().getPassword());
    Assert.assertEquals(expectedProxy.getURI(), connectionInfo.getProxy().getURI());
  }
}

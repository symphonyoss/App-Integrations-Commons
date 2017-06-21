package org.symphonyoss.integration.model.yaml;

import org.junit.Assert;
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
      "ConnectionInfo{host='127.0.0.1', port='8080'}";

  @Test
  public void testConnectionInfo() {
    ConnectionInfo connectionInfo = new ConnectionInfo();
    connectionInfo.setHost(HOST);
    connectionInfo.setMinVersion(MIN_VERSION);
    connectionInfo.setPort(PORT);

    Assert.assertEquals(HOST, connectionInfo.getHost());
    Assert.assertEquals(MIN_VERSION, connectionInfo.getMinVersion());
    Assert.assertEquals(PORT, connectionInfo.getPort());

    Assert.assertEquals(EXPECTED_CONNECTION_INFO, connectionInfo.toString());
  }
}

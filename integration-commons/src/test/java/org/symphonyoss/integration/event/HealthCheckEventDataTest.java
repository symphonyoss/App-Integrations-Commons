package org.symphonyoss.integration.event;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link HealthCheckEventData}
 * Created by crepache on 21/06/17.
 */
public class HealthCheckEventDataTest {

  public static final String SERVICE_NAME = "ServiceName";

  @Test
  public void testHealthCheckEventData() {
    HealthCheckEventData healthCheckEventData = new HealthCheckEventData(SERVICE_NAME);

    Assert.assertEquals(SERVICE_NAME, healthCheckEventData.getServiceName());
  }

}
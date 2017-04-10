package org.symphonyoss.integration.event;

/**
 * Event object to perform the health check in an specific service.
 *
 * Created by rsanchez on 22/03/17.
 */
public class HealthCheckEventData {

  private String serviceName;

  public HealthCheckEventData(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceName() {
    return serviceName;
  }
}

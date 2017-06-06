package org.symphonyoss.integration.api.client.metrics;

import com.codahale.metrics.Timer;

/**
 * Mock the class {@link ApiMetricsController}
 * Created by rsanchez on 06/06/17.
 */
public class MockApiMetricsController extends ApiMetricsController {

  private Boolean status;

  public Timer.Context startApiCall(String requestPath) {
    return new Timer().time();
  }

  public void finishApiCall(Timer.Context context, String path, boolean success) {
    if (context != null) {
      context.close();

      this.status = new Boolean(success);
    }
  }

  public boolean isSuccess() {
    return Boolean.TRUE.equals(status);
  }

}

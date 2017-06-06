package org.symphonyoss.integration.webhook;

import com.codahale.metrics.Timer;
import org.springframework.boot.test.context.TestComponent;
import org.symphonyoss.integration.webhook.metrics.ParserMetricsController;

/**
 * Mock the class {@link ParserMetricsController}
 * Created by rsanchez on 06/06/17.
 */
@TestComponent
public class MockParserMetricsController extends ParserMetricsController {

  private Boolean status;

  @Override
  public Timer.Context startParserExecution(String integration) {
    return new Timer().time();
  }

  @Override
  public void finishParserExecution(Timer.Context context, String integration, boolean success) {
    if (context != null) {
      context.close();

      this.status = new Boolean(success);
    }
  }

  public boolean isSuccess() {
    return Boolean.TRUE.equals(status);
  }

}

package org.symphonyoss.integration.metrics;

/**
 * Holds the constants required by the metric components.
 * Created by rsanchez on 13/12/16.
 */
public class IntegrationMetricsConstants {

  private IntegrationMetricsConstants() {}

  /**
   * Base metric name used by all the metrics.
   */
  public static final String BASE_METRIC_NAME = "integration.metrics";

  /**
   * Base metric name used by metrics that calculates ratios.
   */
  public static final String RATIO = "ratio";

  /**
   * Constant used by metric that calculates success counters
   */
  public static final String SUCCESS = "success";

  /**
   * Constant used by metric that calculates failure counters
   */
  public static final String FAIL = "fail";

}

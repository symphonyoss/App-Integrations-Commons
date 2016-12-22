package org.symphonyoss.integration.metrics;

/**
 * Interface to define an specific controller that monitor information related to the integration.
 * Created by rsanchez on 22/12/16.
 */
public interface IntegrationController {


  /**
   * Initializes the metrics for an specific integration. Each controller should define which
   * metrics must monitor during the application execution.
   * @param integration Integration identifier
   */
  void initController(String integration);

}

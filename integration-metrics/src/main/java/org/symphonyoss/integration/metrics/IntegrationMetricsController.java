package org.symphonyoss.integration.metrics;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class to delegate the metric objects creation to the specific controller.
 * Created by rsanchez on 12/12/16.
 */
@Component
public class IntegrationMetricsController {

  /**
   * Holds the list of controller to be initialized
   */
  private List<IntegrationController> controllers = new ArrayList<>();

  /**
   * Register the integration controller. Each integration controller should perform
   * the self-registration during its own bootstrap.
   * @param controller Integration controller
   */
  public void registerIntegrationController(IntegrationController controller) {
    this.controllers.add(controller);
  }

  /**
   * Delegates the metric objects creation to the specific controller.
   * @param integration
   */
  public void addIntegrationTimer(String integration) {
    for (IntegrationController controller : controllers) {
      controller.initController(integration);
    }
  }
}

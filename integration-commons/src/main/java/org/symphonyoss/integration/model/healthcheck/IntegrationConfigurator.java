package org.symphonyoss.integration.model.healthcheck;

/**
 * - * Holds the information about the configurator application.
 * - * It contains the path of load and icon URL's.
 * - * Created by crepache on 03/05/18.
 * -
 */
public class IntegrationConfigurator {

  private static final String APP_CONTROLLER_PAGE = "/controller.html";

  private static final String APP_ICON_IMAGE = "/img/appstore-logo.png";

  private String loadUrl = APP_CONTROLLER_PAGE;

  private String iconUrl = APP_ICON_IMAGE;

  public String getLoadUrl() {
    return loadUrl;
  }

  public String getIconUrl() {
    return iconUrl;
  }

}

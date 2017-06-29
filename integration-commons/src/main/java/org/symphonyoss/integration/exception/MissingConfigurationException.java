package org.symphonyoss.integration.exception;

/**
 * Missing configuration on the YAML file.
 *
 * Created by rsanchez on 01/03/17.
 */
public class MissingConfigurationException extends IntegrationRuntimeException {

  private static final String DEFAULT_MESSAGE =
      "Verify the YAML configuration file. No configuration found to the key %s";

  public MissingConfigurationException(String component, String key) {
    super(component, String.format(DEFAULT_MESSAGE, key));
  }

  public MissingConfigurationException(String component, String message, String... solutions) {
    super(component, message, solutions);
  }

}

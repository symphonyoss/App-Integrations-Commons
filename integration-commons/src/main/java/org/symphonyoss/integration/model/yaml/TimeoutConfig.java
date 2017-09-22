package org.symphonyoss.integration.model.yaml;

/**
 * Holds the timeout configurations.
 * Created by campidelli on 9/22/17.
 */
public class TimeoutConfig {

  private int connectTimeoutInMillis;
  private int readTimeoutInMillis;
  private int maxConnectTimeoutInMillis;
  private int maxReadTimeoutInMillis;

  public int getConnectTimeoutInMillis() {
    return connectTimeoutInMillis;
  }

  public void setConnectTimeoutInMillis(int connectTimeoutInMillis) {
    this.connectTimeoutInMillis = connectTimeoutInMillis;
  }

  public int getReadTimeoutInMillis() {
    return readTimeoutInMillis;
  }

  public void setReadTimeoutInMillis(int readTimeoutInMillis) {
    this.readTimeoutInMillis = readTimeoutInMillis;
  }

  public int getMaxConnectTimeoutInMillis() {
    return maxConnectTimeoutInMillis;
  }

  public void setMaxConnectTimeoutInMillis(int maxConnectTimeoutInMillis) {
    this.maxConnectTimeoutInMillis = maxConnectTimeoutInMillis;
  }

  public int getMaxReadTimeoutInMillis() {
    return maxReadTimeoutInMillis;
  }

  public void setMaxReadTimeoutInMillis(int maxReadTimeoutInMillis) {
    this.maxReadTimeoutInMillis = maxReadTimeoutInMillis;
  }
}

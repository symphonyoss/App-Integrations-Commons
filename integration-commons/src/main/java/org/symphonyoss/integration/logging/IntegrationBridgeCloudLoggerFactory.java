/**
 * Copyright 2016-2017 Symphony Integrations - Symphony LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.symphonyoss.integration.logging;

import static org.symphonyoss.integration.logging.IntegrationBridgeKeyProvider.CONFIG_HARVESTER_URL;

import com.symphony.logging.ISymphonyCloudIdentifierProvider;
import com.symphony.logging.ISymphonyLogger;
import com.symphony.logging.ISymphonyOnPremKeyProvider;
import com.symphony.logging.SymphonyLoggerFactory;
import com.symphony.logging.appender.OnPremHttpsAppender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

/**
 * Factory responsible to create the cloud logger.
 *
 * This instance will post ERROR and FATAL levels to Symphony's cloud.
 *
 * Created by cmarcondes on 11/17/16.
 */
public class IntegrationBridgeCloudLoggerFactory implements ISymphonyOnPremKeyProvider,
    ISymphonyCloudIdentifierProvider {

  private static final String ON_PREM_APPENDER = "on-prem-appender";

  private static final String IB_LOGGER_NAME = "IntegrationBridgeLog";

  private static IntegrationBridgeCloudLoggerFactory instance = new IntegrationBridgeCloudLoggerFactory();

  private ISymphonyOnPremKeyProvider provider = new DefaultKeyProvider();

  private IntegrationBridgeCloudLoggerFactory() {}

  public static void setProvider(IntegrationBridgeKeyProvider provider) {
    instance.provider = provider;

    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    Configuration config = context.getConfiguration();

    Appender appender = config.getAppender(ON_PREM_APPENDER);

    if(appender != null) {
      OnPremHttpsAppender onPremHttpsAppender = (OnPremHttpsAppender) appender;
      onPremHttpsAppender.setSessionKeyProvider(provider);
      onPremHttpsAppender.setUrl(System.getProperty(CONFIG_HARVESTER_URL));
    }
  }

  /**
   * Returns an instance of the Symphony's cloud logger
   * @param clazz
   * @return Logger instance
   */
  public static ISymphonyLogger getLogger(Class<?> clazz) {
    return SymphonyLoggerFactory.getCloudLogger(clazz, instance, instance).useRemoteLog();
  }

  /**
   * Returns the session key to log into the cloud.
   * It's going to verify if the session was created, if not will try to authenticate.
   * @return Session token
   */
  @Override
  public String getSessionKey() {
    return provider.getSessionKey();
  }

  @Override
  public String getSessionName() {
    return provider.getSessionName();
  }

  /**
   * Tries to authenticate to the cloud
   */
  @Override
  public void reAuth() {
    provider.reAuth();
  }

  @Override
  public String getIdentifier() {
    return IB_LOGGER_NAME;
  }
}

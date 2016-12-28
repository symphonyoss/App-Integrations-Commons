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

import com.symphony.logging.ISymphonyLogger;
import com.symphony.logging.ISymphonyOnPremKeyProvider;
import com.symphony.logging.SymphonyLoggerFactory;
import com.symphony.security.cache.InMemoryPersister;
import com.symphony.security.clientsdk.client.AuthProvider;
import com.symphony.security.clientsdk.client.ClientIdentifierFilter;
import com.symphony.security.clientsdk.client.SymphonyClient;
import com.symphony.security.clientsdk.client.SymphonyClientConfig;
import com.symphony.security.clientsdk.client.impl.SymphonyClientFactory;
import com.symphony.security.exceptions.SymphonyEncryptionException;
import com.symphony.security.exceptions.SymphonyInputException;
import com.symphony.security.exceptions.SymphonyNativeException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.symphonyoss.integration.model.yaml.CloudLogging;
import org.symphonyoss.integration.model.yaml.IntegrationProperties;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

/**
 * Provider class responsible to retrieve the session key.
 * Created by rsanchez on 22/12/16.
 */
@Component
public class IntegrationBridgeKeyProvider implements ISymphonyOnPremKeyProvider {

  private static final ISymphonyLogger LOCAL_LOGGER =
      SymphonyLoggerFactory.getLogger(IntegrationBridgeKeyProvider.class);

  public static final String CONFIG_ENABLE_REMOTE = "enableRemote";
  public static final String CONFIG_LOG_LEVEL = "cloudLoggerLevel";
  public static final String CONFIG_HARVESTER_URL = "cloudLogHarvesterURL";
  public static final String SESSION_NAME = "skey";

  private static final int READ_TIME_OUT = 5000;
  private static final int CONNECTION_TIME_OUT = 1000;

  @Autowired
  private IntegrationProperties properties;

  private AuthProvider authProvider;

  private SymphonyClient symphonyClient;

  private ExecutorService executorForAuthentication = Executors.newSingleThreadExecutor();

  private Future<?> authenticationFuture;

  /**
   * Initiates all the variable needed to be used by the Cloud logger.
   * Reads properties from YAML config file.
   * Creates a Symphony Client.
   * Creates an AuthProvider to authenticate with the username and password got from YAML config
   * file.
   */
  @PostConstruct
  public void init() {
    try {
      prepareAuthentication();
      setSystemProperties();

      IntegrationBridgeCloudLoggerFactory.setProvider(this);
    } catch (Exception e) {
      String msg = "Faileld to initialize factory for integration bridge's cloud logger";
      LOCAL_LOGGER.error(msg, new CloudLoggerException(msg, e));
    }
  }

  private void setSystemProperties() {
    if (System.getProperty(CONFIG_ENABLE_REMOTE) == null) {
      System.setProperty(CONFIG_ENABLE_REMOTE, "true");
    }
    if (System.getProperty(CONFIG_LOG_LEVEL) == null) {
      System.setProperty(CONFIG_LOG_LEVEL, "DEBUG");
    }
    if (System.getProperty(CONFIG_HARVESTER_URL) == null) {
      System.setProperty(CONFIG_HARVESTER_URL, properties.getSymphonyUrl());
    }
  }

  private void prepareAuthentication()
      throws SymphonyInputException, SymphonyNativeException, IOException,
      SymphonyEncryptionException {
    initializeSymphonyClient();
    initializeAuthProvider();
  }

  private void initializeAuthProvider()
      throws SymphonyNativeException, SymphonyInputException, SymphonyEncryptionException {
    CloudLogging cloudLogging = properties.getCloudLogging();

    authProvider = new AuthProvider(new InMemoryPersister());
    authProvider.setAuthKeyManager(false);
    authProvider.setSymphonyChallengePath(AuthProvider.STD_SYMPHONY_KEY_CHALLENGE);
    authProvider.setSymphonyResponsePath(AuthProvider.STD_SYMPHONY_KEY_RESPONSE);

    authProvider.generateAuth(symphonyClient);
    authProvider.getSymphonyAuth().setAccountName(cloudLogging.getAccount());
    authProvider.getSymphonyAuth().storeSecretKey(cloudLogging.getSecret());
  }

  private void initializeSymphonyClient() throws IOException {
    SymphonyClientConfig symphonyClientConfig = new SymphonyClientConfig();
    symphonyClientConfig.setAcountName(properties.getCloudLogging().getAccount());
    symphonyClientConfig.setSymphonyUrl(properties.getSymphonyUrl());
    symphonyClientConfig.setLoginUrl(properties.getLoginUrl());
    symphonyClientConfig.setReadTimeout(READ_TIME_OUT);
    symphonyClientConfig.setConnectTimeout(CONNECTION_TIME_OUT);

    symphonyClient = SymphonyClientFactory.getClient(
        new ClientIdentifierFilter("1.0.0", "IntegrationBridgeLogger"), symphonyClientConfig);
  }

  /**
   * Returns the session key to log into the cloud.
   * It's going to verify if the session was created, if not will try to authenticate.
   * @return Session token
   */
  @Override
  public String getSessionKey() {
    String sReturn;

    try {
      sReturn = authProvider.getSymphonyAuth().getSession();

      if (sReturn == null) {
        //the following is for lazy authentication in a separate thread while we are waiting for
        // the pod to come up.
        //Until we can return a valid session key value, the cloud logger will keep log messages
        //in an internal queue. As soon as authentication is complete and session key can be
        //returned, the messages will be pushed to the cloud.

        //This method may be called by multiple threads. Don't start authentication multiple times -
        //only if we haven't run this or if it has already finished then fire a new one
        synchronized (IntegrationBridgeCloudLoggerFactory.class) {
          if ((authenticationFuture == null) ||
              ((authenticationFuture != null) && (authenticationFuture.isDone()))) {

            authenticationFuture = executorForAuthentication.submit(new Runnable() {
              @Override
              public void run() {
                try {
                  //wait a minute before trying - otherwise authentication code
                  //logs too many exceptions
                  Thread.sleep(60000);
                  reAuth();
                } catch (Exception e) {
                  //there will be multiple exceptions during pod start up. They do not indicate real
                  //problems - simply not all components are up and running yet. Hence the log level
                  //"info" and only exception message, not the full stacktrace
                }
              }
            });
          }
        }
      }
    } catch (Exception e) {
      return StringUtils.EMPTY;
    }

    //As of the time of this change: onpremhttpsclient catches only IOException when calling this
    //method and does not check for NULLs. Therefore, replace null with empty string.
    if (sReturn == null) {
      return StringUtils.EMPTY;
    }

    return sReturn;
  }

  @Override
  public String getSessionName() {
    return SESSION_NAME;
  }

  /**
   * Tries to authenticate to the cloud
   */
  @Override
  public void reAuth() {
    authProvider.getSymphonyAuth().authIfNeeded(symphonyClient);
  }
}

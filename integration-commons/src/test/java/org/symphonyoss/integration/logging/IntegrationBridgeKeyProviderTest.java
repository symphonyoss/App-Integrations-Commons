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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import com.symphony.security.clientsdk.client.Auth;
import com.symphony.security.clientsdk.client.AuthProvider;
import com.symphony.security.clientsdk.client.impl.SymphonyClientFactory;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.model.yaml.CloudLogging;
import org.symphonyoss.integration.model.yaml.ConnectionInfo;
import org.symphonyoss.integration.model.yaml.IntegrationProperties;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

/**
 * Unit test for {@link IntegrationBridgeKeyProvider}
 *
 * Created by cmarcondes on 12/7/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class IntegrationBridgeKeyProviderTest {

  @Spy
  private IntegrationProperties properties = new IntegrationProperties();

  @Mock
  private ExecutorService executorForAuthentication;

  @Mock
  private AuthProvider authProvider;

  private FutureTask future;

  @InjectMocks
  private IntegrationBridgeKeyProvider provider = new IntegrationBridgeKeyProvider();

  /**
   * Mocks the getClient method of {@link SymphonyClientFactory}
   * and the method submit of {@link ExecutorService}
   * @throws Exception
   */
  @Before
  public void setup() throws Exception {
    mockProperties();
    future = mockThreadExecutor();
  }

  /**
   * Tests the method getSessionKey, simulating multi-threads. It's going to follow this steps:
   *
   * The test will call the method getSessionKey four times.
   * - First time: Sessionkey and AuthenticationFuture will be null, so it's going to start a new
   * thread.
   * - Second time: Sessionkey is null, the thread started above has not finished yet, so
   * AuthenticationFuture.isClosed returns false, and won't start a new thread.
   * - Third time: The thread created at first time finished, AuthenticationFuture.isClosed returns
   * true, but the Sessionkey still null, so will create a new thread.
   * - Fourth time: The thread created above fineshed and the Sessionkey was filled
   * @throws Exception
   */
  @Test
  public void test() throws Exception {
    Auth authMocked = mock(Auth.class);

    /*
     * Mocks the number of times that the getSession will return null, until returns a valid value.
     */
    when(authMocked.getSession())
        .thenReturn(null)
        .thenReturn(null)
        .thenReturn(null)
        .thenReturn("45123138714312");

    when(authProvider.getSymphonyAuth()).thenReturn(authMocked);

    assertTrue(StringUtils.isEmpty(provider.getSessionKey()));
    assertTrue(StringUtils.isEmpty(provider.getSessionKey()));
    assertTrue(StringUtils.isEmpty(provider.getSessionKey()));
    verify(future, times(2)).isDone();
    assertEquals("45123138714312", provider.getSessionKey());
  }

  private FutureTask mockThreadExecutor() {
    FutureTask future = mock(FutureTask.class);

    //returns false for the first call and true the next.

    when(future.isDone()).thenReturn(false).thenReturn(true);

    when(executorForAuthentication.submit(any(Runnable.class))).thenReturn(future);

    return future;
  }

  private void mockProperties() throws Exception {
    ConnectionInfo pod = new ConnectionInfo();
    pod.setHost("nexus.symphony.com");
    pod.setPort("443");

    properties.setPod(pod);

    CloudLogging cloudLogging = new CloudLogging();
    cloudLogging.setAccount("cloudlogger");
    cloudLogging.setSecret("/uM9z6JeaGIA85JN9vtrPYVYzeyMArgxZNNGGkrXqCE=");

    properties.setCloudLogging(cloudLogging);
  }

}

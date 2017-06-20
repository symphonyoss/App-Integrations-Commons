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

package org.symphonyoss.integration.metrics;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for {@link IntegrationMetricsController}
 * Created by campidelli on 6/20/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class IntegrationMetricsControllerTest {

  private static final String INTEGRATION = "integration";

  @Mock
  private IntegrationController controller;

  @Spy
  private List<IntegrationController> controllers = new ArrayList<>();

  @InjectMocks
  private IntegrationMetricsController metricsController;

  @Test
  public void testAddIntegrationTimer() {
    controllers.add(controller);
    metricsController.addIntegrationTimer(INTEGRATION);
    verify(controller).initController(INTEGRATION);
  }
}

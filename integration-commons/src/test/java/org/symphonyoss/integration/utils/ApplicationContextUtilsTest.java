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

package org.symphonyoss.integration.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.symphonyoss.integration.Integration;
import org.symphonyoss.integration.MockIntegration;

/**
 * Unit test for {@link ApplicationContextUtils}
 * Created by rsanchez on 31/03/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationContextUtilsTest {

  @Mock
  private ApplicationContext context;

  private ApplicationContextUtils utils;

  @Before
  public void init() {
    this.utils = new ApplicationContextUtils(context);
  }

  @Test(expected = NoSuchBeanDefinitionException.class)
  public void testGetRequiredBeanWithNullContext() {
    this.utils = new ApplicationContextUtils(null);
    ApplicationContextUtils.getRequiredBean(MockIntegration.class);
  }

  @Test(expected = NoSuchBeanDefinitionException.class)
  public void testGetRequiredBeanNotFound() {
    doThrow(NoSuchBeanDefinitionException.class).when(context).getBean(MockIntegration.class);

    ApplicationContextUtils.getRequiredBean(MockIntegration.class);
  }

  @Test
  public void testGetRequiredBean() {
    Integration integration = new MockIntegration();
    doReturn(integration).when(context).getBean(MockIntegration.class);

    Integration bean = ApplicationContextUtils.getRequiredBean(MockIntegration.class);
    assertEquals(integration, bean);
  }

  @Test
  public void testGetBeanWithNullContext() {
    this.utils = new ApplicationContextUtils(null);

    Integration bean = ApplicationContextUtils.getBean(MockIntegration.class);
    assertNull(bean);
  }

  @Test
  public void testGetBeanNotFound() {
    doThrow(NoSuchBeanDefinitionException.class).when(context).getBean(MockIntegration.class);

    Integration bean = ApplicationContextUtils.getBean(MockIntegration.class);
    assertNull(bean);
  }

  @Test
  public void testGetBean() {
    Integration integration = new MockIntegration();
    doReturn(integration).when(context).getBean(MockIntegration.class);

    Integration bean = ApplicationContextUtils.getBean(MockIntegration.class);
    assertEquals(integration, bean);
  }
}

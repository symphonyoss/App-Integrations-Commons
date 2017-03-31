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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Helper class to retrieve Spring beans to be used for a non-component classes.
 * Created by rsanchez on 10/02/17.
 */
@Component
public class ApplicationContextUtils {

  /**
   * Application context
   */
  private static ApplicationContext context;

  @Autowired
  public ApplicationContextUtils(ApplicationContext applicationContext) {
    context = applicationContext;
  }

  /**
   * Return the bean instance that uniquely matches the given object type, if any.
   * @param type type the bean must match; can be an interface or superclass.
   * @return an instance of the single bean matching the required type
   * @throws NoSuchBeanDefinitionException if no bean of the given type was found or context is null
   */
  public static <T> T getRequiredBean(Class<T> type) {
    if (context != null) {
      return context.getBean(type);
    }

    throw new NoSuchBeanDefinitionException(type);
  }

  /**
   * Return the bean instance that uniquely matches the given object type, if any.
   * @param type type the bean must match; can be an interface or superclass.
   * @return an instance of the single bean matching the required type or null if the context
   * isn't provided
   */
  public static <T> T getBean(Class<T> type) {
    if (context != null) {
      try {
        return context.getBean(type);
      } catch (BeansException e) {
        return null;
      }
    }

    return null;
  }

}

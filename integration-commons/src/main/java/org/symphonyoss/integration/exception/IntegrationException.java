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

package org.symphonyoss.integration.exception;

/**
 * Checked exception who will format the message following this pattern {@link
 * ExceptionMessageFormatter}
 *
 * When you should use this instead of {@link IntegrationRuntimeException}?
 * - It should be used if the caller needs to do something in response to the error.
 *
 * Created by cmarcondes on 10/20/16.
 */
public class IntegrationException extends Exception {

  /**
   * Constructs a new IntegrationException with the component name and the specified detail message.
   * @param component The component where the exception was thrown.
   * @param message The message why the exceptions happened.
   */
  public IntegrationException(String component, String message) {
    super(ExceptionMessageFormatter.format(component, message));
  }

  /**
   * Constructs a new IntegrationException with the component name, the specified detail message and
   * cause.
   * @param component The component where the exception was thrown.
   * @param message The message why the exceptions happened.
   * @param cause The root cause of the error.
   */
  public IntegrationException(String component, String message, Throwable cause) {
    super(ExceptionMessageFormatter.format(component, message, cause), cause);
  }

  /**
   * Constructs a new IntegrationException with the component name, the specified detail message and
   * cause.
   * @param component The component where the exception was thrown.
   * @param message The message why the exceptions happened.
   * @param cause The root cause of the error.
   * @param solutions The solutions provided for exception.
   */
  public IntegrationException(String component, String message, Throwable cause, String... solutions) {
    super(ExceptionMessageFormatter.format(component, message, cause, solutions), cause);
  }

  /**
   * Constructs a new IntegrationException with the component name, the specified detail message, and
   * the solutions provided for this exception.
   * @param component The component where the exception was thrown.
   * @param message The message why the exceptions happened.
   * @param solutions The solutions provided for exception.
   */
  public IntegrationException(String component, String message, String... solutions) {
    super(ExceptionMessageFormatter.format(component, message, solutions));
  }
}
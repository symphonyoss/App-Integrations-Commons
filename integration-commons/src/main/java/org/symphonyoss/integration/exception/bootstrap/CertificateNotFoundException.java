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

package org.symphonyoss.integration.exception.bootstrap;

/**
 * Certificate not found:
 *
 * Possible reasons:
 * - Certs folder does not exist or cert files are missing.
 * - Wrong cert file path provided on atlas.
 * - Wrong atlas home is
 *
 * Created by cmarcondes on 10/26/16.
 */
public class CertificateNotFoundException extends BootstrapException {

  public CertificateNotFoundException(String message, Exception cause) {
    super(message, cause);
  }
}

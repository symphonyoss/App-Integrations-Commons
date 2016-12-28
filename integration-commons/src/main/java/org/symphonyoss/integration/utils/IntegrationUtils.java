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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.symphonyoss.integration.exception.bootstrap.CertificateNotFoundException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Integration Bridge utility class to retrieve application settings.
 * Created by rsanchez on 28/12/16.
 */
@Component
public class IntegrationUtils {

  public static final String CERTS_DIR = "certs";

  /**
   * Define the web server base directory
   */
  private String webServerBaseDir;

  @Autowired
  public IntegrationUtils(@Value("${server.tomcat.basedir}") String webServerBaseDir) {
    this.webServerBaseDir = webServerBaseDir;
  }

  /**
   * Retrieve the certificate's directory.
   * @return Certificate's directory.
   */
  public String getCertsDirectory() {
    if (StringUtils.isBlank(webServerBaseDir)) {
      throw new CertificateNotFoundException();
    }

    Path certsPath = Paths.get(webServerBaseDir).getParent().resolve(CERTS_DIR);
    if (!Files.isDirectory(certsPath)) {
      throw new CertificateNotFoundException();
    }

    return certsPath.toAbsolutePath() + File.separator;
  }

}

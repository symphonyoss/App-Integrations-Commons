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
import static org.symphonyoss.integration.utils.IntegrationUtils.CERTS_DIR;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.symphonyoss.integration.exception.bootstrap.CertificateNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Unit test class to validate {@link IntegrationUtils}
 * Created by rsanchez on 28/12/16.
 */
public class IntegrationUtilsTest {

  @Test(expected = CertificateNotFoundException.class)
  public void testBlankPath() {
    IntegrationUtils utils = new IntegrationUtils("");
    utils.getCertsDirectory();
  }

  @Test(expected = CertificateNotFoundException.class)
  public void testPathNotFound() {
    IntegrationUtils utils = new IntegrationUtils(System.getProperty("java.io.tmpdir"));
    utils.getCertsDirectory();
  }

  @Test
  public void testValidPath() throws IOException {
    String tmpDir = System.getProperty("java.io.tmpdir");
    Path certsPath = Paths.get(tmpDir).resolve(CERTS_DIR);

    Files.createDirectories(certsPath);

    IntegrationUtils utils = new IntegrationUtils(certsPath.toAbsolutePath().toString());
    String result = utils.getCertsDirectory();

    FileUtils.deleteDirectory(certsPath.toFile());

    String expected = tmpDir + File.separator + CERTS_DIR + File.separator;
    assertEquals(expected, result);
  }
}

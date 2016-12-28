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

package org.symphonyoss.integration.webhook;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Holds mocks for other unit tests.
 * Created by rsanchez on 22/11/16.
 */
public class CommonIntegrationTest {

  private static final String APP_TYPE = "jiraWebHookIntegration";

  private static final String DEFAULT_KEYSTORE_TYPE = "pkcs12";

  private static final String DEFAULT_KEYSTORE_PASSWORD = "changeit";

  private static final String DEFAULT_KEYSTORE_TYPE_SUFFIX = ".p12";

  private static final String CERTS_DIR = "certs";

  protected String mockCertDir() throws IOException {
    Path tmpDir = Paths.get(System.getProperty("java.io.tmpdir")).resolve(CERTS_DIR);
    FileUtils.deleteDirectory(tmpDir.toFile());
    return Files.createDirectory(tmpDir).toAbsolutePath() + File.separator;
  }

  protected String mockKeyStore()
      throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
    KeyStore ks = KeyStore.getInstance(DEFAULT_KEYSTORE_TYPE);

    char[] password = DEFAULT_KEYSTORE_PASSWORD.toCharArray();
    ks.load(null, password);

    // Store away the keystore.
    String filename = APP_TYPE + DEFAULT_KEYSTORE_TYPE_SUFFIX;
    String certsDir = mockCertDir();
    String storeLocation = certsDir + filename;

    try (FileOutputStream fos = new FileOutputStream(storeLocation)) {
      ks.store(fos, password);
    }

    return certsDir;
  }
}

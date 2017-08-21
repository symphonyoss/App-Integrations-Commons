/**
 * Copyright 2016-2017 Symphony Integrations - Symphony LLC
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.symphonyoss.integration.authorization.oauth;

import com.google.api.client.auth.oauth.OAuthRsaSigner;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.symphonyoss.integration.authorization.oauth.v1.OAuth1Exception;
import org.symphonyoss.integration.logging.LogMessageSource;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Deals with public and private RSA keys.
 *
 * Created by campidelli on 25-jul-2017.
 */
@Component
public class OAuthRsaSignerFactory {

  private static final String RSA_NOT_FOUND = "integration.authorization.rsa.notfound";

  private static final String RSA_NOT_FOUND_SOLUTION = RSA_NOT_FOUND + ".solution";

  private static final String INVALID_PRIVATE_KEY = "integration.authorization.invalid.privatekey";

  private static final String INVALID_PRIVATE_KEY_SOLUTION = INVALID_PRIVATE_KEY + ".solution";

  private static final String INVALID_PUBLIC_KEY = "integration.authorization.invalid.publickey";

  private static final String INVALID_PUBLIC_KEY_SOLUTION = INVALID_PUBLIC_KEY + ".solution";

  @Autowired
  private LogMessageSource logMessage;

  /**
   * Creates a RSA signer based on a given private key.
   * @param privateKey private key in PKCS8 syntax.
   * @return OAuthRsaSigner
   */
  public OAuthRsaSigner getOAuthRsaSigner(String privateKey) throws OAuth1Exception {
    OAuthRsaSigner oAuthRsaSigner = new OAuthRsaSigner();
    oAuthRsaSigner.privateKey = getPrivateKey(privateKey);
    return oAuthRsaSigner;
  }

  /**
   * Convert the passed String into a PrivateKey.
   * @param privateKey String format of a key.
   * @return PrivateKey converted.
   * @throws OAuth1Exception Thrown in case of error.
   */
  public PrivateKey getPrivateKey(String privateKey) throws OAuth1Exception {
    byte[] privateBytes = Base64.decodeBase64(privateKey);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);

    try {
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePrivate(keySpec);
    } catch (NoSuchAlgorithmException e) {
      throw new OAuth1Exception(logMessage.getMessage(RSA_NOT_FOUND), e,
          logMessage.getMessage(RSA_NOT_FOUND_SOLUTION));
    } catch (InvalidKeySpecException e) {
      throw new OAuth1Exception(logMessage.getMessage(INVALID_PRIVATE_KEY), e,
          logMessage.getMessage(INVALID_PRIVATE_KEY_SOLUTION));
    }
  }

  /**
   * Convert the passed String into a PublicKey.
   * @param publicKey String format of a key.
   * @return PublicKey converted.
   * @throws OAuth1Exception Thrown in case of error.
   */
  public PublicKey getPublicKey(String publicKey) throws OAuth1Exception {
    try {
      byte[] encoded = Base64.decodeBase64(publicKey);
      X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);

      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePublic(spec);
    } catch (NoSuchAlgorithmException e) {
      throw new OAuth1Exception(logMessage.getMessage(RSA_NOT_FOUND), e,
          logMessage.getMessage(RSA_NOT_FOUND_SOLUTION));
    } catch (InvalidKeySpecException e) {
      throw new OAuth1Exception(logMessage.getMessage(INVALID_PUBLIC_KEY), e,
          logMessage.getMessage(INVALID_PUBLIC_KEY_SOLUTION));
    }
  }
}

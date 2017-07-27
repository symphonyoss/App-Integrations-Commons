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
 * Builds a RSA Signer based on a PKCS8 private key.
 *
 * Created by campidelli on 25-jul-2017.
 */
@Component
public class OAuthRsaSignerFactory {

  @Autowired
  LogMessageSource logMessage;

  /**
   * @param privateKey private key in PKCS8 syntax.
   * @return OAuthRsaSigner
   */
  public OAuthRsaSigner getOAuthRsaSigner(String privateKey) {
    OAuthRsaSigner oAuthRsaSigner = new OAuthRsaSigner();
    oAuthRsaSigner.privateKey = getPrivateKey(privateKey);
    return oAuthRsaSigner;
  }

  public PrivateKey getPrivateKey(String privateKey) {
    byte[] privateBytes = Base64.decodeBase64(privateKey);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);

    try {
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePrivate(keySpec);
    } catch (NoSuchAlgorithmException e) {
      throw new OAuth1Exception(logMessage.getMessage("integration.authorization.rsa.notfound"),
          e, logMessage.getMessage("integration.authorization.rsa.notfound.solution"));
    } catch (InvalidKeySpecException e) {
      throw new OAuth1Exception(logMessage.getMessage("integration.authorization.invalid.privatekey"),
          e, logMessage.getMessage("integration.authorization.invalid.privatekey.solution"));
    }
  }

  public PublicKey getPublicKey(String publicKey) {
    try {
      byte[] encoded = Base64.decodeBase64(publicKey);
      X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);

      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePublic(spec);
    } catch (NoSuchAlgorithmException e) {
      throw new OAuth1Exception(logMessage.getMessage("integration.authorization.rsa.notfound"),
          e, logMessage.getMessage("integration.authorization.rsa.notfound.solution"));
    } catch (InvalidKeySpecException e) {
      throw new OAuth1Exception(logMessage.getMessage("integration.authorization.invalid.publickey"),
          e, logMessage.getMessage("integration.authorization.invalid.publickey.solution"));
    }
  }

  public PublicKey getPublicKey(String publicKey) {
    try {
      byte[] encoded = Base64.decodeBase64(publicKey);
      X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);

      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePublic(spec);
    } catch (NoSuchAlgorithmException e) {
      throw new OAuth1Exception("'RSA' algorithm not found.", e,
          "Verify the available signature algorithms supported by your Java installation. "
              + "There must be one called 'RSA'.");
    } catch (InvalidKeySpecException e) {
      throw new OAuth1Exception("Informed public key is invalid.", e, "Generate the public key again.");
    }
  }
}

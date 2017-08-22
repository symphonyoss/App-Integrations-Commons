package org.symphonyoss.integration.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.symphonyoss.integration.exception.authentication.UnexpectedAuthException;
import org.symphonyoss.integration.logging.LogMessageSource;
import sun.security.provider.X509Factory;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Deals with public and private RSA keys.
 *
 * Created by campidelli on 25-jul-2017.
 */
@Component
public class RsaKeyUtils {

  private static final String RSA_NOT_FOUND = "integration.authorization.rsa.notfound";
  private static final String RSA_NOT_FOUND_SOLUTION = RSA_NOT_FOUND + ".solution";
  private static final String INVALID_PRIVATE_KEY = "integration.authorization.invalid.privatekey";
  private static final String INVALID_PRIVATE_KEY_SOLUTION = INVALID_PRIVATE_KEY + ".solution";
  private static final String INVALID_PUBLIC_KEY = "integration.authorization.invalid.publickey";
  private static final String INVALID_PUBLIC_KEY_SOLUTION = INVALID_PUBLIC_KEY + ".solution";
  private static final String INVALID_PUBLIC_CERT = "integration.authorization.invalid.certificate";
  private static final String INVALID_PUBLIC_CERT_SOLUTION = INVALID_PUBLIC_CERT + ".solution";

  private static final String PUBLIC_KEY_PREFIX = "-----BEGIN PUBLIC KEY-----\n";
  private static final String PUBLIC_KEY_SUFFIX = "-----END PUBLIC KEY-----\n";
  private static final String PRIVATE_KEY_PREFIX = "-----BEGIN PRIVATE KEY-----\n";
  private static final String PRIVATE_KEY_SUFFIX = "-----END PRIVATE KEY-----\n";

  @Autowired
  private LogMessageSource logMessage;

  /**
   * Convert the passed String into a PrivateKey.
   * @param privateKey String format of a key.
   * @return PrivateKey converted.
   */
  public PrivateKey getPrivateKey(String privateKey) {
    byte[] privateBytes = Base64.decodeBase64(privateKey);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);

    try {
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePrivate(keySpec);
    } catch (NoSuchAlgorithmException e) {
      throw new UnexpectedAuthException(logMessage.getMessage(RSA_NOT_FOUND), e,
          logMessage.getMessage(RSA_NOT_FOUND_SOLUTION));
    } catch (InvalidKeySpecException e) {
      throw new UnexpectedAuthException(logMessage.getMessage(INVALID_PRIVATE_KEY), e,
          logMessage.getMessage(INVALID_PRIVATE_KEY_SOLUTION));
    }
  }

  /**
   * Convert the passed String into a PublicKey.
   * @param publicKey String format of a key.
   * @return PublicKey converted.
   */
  public PublicKey getPublicKey(String publicKey) {
    try {
      byte[] encoded = Base64.decodeBase64(publicKey);
      X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);

      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePublic(spec);
    } catch (NoSuchAlgorithmException e) {
      throw new UnexpectedAuthException(logMessage.getMessage(RSA_NOT_FOUND), e,
          logMessage.getMessage(RSA_NOT_FOUND_SOLUTION));
    } catch (InvalidKeySpecException e) {
      throw new UnexpectedAuthException(logMessage.getMessage(INVALID_PUBLIC_KEY), e,
          logMessage.getMessage(INVALID_PUBLIC_KEY_SOLUTION));
    }
  }

  /**
   * Remove the prefix and suffix from a String private key.
   * @param privateKey String private key to be trimmed.
   * @return Private key String trimmed.
   */
  public String trimPrivateKey(String privateKey) {
    return privateKey.replace(PRIVATE_KEY_PREFIX, StringUtils.EMPTY)
        .replace(PRIVATE_KEY_SUFFIX, StringUtils.EMPTY);
  }

  /**
   * Remove the prefix and suffix from a String public key.
   * @param publicKey String public key to be trimmed.
   * @return Public key String trimmed.
   */
  public String trimPublicKey(String publicKey) {
    return publicKey.replace(PUBLIC_KEY_PREFIX, StringUtils.EMPTY)
        .replace(PUBLIC_KEY_SUFFIX, StringUtils.EMPTY);
  }

  /**
   * Gets a public key from a public certificate.
   * @param certificate X509 certificate to extract the public key from.
   * @return Extracted Public Key.
   */
  public PublicKey getPublicKeyFromCertificate(String certificate) {
    try {
      certificate = certificate.replace(X509Factory.BEGIN_CERT, StringUtils.EMPTY);
      certificate = certificate.replace(X509Factory.END_CERT, StringUtils.EMPTY);
      byte[] decoded = Base64.decodeBase64(certificate);

      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      X509Certificate x509Certificate =
          (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(decoded));

      return x509Certificate.getPublicKey();
    } catch (CertificateException e) {
      throw new UnexpectedAuthException(logMessage.getMessage(INVALID_PUBLIC_CERT), e,
          logMessage.getMessage(INVALID_PUBLIC_CERT_SOLUTION));
    }
  }

  /**
   * Parse a PublicKey to String.
   * @param publicKey to be parsed.
   * @return String-like public key.
   */
  public String parsePublicKey(PublicKey publicKey) {
    try {
      KeyFactory kf = KeyFactory.getInstance("RSA");
      X509EncodedKeySpec spec = kf.getKeySpec(publicKey, X509EncodedKeySpec.class);
      return Base64.encodeBase64String(spec.getEncoded()).replaceAll("[\n\r]", "");
    } catch (NoSuchAlgorithmException e) {
      throw new UnexpectedAuthException(logMessage.getMessage(RSA_NOT_FOUND), e,
          logMessage.getMessage(RSA_NOT_FOUND_SOLUTION));
    } catch (InvalidKeySpecException e) {
      throw new UnexpectedAuthException(logMessage.getMessage(INVALID_PUBLIC_KEY), e,
          logMessage.getMessage(INVALID_PUBLIC_KEY_SOLUTION));
    }
  }
}

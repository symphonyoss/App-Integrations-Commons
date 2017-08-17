package org.symphonyoss.integration.utils;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import com.google.api.client.auth.oauth.OAuthRsaSigner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.exception.authentication.UnexpectedAuthException;
import org.symphonyoss.integration.logging.LogMessageSource;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import sun.misc.BASE64Encoder;

/**
 * Test class to validate {@link RsaKeyUtils}
 * Created by campidelli on 8/17/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class RsaKeyUtilsTest {

  private static final String CONSUMER_KEY = "OauthKey";

  private static final String PRIVATE_KEY_PREFIX = "-----BEGIN PRIVATE KEY-----\n";
  private static final String PRIVATE_KEY_SUFFIX = "-----END PRIVATE KEY-----\n";
  private static final String PRIVATE_KEY_TRIMMED = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAo"
      + "GBAN8wcSF5AE7sL30p2mnM0X3T1OZy4BDfxucZTYdYmg99vqv6uVQyjc4zKOHRiwnCh2GwatT4jBfoQfWx6VUmvcxK"
      + "HuZwcVCHF/u/Vw85wsMDpD4pBglpX1GsFlfSQe1E115X7mHD7tHlkQHvtVplf5BmYxM6G2EljBmiRRQq4OLbAgMBAA"
      + "ECgYEAxu54h6tAWRgvo9IgOVk0CIE9LEKL8L5knStybQbOGqyrvMJ3WdLNjlMPR2fsE8DtxmbmcfkvdUexMvtmzF0B"
      + "oWDvJgqnGaUr9l0gZfGCR0ir2PBJ7V9OOJz5ug4ExLz6S9WNV6RdtXOSXSbNG3/L+56tocA05JpZrZaUfK43V0ECQQ"
      + "DyjkokOrk54DwdnSH86V2bXn+RlzAyumhfGKJpC7pbeZgcSJtkbV9RslEr+TcVuuJyHZGeWtPEStl1BaKnvRLxAkEA"
      + "649aVUD1b9Cly+Q2l7KbgDjny5k/Ezw7JK3hjYEKQrHjgkMejOuKSkeRz2imWD8PLoJ01GgMXLIiu+F1lb06iwJBAI"
      + "7NJuldiV+BnOLyd+gmnG20nPZiRIYZKQmTv0qJFRZ16A/+zz25Br1adl+lQcERXfBBaFIKt1KBnrU+tBx9PIECQQCL"
      + "quG6rttXwvSrIdMkuufsbNEzLNfzRcEjjF2yExLMXMEymS1iDL5gMHNJ8RjANhOAViWDU3YQ+CYUFCgt8pblAkEAhM"
      + "5ky54f3UViEO29UyWv2ZNaZPd17bSr8HAo/lxXyju4TRNRB3vIq79lMNalX5HKHlI9EST7xXLh110xXRH9/Q\\=\\=";
  private static final String PRIVATE_KEY = PRIVATE_KEY_PREFIX + PRIVATE_KEY_TRIMMED
      + PRIVATE_KEY_SUFFIX;

  private static final String PUBLIC_KEY_PREFIX = "-----BEGIN PUBLIC KEY-----\n";
  private static final String PUBLIC_KEY_SUFFIX = "-----END PUBLIC KEY-----\n";
  private static final String PUBLIC_KEY_TRIMMED = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1WMuX9fL"
      + "lyMKII1IirzrkGrBcRQjpsTuEABNQ6GC8cl2vW4NxFgtGuKMlzo7FxY4O1rsw8/uoJopMIIv+FFK9VQ4syb1N4pekA"
      + "VbNv8OzI9d2hxffro6J1POMjZCO6N00e+KQIXRm4wINiPboTHHE7prpcTQV97rWbXyp2mLltQIDAQAB";
  private static final String PUBLIC_KEY = PUBLIC_KEY_PREFIX + PUBLIC_KEY_TRIMMED
      + PUBLIC_KEY_SUFFIX;

  @Mock
  private LogMessageSource logMessage;

  @InjectMocks
  private RsaKeyUtils rsaKeyUtils;

  @Test
  public void testGetOAuthRsaSigner() {
    PrivateKey privateKey = rsaKeyUtils.getPrivateKey(PRIVATE_KEY_TRIMMED);
    OAuthRsaSigner oAuthRsaSigner = rsaKeyUtils.getOAuthRsaSigner(PRIVATE_KEY_TRIMMED);
    assertNotNull(oAuthRsaSigner);
    assertEquals(privateKey, oAuthRsaSigner.privateKey);
  }

  @Test
  public void testTrimPrivateKey() {
    String trimmedPK = rsaKeyUtils.trimPrivateKey(PRIVATE_KEY);
    assertEquals(PRIVATE_KEY_TRIMMED, trimmedPK);
  }

  @Test(expected = UnexpectedAuthException.class)
  public void testInvalidPrivateKey() {
    rsaKeyUtils.getOAuthRsaSigner("?");
  }

  @Test
  public void testTrimPublicKey() {
    String trimmedPK = rsaKeyUtils.trimPublicKey(PUBLIC_KEY);
    assertEquals(PUBLIC_KEY_TRIMMED, trimmedPK);
  }

  @Test
  public void testGetPublicKey() throws Exception {
    PublicKey publicKey = rsaKeyUtils.getPublicKey(PUBLIC_KEY_TRIMMED);
    String publicKeyAsString = parsePublicKey(publicKey);
    assertEquals(PUBLIC_KEY_TRIMMED, publicKeyAsString);
  }

  private String parsePublicKey(PublicKey publicKey)
      throws NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException {
    KeyFactory kf = KeyFactory.getInstance("RSA");
    X509EncodedKeySpec spec = kf.getKeySpec(publicKey, X509EncodedKeySpec.class);
    BASE64Encoder encoder = new BASE64Encoder();
    return encoder.encode(spec.getEncoded()).replace("\n", "");
  }

  @Test(expected = UnexpectedAuthException.class)
  public void testInvalidPublicKey() {
    rsaKeyUtils.getPublicKey("?");
  }
}

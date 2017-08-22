package org.symphonyoss.integration.utils;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.exception.authentication.UnexpectedAuthException;
import org.symphonyoss.integration.logging.LogMessageSource;
import sun.misc.BASE64Encoder;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

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
  private static final String PUBLIC_KEY_TRIMMED = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA54V"
      + "WseT0hHhlu/JdNr+O7HPAs0OSAzW963W4P9/XoUykNZ8UltwGFdVLLyBkIm0GT7Jgqd3y0W2b90eUykbeaXsodiwkD"
      + "knb6gqCY/Z5duxojO5os45hp/++gf7DTTXG48K/gNU63LdRxP+WxpLde9AKJsRhcQHYfJi+nVhQ/Iehaldcj87Vri/"
      + "dQMYH+AERsRApUmG2QM+VwWK1tCtfZ5UCacMg07gkaj9QyHyYCE0A7ScqaHNWtIdSjX88bgGKaT208nRm2nE2nyjdf"
      + "JM91pb/dVcWPNC0XXqjPnLjaywO7jtM3gcmMREJNKdqqd69wd4khK8oqWuHayL1S5LDXQIDAQAB";
  private static final String PUBLIC_KEY = PUBLIC_KEY_PREFIX + PUBLIC_KEY_TRIMMED
      + PUBLIC_KEY_SUFFIX;
  private static final String PUBLIC_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n"
      + "MIIEQDCCAyigAwIBAgIVAKmVBTro/GCF6u842F9q7JdYVYguMA0GCSqGSIb3DQEB\n"
      + "CwUAMGQxJjAkBgNVBAMMHUlzc3VpbmcgQ2VydGlmaWNhdGUgQXV0aG9yaXR5MS0w\n"
      + "KwYDVQQKDCRTeW1waG9ueSBDb21tdW5pY2F0aW9uIFNlcnZpY2VzIExMQy4xCzAJ\n"
      + "BgNVBAYTAlVTMB4XDTE3MDYyODE2MDMxMVoXDTM3MDYyODE2MDMxMVowZDEmMCQG\n"
      + "A1UEAwwdSXNzdWluZyBDZXJ0aWZpY2F0ZSBBdXRob3JpdHkxLTArBgNVBAoMJFN5\n"
      + "bXBob255IENvbW11bmljYXRpb24gU2VydmljZXMgTExDLjELMAkGA1UEBhMCVVMw\n"
      + "ggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDnhVax5PSEeGW78l02v47s\n"
      + "c8CzQ5IDNb3rdbg/39ehTKQ1nxSW3AYV1UsvIGQibQZPsmCp3fLRbZv3R5TKRt5p\n"
      + "eyh2LCQOSdvqCoJj9nl27GiM7mizjmGn/76B/sNNNcbjwr+A1Trct1HE/5bGkt17\n"
      + "0AomxGFxAdh8mL6dWFD8h6FqV1yPztWuL91Axgf4ARGxEClSYbZAz5XBYrW0K19n\n"
      + "lQJpwyDTuCRqP1DIfJgITQDtJypoc1a0h1KNfzxuAYppPbTydGbacTafKN18kz3W\n"
      + "lv91VxY80LRdeqM+cuNrLA7uO0zeByYxEQk0p2qp3r3B3iSEryipa4drIvVLksNd\n"
      + "AgMBAAGjgegwgeUwDgYDVR0PAQH/BAQDAgGGMA8GA1UdEwEB/wQFMAMBAf8wHQYD\n"
      + "VR0OBBYEFESrA+Ak9Ut+Jfcc8W+mG3RfqFXCMIGiBgNVHSMEgZowgZeAFESrA+Ak\n"
      + "9Ut+Jfcc8W+mG3RfqFXCoWikZjBkMSYwJAYDVQQDDB1Jc3N1aW5nIENlcnRpZmlj\n"
      + "YXRlIEF1dGhvcml0eTEtMCsGA1UECgwkU3ltcGhvbnkgQ29tbXVuaWNhdGlvbiBT\n"
      + "ZXJ2aWNlcyBMTEMuMQswCQYDVQQGEwJVU4IVAKmVBTro/GCF6u842F9q7JdYVYgu\n"
      + "MA0GCSqGSIb3DQEBCwUAA4IBAQC6ic1eJFuvRiHUWU48N6gynDe8Wmmzyx5zpNcg\n"
      + "nGXjCTqWCQymEcKVNBYEeBwFWn74ImRMxbw+n6Du3Ybbp4C1eDwbQ9xRLs4zFigs\n"
      + "DRtb4Fr/XMR3IyrgLdZt5+nqXmf8rBMqT5dXsiujym9di2kwbYm1kYuEZaKzrmFT\n"
      + "y4/SmH8irbKndNFyKR+9kU785qUXrH/ZIAQc4h5TeyyMPmrda9cftQ8GgbCO8Ejb\n"
      + "26x03kZ47dkiLcro82SBnNFtoD0eh7AI5qjVdFlksSduflRptrFX9JtbGNliWVNO\n"
      + "dZH6wQyZkOmElekcEhXdCUjyyxHjKdhIQe1HI4ObrRJsTWkf\n"
      + "-----END CERTIFICATE-----";


  @Mock
  private LogMessageSource logMessage;

  @InjectMocks
  private RsaKeyUtils rsaKeyUtils;

  @Test
  public void testTrimPrivateKey() {
    String trimmedPK = rsaKeyUtils.trimPrivateKey(PRIVATE_KEY);
    assertEquals(PRIVATE_KEY_TRIMMED, trimmedPK);
  }

  @Test(expected = UnexpectedAuthException.class)
  public void testInvalidPrivateKey() {
    rsaKeyUtils.getPrivateKey("?");
  }

  @Test
  public void testTrimPublicKey() {
    String trimmedPK = rsaKeyUtils.trimPublicKey(PUBLIC_KEY);
    assertEquals(PUBLIC_KEY_TRIMMED, trimmedPK);
  }

  @Test
  public void testGetPublicKey() throws Exception {
    PublicKey publicKey = rsaKeyUtils.getPublicKey(PUBLIC_KEY_TRIMMED);
    String publicKeyAsString = rsaKeyUtils.parsePublicKey(publicKey);
    assertEquals(PUBLIC_KEY_TRIMMED, publicKeyAsString);
  }

  @Test(expected = UnexpectedAuthException.class)
  public void testInvalidPublicKey() {
    rsaKeyUtils.getPublicKey("?");
  }

  @Test
  public void testGetPublicKeyFromCertificate() {
    PublicKey publicKey = rsaKeyUtils.getPublicKeyFromCertificate(PUBLIC_CERTIFICATE);
    String publicKeyAsString = rsaKeyUtils.parsePublicKey(publicKey);
    assertEquals(PUBLIC_KEY_TRIMMED, publicKeyAsString);
  }
}

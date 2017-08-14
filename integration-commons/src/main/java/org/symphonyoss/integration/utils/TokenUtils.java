package org.symphonyoss.integration.utils;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * Used to generate random tokens.
 * Created by campidelli on 8/10/17.
 */
@Component
public class TokenUtils {

  private final SecureRandom secureRandom = new SecureRandom();

  /**
   * Generate a 64-bit security-safe String random token.
   * @return  String random token.
   */
  public String generateToken() {
    byte[] randBytes = new byte[64];
    secureRandom.nextBytes(randBytes);
    return Hex.encodeHexString(randBytes);
  }
}

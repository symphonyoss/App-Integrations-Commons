package org.symphonyoss.integration.service;

/**
 * Contract to define responsibilities to provide cryptography services.
 * Created by campidelli on 9/5/17.
 */
public interface CryptoService {

  /**
   * Encrypt the given text using the given key.
   * @param plainText Text to be encrypted.
   * @param key Key used as secret to encrypt te text.
   * @return Encrypted text.
   */
  String encrypt(String plainText, String key);

  /**
   * Decrypt the given text using the given key.
   * @param encryptedText Text to be decrypted.
   * @param key Key used as secret to decrypt te text.
   * @return Decrypted text.
   */
  String decrypt(String encryptedText, String key);
}

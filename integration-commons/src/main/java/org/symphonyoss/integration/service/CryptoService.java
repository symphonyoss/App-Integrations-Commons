package org.symphonyoss.integration.service;

import org.symphonyoss.integration.exception.CryptoException;

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
   * @throws CryptoException Thrown when the encrypt process cannot be done.
   */
  String encrypt(String plainText, String key) throws CryptoException;

  /**
   * Decrypt the given text using the given key.
   * @param encryptedText Text to be decrypted.
   * @param key Key used as secret to decrypt te text.
   * @return Decrypted text.
   * @throws CryptoException Thrown when the decrypt process cannot be done.
   */
  String decrypt(String encryptedText, String key) throws CryptoException;
}

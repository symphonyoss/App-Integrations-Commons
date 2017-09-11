package org.symphonyoss.integration.service;

import org.symphonyoss.integration.model.UserKeyManagerData;

/**
 * Contract to define responsibilities to provide key manager services.
 * Created by campidelli on 9/11/17.
 */
public interface KeyManagerService {

  /**
   * Return the Key Manager data for the given user.
   * @param userId The bot user ID.
   * @return Key manager user data.
   */
  UserKeyManagerData getBotUserAccountKeyByUser(String userId);

  /**
   * Return the Key Manager data for the given configuration ID.
   * @param userId The configuration ID.
   * @return Key manager user data.
   */
  UserKeyManagerData getBotUserAccountKeyByConfiguration(String userId);
}

package org.symphonyoss.integration.authorization;

import java.util.List;
import java.util.Map;

/**
 * Provides a contract to implement a repository service to authorization entities.
 * Created by campidelli on 1-aug-17.
 */
public interface AuthorizationRepositoryService {

  /**
   * Save the user authorization data (replaces if already exists).
   * @param configurationId Used to identify the current integration.
   * @param data Data to be saved.
   * @throws AuthorizationException Thrown in case of error.
   */
  void save(String integrationUser, String configurationId, UserAuthorizationData data) throws AuthorizationException;

  /**
   * Find a user authorization data that matches with the given url and userId
   * @param configurationId Used to identify the current integration.
   * @param url Third-party integration url.
   * @param userId User id.
   * @return Data found or null otherwise.
   * @throws AuthorizationException Thrown in case of error.
   */
  UserAuthorizationData find(String integrationUser, String configurationId, String url,
      Long userId) throws AuthorizationException;

  /**
   * Search for user data based on a map of filters.
   * @param configurationId Used to identify the current integration.
   * @param filter Map containing pairs of key/values used as filter.
   * @return List contaning the found data entries or empty.
   * @throws AuthorizationException Thrown in case of error.
   */
  List<UserAuthorizationData> search(String integrationUser, String configurationId,
      Map<String, String> filter) throws AuthorizationException;
}

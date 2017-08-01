package org.symphonyoss.integration.authorization;

import org.symphonyoss.integration.exception.RemoteApiException;

import java.util.List;
import java.util.Map;

/**
 * Created by campidelli on 8/1/17.
 */
public interface AuthorizationService {

  void save(String configurationId, UserAuthorizationData data) throws AuthorizationException;

  UserAuthorizationData find(String configurationId, String url, Long userId)
      throws AuthorizationException;

  List<UserAuthorizationData> search(String configurationId, Map<String, String> filter)
      throws AuthorizationException;
}

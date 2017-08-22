package org.symphonyoss.integration.authorization.oauth;

import com.google.api.client.auth.oauth.OAuthRsaSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.symphonyoss.integration.utils.RsaKeyUtils;

/**
 * Deals with public and private RSA keys.
 *
 * Created by campidelli on 25-jul-2017.
 */
@Component
public class OAuthRsaSignerFactory {

  @Autowired
  private RsaKeyUtils rsaKeyUtils;

  /**
   * Creates a RSA signer based on a given private key.
   * @param privateKey private key in PKCS8 syntax.
   * @return OAuthRsaSigner
   */
 public OAuthRsaSigner getOAuthRsaSigner(String privateKey) {
     OAuthRsaSigner oAuthRsaSigner = new OAuthRsaSigner();
     oAuthRsaSigner.privateKey = rsaKeyUtils.getPrivateKey(privateKey);
     return oAuthRsaSigner;
   }
}

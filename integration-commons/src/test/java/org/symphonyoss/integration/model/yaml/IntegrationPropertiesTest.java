package org.symphonyoss.integration.model.yaml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * Unit test for {@link IntegrationProperties}
 * Created by crepache on 22/06/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
@ContextConfiguration(classes = {IntegrationProperties.class})
public class IntegrationPropertiesTest {

  @Autowired
  private IntegrationProperties integrationProperties;

  @Test
  public void testIntegrationProperties() {
    validateApplication();
    validateIntegrationBridge();
    validateSigningCert();
    validateAgent();
    validateAdminUser();
    validatePod();
    validatePodSession();
    validateKeyManager();
    validateKeyManagerAuth();
    validateNumberOfApplications();
    validateLoginURL();

    assertNotNull(integrationProperties.getGlobalWhiteList());
    assertEquals("IntegrationProperties{pod=ConnectionInfo{host='nexus.symphony.com', port='443'}, agent=ConnectionInfo{host='nexus.symphony.com', port='8444'}, sessionManager=ConnectionInfo{host='nexus.symphony.com', port='8444'}, keyManager=ConnectionInfo{host='nexus.symphony.com', port='443'}}", integrationProperties.toString());
  }

  private void validateLoginURL() {
    assertEquals("https://nexus.symphony.com:443/login", integrationProperties.getLoginUrl());
  }

  private void validateNumberOfApplications() {
    assertEquals(2, integrationProperties.getApplications().size());
  }

  private void validateKeyManagerAuth() {
    assertEquals("nexus.symphony.com", integrationProperties.getKeyManagerAuth().getHost());
    assertEquals("8444", integrationProperties.getKeyManagerAuth().getPort());
    assertNull(integrationProperties.getKeyManagerAuth().getMinVersion());
    assertEquals("https://nexus.symphony.com:8444/keyauth", integrationProperties.getKeyManagerAuthUrl());
    assertEquals("ConnectionInfo{host='nexus.symphony.com', port='8444'}", integrationProperties.getKeyManagerAuth().toString());
  }

  private void validateKeyManager() {
    assertEquals("nexus.symphony.com", integrationProperties.getKeyManager().getHost());
    assertEquals("443", integrationProperties.getKeyManager().getPort());
    assertNull(integrationProperties.getKeyManager().getMinVersion());
    assertEquals("https://nexus.symphony.com:443/relay", integrationProperties.getKeyManagerUrl());
    assertEquals("ConnectionInfo{host='nexus.symphony.com', port='443'}", integrationProperties.getKeyManager().toString());
  }

  private void validatePodSession() {
    assertEquals("nexus.symphony.com", integrationProperties.getPodSessionManager().getHost());
    assertEquals("8444", integrationProperties.getPodSessionManager().getPort());
    assertNull(integrationProperties.getPodSessionManager().getMinVersion());
    assertEquals("https://nexus.symphony.com:8444/sessionauth", integrationProperties.getSessionManagerAuthUrl());
    assertEquals("ConnectionInfo{host='nexus.symphony.com', port='8444'}", integrationProperties.getPodSessionManager().toString());
  }

  private void validatePod() {
    assertEquals("nexus.symphony.com", integrationProperties.getPod().getHost());
    assertEquals("443", integrationProperties.getPod().getPort());
    assertNull(integrationProperties.getPod().getMinVersion());
    assertEquals("https://nexus.symphony.com:443/pod", integrationProperties.getPodUrl());
    assertEquals("ConnectionInfo{host='nexus.symphony.com', port='443'}", integrationProperties.getPod().toString());
    assertEquals("https://nexus.symphony.com:443", integrationProperties.getSymphonyUrl().toString());
  }

  private void validateAdminUser() {
    assertEquals("/home/centos/admin.p12", integrationProperties.getAdminUser().getKeystoreFile());
    assertEquals("changeit", integrationProperties.getAdminUser().getKeystorePassword());
  }

  private void validateAgent() {
    assertEquals("nexus.symphony.com", integrationProperties.getAgent().getHost());
    assertEquals("8444", integrationProperties.getAgent().getPort());
    assertNull(integrationProperties.getAgent().getMinVersion());
    assertEquals("https://nexus.symphony.com:8444/agent", integrationProperties.getAgentUrl());
  }

  private void validateSigningCert() {
    assertEquals("caCertChainFileTest", integrationProperties.getSigningCert().getCaCertChainFile());
    assertEquals("/home/centos/int-cert.pem", integrationProperties.getSigningCert().getCaCertFile());
    assertEquals("/home/centos/int-key.pem", integrationProperties.getSigningCert().getCaKeyFile());
    assertEquals("changeit", integrationProperties.getSigningCert().getCaKeyPassword());
  }

  private void validateIntegrationBridge() {
    assertEquals("nexus.symphony.com", integrationProperties.getIntegrationBridge().getHost());
    assertEquals(".symphony.com", integrationProperties.getIntegrationBridge().getDomain());
    assertEquals("/data/symphony/ib/certs/custom.truststore", integrationProperties.getIntegrationBridge().getTruststoreFile());
    assertEquals("jks", integrationProperties.getIntegrationBridge().getTruststoreType());
    assertEquals("changeit", integrationProperties.getIntegrationBridge().getTruststorePassword());

    assertNull(integrationProperties.getIntegrationBridge().getPort());
  }

  private void validateApplication() {
    assertNotNull(integrationProperties.getApplicationId("jira"));
    assertEquals("https://nexus.symphony.com/apps/context", integrationProperties.getApplicationUrl("jira"));

    Application application = integrationProperties.getApplication("jira");
    assertEquals("jira.icon", application.getAvatar());
    assertEquals("jira.com", application.getAvatarUrl());
    assertEquals("jira", application.getComponent());
    assertEquals("context", application.getContext());
    assertEquals("integration of jira", application.getDescription());
    assertEquals("jira", application.getId());
    assertEquals("jira", application.getName());
    assertEquals("publisher", application.getPublisher());
    assertEquals("jira.com", application.getUrl());
    assertEquals("jirauser", application.getUsername());
    assertEquals("jira.p12", application.getKeystore().getFile());
    assertEquals("pkcs12", application.getKeystore().getType());
    assertEquals("changeit", application.getKeystore().getPassword());
    assertEquals("PROVISIONED", application.getState().name());
    assertEquals("Symphony Integration for JIRA", application.getAuthorization().getApplicationName());
    assertEquals("https://nexus.symphony.com:443/integration", application.getAuthorization().getApplicationURL());

    Map<String, Object> authProperties = application.getAuthorization().getProperties();

    assertEquals("symphony_consumer", authProperties.get("consumerKey"));
    assertEquals("Symphony Consumer", authProperties.get("consumerName"));
  }

}
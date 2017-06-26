package org.symphonyoss.integration.model.yaml;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

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

    Assert.assertNotNull(integrationProperties.getGlobalWhiteList());
    Assert.assertEquals("IntegrationProperties{pod=ConnectionInfo{host='nexus.symphony.com', port='443'}, agent=ConnectionInfo{host='nexus.symphony.com', port='8444'}, sessionManager=ConnectionInfo{host='nexus.symphony.com', port='8444'}, keyManager=ConnectionInfo{host='nexus.symphony.com', port='443'}}", integrationProperties.toString());
  }

  private void validateLoginURL() {
    Assert.assertEquals("https://nexus.symphony.com:443/login", integrationProperties.getLoginUrl());
  }

  private void validateNumberOfApplications() {
    Assert.assertEquals(2, integrationProperties.getApplications().size());
  }

  private void validateKeyManagerAuth() {
    Assert.assertEquals("nexus.symphony.com", integrationProperties.getKeyManagerAuth().getHost());
    Assert.assertEquals("8444", integrationProperties.getKeyManagerAuth().getPort());
    Assert.assertNull(integrationProperties.getKeyManagerAuth().getMinVersion());
    Assert.assertEquals("https://nexus.symphony.com:8444/keyauth", integrationProperties.getKeyManagerAuthUrl());
    Assert.assertEquals("ConnectionInfo{host='nexus.symphony.com', port='8444'}", integrationProperties.getKeyManagerAuth().toString());
  }

  private void validateKeyManager() {
    Assert.assertEquals("nexus.symphony.com", integrationProperties.getKeyManager().getHost());
    Assert.assertEquals("443", integrationProperties.getKeyManager().getPort());
    Assert.assertNull(integrationProperties.getKeyManager().getMinVersion());
    Assert.assertEquals("https://nexus.symphony.com:443/relay", integrationProperties.getKeyManagerUrl());
    Assert.assertEquals("ConnectionInfo{host='nexus.symphony.com', port='443'}", integrationProperties.getKeyManager().toString());
  }

  private void validatePodSession() {
    Assert.assertEquals("nexus.symphony.com", integrationProperties.getPodSessionManager().getHost());
    Assert.assertEquals("8444", integrationProperties.getPodSessionManager().getPort());
    Assert.assertNull(integrationProperties.getPodSessionManager().getMinVersion());
    Assert.assertEquals("https://nexus.symphony.com:8444/sessionauth", integrationProperties.getSessionManagerAuthUrl());
    Assert.assertEquals("ConnectionInfo{host='nexus.symphony.com', port='8444'}", integrationProperties.getPodSessionManager().toString());
  }

  private void validatePod() {
    Assert.assertEquals("nexus.symphony.com", integrationProperties.getPod().getHost());
    Assert.assertEquals("443", integrationProperties.getPod().getPort());
    Assert.assertNull(integrationProperties.getPod().getMinVersion());
    Assert.assertEquals("https://nexus.symphony.com:443/pod", integrationProperties.getPodUrl());
    Assert.assertEquals("ConnectionInfo{host='nexus.symphony.com', port='443'}", integrationProperties.getPod().toString());
    Assert.assertEquals("https://nexus.symphony.com:443", integrationProperties.getSymphonyUrl().toString());
  }

  private void validateAdminUser() {
    Assert.assertEquals("/home/centos/admin.p12", integrationProperties.getAdminUser().getKeystoreFile());
    Assert.assertEquals("changeit", integrationProperties.getAdminUser().getKeystorePassword());
  }

  private void validateAgent() {
    Assert.assertEquals("nexus.symphony.com", integrationProperties.getAgent().getHost());
    Assert.assertEquals("8444", integrationProperties.getAgent().getPort());
    Assert.assertNull(integrationProperties.getAgent().getMinVersion());
    Assert.assertEquals("https://nexus.symphony.com:8444/agent", integrationProperties.getAgentUrl());
  }

  private void validateSigningCert() {
    Assert.assertEquals("caCertChainFileTest", integrationProperties.getSigningCert().getCaCertChainFile());
    Assert.assertEquals("/home/centos/int-cert.pem", integrationProperties.getSigningCert().getCaCertFile());
    Assert.assertEquals("/home/centos/int-key.pem", integrationProperties.getSigningCert().getCaKeyFile());
    Assert.assertEquals("changeit", integrationProperties.getSigningCert().getCaKeyPassword());
  }

  private void validateIntegrationBridge() {
    Assert.assertEquals("nexus.symphony.com", integrationProperties.getIntegrationBridge().getHost());
    Assert.assertEquals(".symphony.com", integrationProperties.getIntegrationBridge().getDomain());
    Assert.assertEquals("/data/symphony/ib/certs/custom.truststore", integrationProperties.getIntegrationBridge().getTruststoreFile());
    Assert.assertEquals("jks", integrationProperties.getIntegrationBridge().getTruststoreType());
    Assert.assertEquals("changeit", integrationProperties.getIntegrationBridge().getTruststorePassword());
  }

  private void validateApplication() {
    Assert.assertNotNull(integrationProperties.getApplicationId("jira"));
    Assert.assertEquals("https://nexus.symphony.com/apps/context", integrationProperties.getApplicationUrl("jira"));


    Application application = integrationProperties.getApplication("jira");
    Assert.assertEquals("jira.icon", application.getAvatar());
    Assert.assertEquals("jira.com", application.getAvatarUrl());
    Assert.assertEquals("jira", application.getComponent());
    Assert.assertEquals("context", application.getContext());
    Assert.assertEquals("integration of jira", application.getDescription());
    Assert.assertEquals("jira", application.getId());
    Assert.assertEquals("jira", application.getName());
    Assert.assertEquals("publisher", application.getPublisher());
    Assert.assertEquals("jira.com", application.getUrl());
    Assert.assertEquals("jirauser", application.getUsername());
    Assert.assertEquals("jira.p12", application.getKeystore().getFile());
    Assert.assertEquals("pkcs12", application.getKeystore().getType());
    Assert.assertEquals("changeit", application.getKeystore().getPassword());
    Assert.assertEquals("PROVISIONED", application.getState().name());
  }

}
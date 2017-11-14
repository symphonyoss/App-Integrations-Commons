package org.symphonyoss.integration.model.yaml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
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

  private static final Integer PUB_POD_CERT_CACHE_DURATION = 60;
  private static final Integer CONNECT_TIMEOUT = 2000;
  private static final Integer READ_TIMEOUT = 5000;

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
    validatePublicPodCertificateCacheDuration();
    validateHttpClientTimeouts();

    assertNotNull(integrationProperties.getGlobalWhiteList());
    assertEquals(
        "IntegrationProperties{pod=ConnectionInfo{host='nexus.symphony.com', port='443', "
            + "proxy='ProxyConnectionInfo{uri='null', user=null', password=null'}}, "
            + "agent=ConnectionInfo{host='nexus.symphony.com', port='8444', "
            + "proxy='ProxyConnectionInfo{uri='null', user=null', password=null'}}, "
            + "sessionManager=ConnectionInfo{host='nexus.symphony.com', port='8444', "
            + "proxy='ProxyConnectionInfo{uri='null', user=null', password=null'}}, "
            + "keyManager=ConnectionInfo{host='nexus.symphony.com', port='443', "
            + "proxy='ProxyConnectionInfo{uri='null', user=null', password=null'}}}",
        integrationProperties.toString());
  }

  private void validateLoginURL() {
    assertEquals("https://nexus.symphony.com:443/login", integrationProperties.getLoginUrl());

    ConnectionInfo connectionInfo = integrationProperties.getPod();

    integrationProperties.setPod(null);
    assertEquals(StringUtils.EMPTY, integrationProperties.getLoginUrl());

    integrationProperties.setPod(new ConnectionInfo());
    assertEquals(StringUtils.EMPTY, integrationProperties.getLoginUrl());

    ConnectionInfo mock = new ConnectionInfo();
    mock.setHost(connectionInfo.getHost());

    integrationProperties.setPod(mock);
    assertEquals("https://nexus.symphony.com:443/login", integrationProperties.getLoginUrl());

    integrationProperties.setPod(connectionInfo);
  }

  private void validateNumberOfApplications() {
    assertEquals(2, integrationProperties.getApplications().size());
  }

  private void validateKeyManagerAuth() {
    assertEquals("nexus.symphony.com", integrationProperties.getKeyManagerAuth().getHost());
    assertEquals("8444", integrationProperties.getKeyManagerAuth().getPort());
    assertNull(integrationProperties.getKeyManagerAuth().getMinVersion());
    assertEquals("https://nexus.symphony.com:8444/keyauth",
        integrationProperties.getKeyManagerAuthUrl());
    assertEquals(
        "ConnectionInfo{host='nexus.symphony.com', port='8444', "
            + "proxy='ProxyConnectionInfo{uri='null', user=null', password=null'}}",
        integrationProperties.getKeyManagerAuth().toString());
    assertNull(integrationProperties.getKeyManagerAuth().getProxy().getURI());
    assertNull(integrationProperties.getKeyManagerAuth().getProxy().getPassword());
    assertNull(integrationProperties.getKeyManagerAuth().getProxy().getUser());

    ConnectionInfo connectionInfo = integrationProperties.getKeyManagerAuth();

    integrationProperties.setKeyManagerAuth(null);
    assertEquals(StringUtils.EMPTY, integrationProperties.getKeyManagerAuthUrl());

    integrationProperties.setKeyManagerAuth(new ConnectionInfo());
    assertEquals(StringUtils.EMPTY, integrationProperties.getKeyManagerAuthUrl());

    ConnectionInfo mock = new ConnectionInfo();
    mock.setHost(connectionInfo.getHost());

    integrationProperties.setKeyManagerAuth(mock);
    assertEquals("https://nexus.symphony.com:443/keyauth",
        integrationProperties.getKeyManagerAuthUrl());

    integrationProperties.setKeyManagerAuth(connectionInfo);
  }

  private void validateKeyManager() {
    assertEquals("nexus.symphony.com", integrationProperties.getKeyManager().getHost());
    assertEquals("443", integrationProperties.getKeyManager().getPort());
    assertNull(integrationProperties.getKeyManager().getMinVersion());
    assertEquals("https://nexus.symphony.com:443/relay", integrationProperties.getKeyManagerUrl());
    assertEquals("ConnectionInfo{host='nexus.symphony.com', port='443', "
            + "proxy='ProxyConnectionInfo{uri='null', user=null', password=null'}}",
        integrationProperties.getKeyManager().toString());
    assertNull(integrationProperties.getKeyManager().getProxy().getURI());
    assertNull(integrationProperties.getKeyManager().getProxy().getPassword());
    assertNull(integrationProperties.getKeyManager().getProxy().getUser());

    ConnectionInfo connectionInfo = integrationProperties.getKeyManager();

    integrationProperties.setKeyManager(null);
    assertEquals(StringUtils.EMPTY, integrationProperties.getKeyManagerUrl());

    integrationProperties.setKeyManager(new ConnectionInfo());
    assertEquals(StringUtils.EMPTY, integrationProperties.getKeyManagerUrl());

    ConnectionInfo mock = new ConnectionInfo();
    mock.setHost(connectionInfo.getHost());

    integrationProperties.setKeyManager(mock);
    assertEquals("https://nexus.symphony.com:443/relay", integrationProperties.getKeyManagerUrl());

    integrationProperties.setKeyManager(connectionInfo);
  }

  private void validatePodSession() {
    assertEquals("nexus.symphony.com", integrationProperties.getPodSessionManager().getHost());
    assertEquals("8444", integrationProperties.getPodSessionManager().getPort());
    assertNull(integrationProperties.getPodSessionManager().getMinVersion());
    assertEquals("https://nexus.symphony.com:8444/sessionauth",
        integrationProperties.getSessionManagerAuthUrl());
    assertEquals("ConnectionInfo{host='nexus.symphony.com', port='8444', "
            + "proxy='ProxyConnectionInfo{uri='null', user=null', password=null'}}",
        integrationProperties.getPodSessionManager().toString());
    assertNull(integrationProperties.getPodSessionManager().getProxy().getURI());
    assertNull(integrationProperties.getPodSessionManager().getProxy().getPassword());
    assertNull(integrationProperties.getPodSessionManager().getProxy().getUser());

    ConnectionInfo connectionInfo = integrationProperties.getPodSessionManager();

    integrationProperties.setPodSessionManager(null);
    assertEquals(StringUtils.EMPTY, integrationProperties.getSessionManagerAuthUrl());

    integrationProperties.setPodSessionManager(new ConnectionInfo());
    assertEquals(StringUtils.EMPTY, integrationProperties.getSessionManagerAuthUrl());

    ConnectionInfo mock = new ConnectionInfo();
    mock.setHost(connectionInfo.getHost());

    integrationProperties.setPodSessionManager(mock);
    assertEquals("https://nexus.symphony.com:443/sessionauth",
        integrationProperties.getSessionManagerAuthUrl());

    integrationProperties.setPodSessionManager(connectionInfo);
  }

  private void validatePod() {
    assertEquals("nexus.symphony.com", integrationProperties.getPod().getHost());
    assertEquals("443", integrationProperties.getPod().getPort());
    assertNull(integrationProperties.getPod().getMinVersion());
    assertEquals("https://nexus.symphony.com:443/pod", integrationProperties.getPodUrl());
    assertEquals("ConnectionInfo{host='nexus.symphony.com', port='443', "
            + "proxy='ProxyConnectionInfo{uri='null', user=null', password=null'}}",
        integrationProperties.getPod().toString());
    assertEquals("https://nexus.symphony.com:443",
        integrationProperties.getSymphonyUrl().toString());
    assertNull(integrationProperties.getPod().getProxy().getURI());
    assertNull(integrationProperties.getPod().getProxy().getPassword());
    assertNull(integrationProperties.getPod().getProxy().getUser());

    ConnectionInfo connectionInfo = integrationProperties.getPod();

    integrationProperties.setPod(null);
    assertEquals(StringUtils.EMPTY, integrationProperties.getSymphonyUrl());
    assertEquals(StringUtils.EMPTY, integrationProperties.getPodUrl());

    integrationProperties.setPod(new ConnectionInfo());
    assertEquals(StringUtils.EMPTY, integrationProperties.getSymphonyUrl());
    assertEquals(StringUtils.EMPTY, integrationProperties.getPodUrl());

    ConnectionInfo mock = new ConnectionInfo();
    mock.setHost(connectionInfo.getHost());

    integrationProperties.setPod(mock);
    assertEquals("https://nexus.symphony.com:443", integrationProperties.getSymphonyUrl());
    assertEquals("https://nexus.symphony.com:443/pod", integrationProperties.getPodUrl());

    integrationProperties.setPod(connectionInfo);
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

    ConnectionInfo connectionInfo = integrationProperties.getAgent();

    integrationProperties.setAgent(null);
    assertEquals(StringUtils.EMPTY, integrationProperties.getAgentUrl());

    integrationProperties.setAgent(new ConnectionInfo());
    assertEquals(StringUtils.EMPTY, integrationProperties.getAgentUrl());

    ConnectionInfo mock = new ConnectionInfo();
    mock.setHost(connectionInfo.getHost());

    integrationProperties.setAgent(mock);
    assertEquals("https://nexus.symphony.com:443/agent", integrationProperties.getAgentUrl());

    integrationProperties.setAgent(connectionInfo);
  }

  private void validateSigningCert() {
    assertEquals("caCertChainFileTest",
        integrationProperties.getSigningCert().getCaCertChainFile());
    assertEquals("/home/centos/int-cert.pem",
        integrationProperties.getSigningCert().getCaCertFile());
    assertEquals("/home/centos/int-key.pem", integrationProperties.getSigningCert().getCaKeyFile());
    assertEquals("changeit", integrationProperties.getSigningCert().getCaKeyPassword());
  }

  private void validateIntegrationBridge() {
    assertEquals("nexus.symphony.com", integrationProperties.getIntegrationBridge().getHost());
    assertEquals(".symphony.com", integrationProperties.getIntegrationBridge().getDomain());
    assertEquals("/data/symphony/ib/certs/custom.truststore",
        integrationProperties.getIntegrationBridge().getTruststoreFile());
    assertEquals("jks", integrationProperties.getIntegrationBridge().getTruststoreType());
    assertEquals("changeit", integrationProperties.getIntegrationBridge().getTruststorePassword());

    assertEquals("8080", integrationProperties.getIntegrationBridge().getPort());

    assertEquals("https://nexus.symphony.com:8080/integration",
        integrationProperties.getIntegrationBridgeUrl());

    IntegrationBridge integrationBridge = integrationProperties.getIntegrationBridge();

    integrationProperties.setIntegrationBridge(null);
    assertEquals(StringUtils.EMPTY, integrationProperties.getIntegrationBridgeUrl());
    assertTrue(integrationProperties.getGlobalWhiteList().isEmpty());

    integrationProperties.setIntegrationBridge(new IntegrationBridge());
    assertEquals(StringUtils.EMPTY, integrationProperties.getIntegrationBridgeUrl());

    integrationProperties.setIntegrationBridge(integrationBridge);
  }

  private void validateApplication() {
    assertNull(integrationProperties.getApplication("test"));
    assertEquals("test", integrationProperties.getApplicationId("test"));

    assertNotNull(integrationProperties.getApplicationId("jira"));
    assertEquals("https://nexus.symphony.com:8080/apps/context", integrationProperties
        .getApplicationUrl("jira"));
    assertEquals(StringUtils.EMPTY, integrationProperties.getApplicationUrl("test"));

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
    assertEquals("jira_app.p12", application.getAppKeystore().getFile());
    assertEquals("pkcs12", application.getAppKeystore().getType());
    assertEquals("testapp", application.getAppKeystore().getPassword());
    assertEquals("PROVISIONED", application.getState().name());
    assertEquals("Symphony Integration for JIRA",
        application.getAuthorization().getApplicationName());
    assertEquals("https://nexus.symphony.com:8080/integration",
        application.getAuthorization().getApplicationURL());

    Map<String, Object> authProperties = application.getAuthorization().getProperties();

    assertEquals("symphony_consumer", authProperties.get("consumerKey"));
    assertEquals("Symphony Consumer", authProperties.get("consumerName"));

    IntegrationBridge integrationBridge = integrationProperties.getIntegrationBridge();

    integrationProperties.setIntegrationBridge(null);
    assertEquals(StringUtils.EMPTY, integrationProperties.getApplicationUrl("jira"));

    integrationProperties.setIntegrationBridge(integrationBridge);
  }

  private void validatePublicPodCertificateCacheDuration() {
    assertEquals(PUB_POD_CERT_CACHE_DURATION,
        integrationProperties.getPublicPodCertificateCacheDuration());
  }

  private void validateHttpClientTimeouts() {
    assertEquals(CONNECT_TIMEOUT, integrationProperties.getHttpClientConfig().getConnectTimeout());
    assertEquals(READ_TIMEOUT, integrationProperties.getHttpClientConfig().getReadTimeout());
  }
}
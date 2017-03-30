package org.symphonyoss.integration.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.symphonyoss.integration.api.client.json.JsonEntitySerializer;
import org.symphonyoss.integration.api.client.metrics.ApiMetricsController;
import org.symphonyoss.integration.api.client.metrics.MetricsHttpApiClient;
import org.symphonyoss.integration.api.client.trace.TraceLoggingApiClient;
import org.symphonyoss.integration.authentication.AuthenticationProxy;
import org.symphonyoss.integration.exception.RemoteApiException;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;

/**
 * Low-level HTTP client to query external services.
 * Created by rsanchez on 01/03/17.
 */
public abstract class SymphonyApiClient implements HttpApiClient {

  @Autowired
  private AuthenticationProxy proxy;

  @Autowired
  private ApiMetricsController metricsController;

  private HttpApiClient client;

  private String serviceName;

  private EntitySerializer serializer;

  public SymphonyApiClient(String serviceName) {
    this.serviceName = serviceName;

    // Default serializer
    this.serializer = new JsonEntitySerializer();
  }

  @PostConstruct
  public void init() {
    String url = getBasePath();
    buildHttpClient(url);
  }

  protected abstract String getBasePath();

  /**
   * Builds the HTTP client and set the base path.
   * This HTTP client should implement connectivity exception handling, re-authentication, trace
   * logging, and metric counters.
   * @param basePath Base path
   */
  private void buildHttpClient(String basePath) {
    AuthenticationProxyApiClient simpleClient = new AuthenticationProxyApiClient(serializer, proxy);
    simpleClient.setBasePath(basePath);

    ConnectivityApiClientDecorator connectivityApiClient =
        new ConnectivityApiClientDecorator(serviceName, simpleClient);

    ReAuthenticationApiClient reAuthApiClient =
        new ReAuthenticationApiClient(proxy, connectivityApiClient);

    TraceLoggingApiClient traceLoggingApiClient = new TraceLoggingApiClient(reAuthApiClient);

    this.client = new MetricsHttpApiClient(metricsController, traceLoggingApiClient);
  }

  @Override
  public String escapeString(String str) {
    return client.escapeString(str);
  }

  @Override
  public <T> T doGet(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Class<T> returnType) throws RemoteApiException {
    return client.doGet(path, headerParams, queryParams, returnType);
  }

  @Override
  public <T> T doPost(String path, Map<String, String> headerParams,
      Map<String, String> queryParams, Object payload, Class<T> returnType)
      throws RemoteApiException {
    return client.doPost(path, headerParams, queryParams, payload, returnType);
  }

  @Override
  public <T> T doPut(String path, Map<String, String> headerParams, Map<String, String> queryParams,
      Object payload, Class<T> returnType) throws RemoteApiException {
    return client.doPut(path, headerParams, queryParams, payload, returnType);
  }

  @Override
  public <T> T doDelete(String path, Map<String, String> headerParams,
      Map<String, String> queryParams, Class<T> returnType) throws RemoteApiException {
    return client.doDelete(path, headerParams, queryParams, returnType);
  }

  @Override
  public Client getClientForContext(Map<String, String> queryParams,
      Map<String, String> headerParams) {
    return client.getClientForContext(queryParams, headerParams);
  }

  public HttpApiClient getClient() {
    return client;
  }

  @Override
  public void setEntitySerializer(EntitySerializer serializer) {
    this.serializer = serializer;
    this.client.setEntitySerializer(serializer);
  }

}

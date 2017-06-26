package org.symphonyoss.integration.api.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import sun.net.www.http.HttpClient;

/**
 * Unit test for {@link SymphonyApiClient}
 * Created by campidelli on 6/19/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SymphonyApiClientTest {

  private static final String SERVICE_NAME = "serviceName";
  private static final String BASE_PATH = "http://www.mocking-symphony.com";

  private SymphonyApiClient apiClient;

  @Before
  public void init() {
    apiClient = new SymphonyApiClient(SERVICE_NAME) {
      @Override
      protected String getBasePath() {
        return BASE_PATH;
      }
    };
  }

  @Test
  public void testInit() {
    HttpApiClient client = apiClient.getClient();
    assertNull(client);

    apiClient.init();

    client = apiClient.getClient();
    assertNotNull(client);
  }
}

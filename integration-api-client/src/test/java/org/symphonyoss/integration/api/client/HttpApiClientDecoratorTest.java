package org.symphonyoss.integration.api.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.exception.RemoteApiException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Unit test for {@link HttpApiClientDecorator}
 * Created by campidelli on 6/19/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class HttpApiClientDecoratorTest {

  private static final Client CLIENT = ClientBuilder.newClient();

  @InjectMocks
  private HttpApiClientDecorator decorator;

  @Mock
  private HttpApiClient apiClient;

  @Test
  public void testEscapeString() {
    doReturn(StringUtils.EMPTY).when(apiClient).escapeString(anyString());
    String result = decorator.escapeString(StringUtils.EMPTY);
    assertEquals(StringUtils.EMPTY, result);
  }

  @Test
  public void testDoGet() throws RemoteApiException {
    doReturn(CLIENT).when(apiClient).doGet(null, null, null, Client.class);
    Client result = decorator.doGet(null, null, null, Client.class);
    assertEquals(CLIENT, result);
  }

  @Test
  public void testDoPost() throws RemoteApiException {
    doReturn(CLIENT).when(apiClient).doPost(null, null, null, null, Client.class);
    Client result = decorator.doPost(null, null, null, null, Client.class);
    assertEquals(CLIENT, result);
  }

  @Test
  public void testDoPut() throws RemoteApiException {
    doReturn(CLIENT).when(apiClient).doPut(null, null, null, null, Client.class);
    Client result = decorator.doPut(null, null, null, null, Client.class);
    assertEquals(CLIENT, result);
  }

  @Test
  public void testDoDelete() throws RemoteApiException {
    doReturn(CLIENT).when(apiClient).doDelete(null, null, null, Client.class);
    Client result = decorator.doDelete(null, null, null, Client.class);
    assertEquals(CLIENT, result);
  }

  @Test
  public void testGetClientForContext() {
    doReturn(CLIENT).when(apiClient).getClientForContext(null, null);
    Client result = decorator.getClientForContext(null, null);
    assertEquals(CLIENT, result);
  }
}

package org.symphonyoss.integration.api.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.exception.RemoteApiException;
import org.symphonyoss.integration.model.message.Message;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * Unit test for {@link HttpApiClientDecorator}
 * Created by campidelli on 6/19/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class HttpApiClientDecoratorTest {

  private static final Client CLIENT = ClientBuilder.newClient();

  private static String PATH = "symphony.com/path/v1";

  private static final Map<String, String> headerParams = new HashMap<>();

  private static final Map<String, String> queryParams = new HashMap<>();

  private static final String CONTENT_TYPE_HEADER_PARAM = "content-type";

  private static final String SESSION_TOKEN = "sessionToken";

  private static final String MOCK_SESSION = "37ee62570a52804c1fb388a49f30df59fa1513b0368871a031c6de1036db";

  private static Message message = new Message();

  @InjectMocks
  private HttpApiClientDecorator decorator;

  @Mock
  private HttpApiClient apiClient;

  @Before
  public void init() {
    headerParams.put(CONTENT_TYPE_HEADER_PARAM, MediaType.APPLICATION_JSON);
    queryParams.put(SESSION_TOKEN, MOCK_SESSION);

    message.setFormat(Message.FormatEnum.MESSAGEML);
    message.setMessage("Test Message");
  }

  @Test
  public void testEscapeString() {
    doReturn(StringUtils.EMPTY).when(apiClient).escapeString(anyString());
    String result = decorator.escapeString(StringUtils.EMPTY);
    assertEquals(StringUtils.EMPTY, result);
  }

  @Test
  public void testDoGet() throws RemoteApiException {
    doReturn(CLIENT).when(apiClient).doGet(PATH, headerParams, queryParams, Client.class);
    Client result = decorator.doGet(PATH, headerParams, queryParams, Client.class);
    assertEquals(CLIENT, result);
  }

  @Test
  public void testDoPost() throws RemoteApiException {
    doReturn(CLIENT).when(apiClient).doPost(PATH, headerParams, queryParams, message, Client.class);
    Client result = decorator.doPost(PATH, headerParams, queryParams, message, Client.class);
    assertEquals(CLIENT, result);
  }

  @Test
  public void testDoPut() throws RemoteApiException {
    doReturn(CLIENT).when(apiClient).doPut(PATH, headerParams, queryParams, message, Client.class);
    Client result = decorator.doPut(PATH, headerParams, queryParams, message, Client.class);
    assertEquals(CLIENT, result);
  }

  @Test
  public void testDoDelete() throws RemoteApiException {
    doReturn(CLIENT).when(apiClient).doDelete(PATH, headerParams, queryParams, Client.class);
    Client result = decorator.doDelete(PATH, headerParams, queryParams, Client.class);
    assertEquals(CLIENT, result);
  }

  @Test
  public void testGetClientForContext() {
    doReturn(CLIENT).when(apiClient).getClientForContext(queryParams, headerParams);
    Client result = decorator.getClientForContext(queryParams, headerParams);
    assertEquals(CLIENT, result);
  }
}
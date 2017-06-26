package org.symphonyoss.integration.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.api.client.form.MultiPartEntitySerializer;
import org.symphonyoss.integration.api.client.json.JsonEntitySerializer;
import org.symphonyoss.integration.exception.RemoteApiException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Unit test for {@link SimpleHttpApiClient}
 * Created by campidelli on 6/19/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleHttpApiClientTest {

  private static final String SAMPLE_KEY = "sampleKey";

  private static final String SAMPLE_VALUE = "sampleValue";

  private static final String MOCK_PATH = "/v1/mock/path";

  private static final String BASE_PATH = "http://www.mocking-symphony.com";

  @Mock
  private Client mockClient;

  @Mock
  private WebTarget mockWebTarget;

  @Mock
  private Invocation.Builder invocationBuilder;

  @Mock
  private Response response;

  @Mock
  private Response.StatusType responseStatus;

  private SimpleHttpApiClient client;

  @Before
  public void init() {
    client = new SimpleHttpApiClient(new JsonEntitySerializer()) {
      @Override
      public Client getClientForContext(Map<String, String> queryParams,
          Map<String, String> headerParams) {
        return mockClient;
      }
    };

    client.setBasePath(BASE_PATH);

    doReturn(mockWebTarget).when(mockClient).target(anyString());
    doReturn(mockWebTarget).when(mockWebTarget).path(anyString());
    doReturn(mockWebTarget).when(mockWebTarget).queryParam(anyString(), anyString());
    doReturn(invocationBuilder).when(mockWebTarget).request();
    doReturn(invocationBuilder).when(invocationBuilder).header(anyString(), anyString());
    doReturn(response).when(invocationBuilder).get();
    doReturn(response).when(invocationBuilder).post(any(Entity.class));
    doReturn(response).when(invocationBuilder).put(any(Entity.class));
    doReturn(response).when(invocationBuilder).delete();
    doReturn(responseStatus).when(response).getStatusInfo();
  }

  @Test
  public void testSetEntitySerializer() {
    MultiPartEntitySerializer serializer = new MultiPartEntitySerializer();
    client.setEntitySerializer(serializer);
    EntitySerializer newES = (EntitySerializer) Whitebox.getInternalState(client, "serializer");
    assertEquals(serializer, newES);
  }

  @Test
  public void testSetBasePath() {
    client.setBasePath(BASE_PATH);
    String result = (String) Whitebox.getInternalState(client, "basePath");
    assertEquals(BASE_PATH, result);
  }

  @Test
  public void testEscapeString() {
    String original = "expected string";
    String expected = "expected%20string";
    String result = client.escapeString(original);
    assertEquals(expected, result);
  }

  @Test
  public void testDoGetNullContent() throws RemoteApiException {
    doReturn(Response.Status.NO_CONTENT.getStatusCode()).when(response).getStatus();

    String result = client.doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), String.class);

    assertNull(result);
  }

  @Test
  public void testDoGetNullReturnType() throws RemoteApiException {
    doReturn(Response.Status.Family.SUCCESSFUL).when(responseStatus).getFamily();

    String result = client.doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), null);

    assertNull(result);
  }

  @Test(expected = RemoteApiException.class)
  public void testDoGetFailedCallingAPI() throws RemoteApiException {
    doReturn(Response.Status.Family.OTHER).when(responseStatus).getFamily();
    doReturn(true).when(response).hasEntity();

    client.doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), null);
  }

  @Test(expected = RemoteApiException.class)
  public void testDoGetRuntimeException() throws RemoteApiException {
    doReturn(Response.Status.Family.OTHER).when(responseStatus).getFamily();
    doReturn(true).when(response).hasEntity();
    doThrow(RuntimeException.class).when(response).readEntity(String.class);

    client.doGet(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), null);
  }

  @Test
  public void testDoGet() throws RemoteApiException {
    Map<String, String> headerParams = new HashMap<>();
    headerParams.put(SAMPLE_KEY, SAMPLE_VALUE);

    Map<String, String> queryParams = new HashMap<>();
    queryParams.put(SAMPLE_KEY, SAMPLE_VALUE);

    prepareSucessfulResponse();

    String result = client.doGet(MOCK_PATH, headerParams, queryParams, String.class);

    assertEquals(SAMPLE_VALUE, result);
  }

  @Test
  public void testDoPost() throws RemoteApiException {
    prepareSucessfulResponse();

    String result = client.doPost(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), SAMPLE_VALUE, String.class);

    assertEquals(SAMPLE_VALUE, result);
  }

  @Test
  public void testDoPut() throws RemoteApiException {
    prepareSucessfulResponse();

    String result = client.doPut(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), SAMPLE_VALUE, String.class);

    assertEquals(SAMPLE_VALUE, result);
  }

  @Test
  public void testDoDelete() throws RemoteApiException {
    prepareSucessfulResponse();

    String result = client.doDelete(MOCK_PATH, Collections.<String, String>emptyMap(),
        Collections.<String, String>emptyMap(), String.class);

    assertEquals(SAMPLE_VALUE, result);
  }

  private void prepareSucessfulResponse() {
    doReturn(Response.Status.Family.SUCCESSFUL).when(responseStatus).getFamily();
    doReturn(true).when(response).hasEntity();
    doReturn(SAMPLE_VALUE).when(response).readEntity(String.class);
  }
}

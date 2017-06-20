package org.symphonyoss.integration.webhook;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

/**
 * Unit tests to validate {@link WebHookPayload}
 * Created by campidelli on 6/19/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class WebHookPayloadTest {

  private static final String CONTENT_TYPE = "content-type";

  @Test
  public void testGetContentTypeDefault() {
    WebHookPayload whp = getWebHookPayload();

    assertEquals(MediaType.WILDCARD_TYPE, whp.getContentType());
  }

  @Test
  public void testGetContentTypeDefaultThroughInvalid() {
    WebHookPayload whp = getWebHookPayload();

    String contentType = "invalid";
    whp.getHeaders().put(CONTENT_TYPE, contentType);
    assertEquals(MediaType.WILDCARD_TYPE, whp.getContentType());
  }

  @Test
  public void testGetContentType() {
    WebHookPayload whp = getWebHookPayload();

    whp.getHeaders().put(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    assertEquals(MediaType.APPLICATION_JSON_TYPE, whp.getContentType());
  }

  private WebHookPayload getWebHookPayload() {
    Map<String, String> parameters = new HashMap<>();
    Map<String, String> headers = new HashMap<>();
    return new WebHookPayload(parameters, headers, null);
  }
}

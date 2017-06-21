package org.symphonyoss.integration.json;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.symphonyoss.integration.model.config.IntegrationInstance;
import org.symphonyoss.integration.parser.SafeString;

import java.io.IOException;
import java.io.InputStream;

/**
 * Unit test for {@link JsonUtils}
 * Created by crepache on 21/06/17.
 */
public class JsonUtilsTest {

  private static final String VALUE_1 = "value1";

  private static final String VALUE_2 = "value2";

  private InputStream inputStream;

  @Before
  public void init() {
    inputStream = getClass().getClassLoader().getResourceAsStream("JSONUtils.json");
  }

  @Test
  public void testReadJsonFromString() throws IOException {
    String jsonTest = JsonUtils.writeValueAsString(JsonUtils.readTree(inputStream));
    JsonNode jsonNode = JsonUtils.readTree(jsonTest);

    Assert.assertEquals(VALUE_1, jsonNode.path("field1").asText());
    Assert.assertEquals(VALUE_2, jsonNode.path("field2").asText());
  }

  @Test
  public void testReadJsonFromInputStream() throws IOException {
    JsonNode jsonNode = JsonUtils.readTree(inputStream);

    Assert.assertEquals(VALUE_1, jsonNode.path("field1").asText());
    Assert.assertEquals(VALUE_2, jsonNode.path("field2").asText());
  }

}
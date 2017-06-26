package org.symphonyoss.integration.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link SimpleFileUtils}.
 * Created by crepache on 23/06/17.
 */
public class SimpleFileUtilsTest {

    private static String jsonFile = "SimpleFile/SimpleFileUtilsJSON.json";

    private static String messageMLFile = "SimpleFile/SimpleFileUtilsML.xml";

    @Test
    public void testJsonFile() throws IOException{
        JsonNode node = SimpleFileUtils.readJsonFromFile(jsonFile);
        String expectedValue1 = "\"foo\"";
        String expectedValue2 = "\"bar\"";

        assertEquals(expectedValue1,node.get("Value1").toString());
        assertEquals(expectedValue2,node.get("Value2").toString());
    }

    @Test
    public void testMessageMLFile() throws IOException{
        String result = SimpleFileUtils.readMessageMLFile(messageMLFile);
        String expected = "<messageML><p>This is a simple messageML example</p></messageML>";

        assertEquals(expected,result);
    }

}

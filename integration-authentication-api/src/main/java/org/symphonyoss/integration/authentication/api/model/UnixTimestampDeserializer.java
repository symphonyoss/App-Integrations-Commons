package org.symphonyoss.integration.authentication.api.model;

/**
 * Created by campidelli on 8/18/17.
 */
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * As the JWT pattern for datetime is SECONDS and Jackson Parser uses MILLISECONDS, we have to
 * add '000' at the text end.
 *
 * Created by campidelli on 15/08/17.
 */
public class UnixTimestampDeserializer extends JsonDeserializer<Date> {

  @Override
  public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    String timestamp = jp.getText().trim();
    try {
      return new Date(Long.valueOf(timestamp + "000"));
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
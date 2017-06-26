package org.symphonyoss.integration.model.stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link Stream}
 * Created by crepache on 20/06/17.
 */
public class StreamTest {

  public static final String ID = "123456";

  @Test
  public void testStream() {
    Stream stream = new Stream();
    stream.setId(ID);

    Assert.assertEquals(ID, stream.getId());
  }

}

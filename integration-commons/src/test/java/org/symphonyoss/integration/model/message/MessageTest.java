package org.symphonyoss.integration.model.message;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link Message}
 * Created by crepache on 20/06/17.
 */
public class MessageTest {

  private static final Long TIMESTAMP = 1476109880000L;
  private static final String TEST_MESSAGE = "Mock test message";
  private static final String TEST_DATA = "Test data";
  private static final String EXPECTED_MESSAGE_JSON =
      "Message{timestamp=1476109880000, message='Mock test message', format=MESSAGEML, data='Test "
          + "data', version=V1}";

  @Test
  public void testMessage() {
    Message message = new Message();
    message.setTimestamp(TIMESTAMP);
    message.setMessage(TEST_MESSAGE);
    message.setFormat(Message.FormatEnum.MESSAGEML);
    message.setData(TEST_DATA);
    message.setVersion(MessageMLVersion.V1);

    Assert.assertEquals(TIMESTAMP, message.getTimestamp());
    Assert.assertEquals(TEST_MESSAGE, message.getMessage());
    Assert.assertEquals(Message.FormatEnum.MESSAGEML, message.getFormat());
    Assert.assertEquals(TEST_DATA, message.getData());
    Assert.assertEquals(MessageMLVersion.V1, message.getVersion());

    Assert.assertEquals(
        EXPECTED_MESSAGE_JSON, message.toString());
  }

}

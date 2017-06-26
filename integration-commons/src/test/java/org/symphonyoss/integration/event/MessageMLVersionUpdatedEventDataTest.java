package org.symphonyoss.integration.event;

import org.junit.Assert;
import org.junit.Test;
import org.symphonyoss.integration.model.message.MessageMLVersion;

/**
 * Unit test for {@link MessageMLVersionUpdatedEventData}
 * Created by crepache on 21/06/17.
 */
public class MessageMLVersionUpdatedEventDataTest {

  @Test
  public void testMessageMLVersionUpdatedEventData() {
    MessageMLVersionUpdatedEventData messageMLVersionUpdatedEventData = new MessageMLVersionUpdatedEventData(
        MessageMLVersion.V1);

    Assert.assertEquals(MessageMLVersion.V1, messageMLVersionUpdatedEventData.getVersion());
  }

}

package org.symphonyoss.integration.logging;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MessageUtils}
 * Created by hamitay on 8/18/17.
 */
public class MessageUtilsTest {

  private static final String BUNDLE_FILENAME = "integration-commons-log-messages";

  private static final String RESOURCE_NAME = "integration.commons.message.utils.test";

  private static final String EXPECTED_SUCCESS_MESSAGE = "This is a test message.";

  private static final String EXPECTED_FAILURE_MESSAGE = "Message not found for resource .";

  @Test
  public void testMessageUtils() {
    MessageUtils messageUtils = new MessageUtils(BUNDLE_FILENAME);
    String successResult = messageUtils.getMessage(RESOURCE_NAME, "");
    String failedResult = messageUtils.getMessage("","");

    Assert.assertEquals(EXPECTED_SUCCESS_MESSAGE, successResult);
    Assert.assertEquals(EXPECTED_FAILURE_MESSAGE, failedResult);
  }

}
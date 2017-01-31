package org.symphonyoss.integration.logging;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for {@link LogMessageSource}

 * Created by evandro on 31/01/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
@ContextConfiguration(classes = {LogMessageSource.class})
public class LogMessageSourceTest {

  @Autowired
  LogMessageSource logMessage;

  @Test
  public void testCommonsMessage() {

    assertEquals("Unable to load log messages from log-messages.properties files. "
            + "Integration Bridge logging will not display proper content.",
        logMessage.getMessage("integration.commons.unable.to.load.log.messages"));
  }

  @Test
  public void testMyOwnMessage() {

    assertEquals("This is a sample message. Data: my data",
        logMessage.getMessage("my.log.message.for.test", "my data"));
  }

  @Test
  public void testUnknownMessageId() {

    assertEquals("Unable to record log information. Log message not found.",
        logMessage.getMessage("my.wrong.id", "my data"));
  }

}

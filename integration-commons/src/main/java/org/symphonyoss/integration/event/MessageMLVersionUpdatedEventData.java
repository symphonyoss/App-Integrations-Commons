package org.symphonyoss.integration.event;

import org.symphonyoss.integration.model.message.MessageMLVersion;

/**
 * MessageML version updated event object.
 *
 * Created by rsanchez on 21/03/17.
 */
public class MessageMLVersionUpdatedEventData {

  private MessageMLVersion version;

  public MessageMLVersionUpdatedEventData(MessageMLVersion version) {
    this.version = version;
  }

  public MessageMLVersion getVersion() {
    return version;
  }
}

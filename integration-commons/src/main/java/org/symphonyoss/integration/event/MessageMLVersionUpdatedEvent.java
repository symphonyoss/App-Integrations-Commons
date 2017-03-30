package org.symphonyoss.integration.event;

import org.symphonyoss.integration.model.message.MessageMLVersion;

/**
 * MessageML version updated event object.
 *
 * Created by rsanchez on 21/03/17.
 */
public class MessageMLVersionUpdatedEvent {

  private MessageMLVersion version;

  public MessageMLVersionUpdatedEvent(MessageMLVersion version) {
    this.version = version;
  }

  public MessageMLVersion getVersion() {
    return version;
  }
}

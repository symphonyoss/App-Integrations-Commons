package org.symphonyoss.integration.event;

import org.symphonyoss.integration.model.message.MessageMLVersion;

/**
 * MessageML version updated event object.
 *
 * This class holds the messageML version supported by the external services.
 *
 * This event might be used to notify the parsers which messageML version is available to be used.
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

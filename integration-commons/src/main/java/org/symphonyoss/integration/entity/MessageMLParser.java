/**
 * Copyright 2016-2017 Symphony Integrations - Symphony LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.symphonyoss.integration.entity;

import com.symphony.messageml.InvalidInputException;
import com.symphony.messageml.MessageMLContext;
import com.symphony.messageml.MessageMLVersion;
import com.symphony.messageml.ProcessingException;

import org.springframework.stereotype.Component;
import org.symphonyoss.integration.parser.ParserUtils;

import java.io.StringReader;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Class responsible to parse an EntityML to {@link MessageML}
 *
 * As a limitation, this class is able to handle a single entity within the messageMl document.
 *
 * Created by rsanchez on 31/08/16.
 */
@Component
public class MessageMLParser {

  private MessageMLContext messageMLContext;

  @PostConstruct
  public void init() {
    this.messageMLContext = new MessageMLContext(MessageMLVersion.MessageML_V2, true, true);
  }


  /**
   * Validates the body of the messageML using {@link com.symphony.messageml.MessageML} to parse.
   * @param body MessageML content
   * @return String messageML
   * @throws MessageMLParseException
   */
  public String validate(String body) throws MessageMLParseException {
    try {
      // Parses the messageML to make sure it is properly formed.
      com.symphony.messageml.MessageML.parseMessageML(messageMLContext, body);
      // Body is returned as provided in the input
      return body;
    } catch (InvalidInputException | ProcessingException e) {
      throw new MessageMLParseException(e.getMessage(), e);
    }
  }

  /**
   * Parses an String XML to an {@link MessageML}
   * @param xml a messageML document.
   * @return a MessageML object for the provided MessageML document.
   * @throws JAXBException
   */
  public static MessageML parse(String xml) throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(MessageML.class);
    Unmarshaller unmarshaller;
    unmarshaller = jaxbContext.createUnmarshaller();

    StringReader reader = new StringReader(xml);

    String presentation = ParserUtils.getPresentationMLContent(xml);
    MessageML messageML = (MessageML) unmarshaller.unmarshal(reader);

    if (messageML.getEntity() == null) {
      throw new MessageMLParseException("Invalid message format. At least one entity is needed to parse.");
    }

    messageML.getEntity().setPresentationML(presentation);
    return messageML;
  }


}

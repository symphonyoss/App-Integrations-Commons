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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.exception.EntityXMLGeneratorException;

import javax.xml.bind.JAXBException;

/**
 * Unit tests for {@link MessageMLParserTest}
 *
 * Created by cmarcondes on 11/2/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageMLParserTest {

  @Test
  public void testParse() throws JAXBException, EntityXMLGeneratorException {
    String xml = "<messageML>"
        + "<entity type=\"com.symphony.integration.sfdc.event.opportunityNotification\" "
        + "version=\"1.0\">"
        + "<presentationML>teste1</presentationML>"
        + "<attribute name=\"username\" type=\"org.symphonyoss.string\" value=\"test\"/>"
        + "</entity>"
        + "</messageML>";
    MessageML messageML = MessageMLParser.parse(xml);
    String result = EntityBuilder.forEntity(messageML.getEntity()).generateXML();

    Assert.assertEquals(xml, "<messageML>" + result + "</messageML>");
  }

  @Test
  public void testPresentationMLWithMarkups() throws JAXBException, EntityXMLGeneratorException {
    String xml = "<messageML>"
        + "<entity type=\"com.symphony.integration.sfdc.event.opportunityNotification\" "
        + "version=\"1.0\">"
        + "<presentationML>teste1<br/>teste2<br/><p>teste3</p></presentationML>"
        + "<attribute name=\"username\" type=\"org.symphonyoss.string\" value=\"test\"/>"
        + "</entity>"
        + "</messageML>";

    MessageML messageML = MessageMLParser.parse(xml);
    String result = EntityBuilder.forEntity(messageML.getEntity()).generateXML();

    Assert.assertEquals(xml, "<messageML>" + result + "</messageML>");
  }

  @Test
  public void testParseNestedEntity() throws JAXBException, EntityXMLGeneratorException {
    String xml = "<messageML>"
        + "<entity type=\"com.symphony.integration.sfdc.event.opportunityNotification\" "
        + "version=\"1.0\">"
        + "<presentationML>teste1</presentationML>"
        + "<attribute name=\"username\" type=\"org.symphonyoss.string\" value=\"test\"/>"
        + "<entity type=\"com.symphony.integration.sfdc.account\" version=\"1.0\">"
        + "<attribute name=\"name\" type=\"org.symphonyoss.string\" value=\"Wells Fargo\"/>"
        + "<entity name=\"totalAmount\" type=\"com.symphony.integration.sfdc.amount\" "
        + "version=\"1.0\">"
        + "<attribute name=\"currency\" type=\"org.symphonyoss.fin.ccy\" value=\"USD\"/>"
        + "</entity>"
        + "</entity>"
        + "</entity>"
        + "</messageML>";
    MessageML messageML = MessageMLParser.parse(xml);
    String result = EntityBuilder.forEntity(messageML.getEntity()).generateXML();

    Assert.assertEquals(xml, "<messageML>" + result + "</messageML>");
  }

  @Test(expected = MessageMLParseException.class)
  public void testParseWithoutEntity() throws JAXBException, EntityXMLGeneratorException {
    String xml = "<messageML>simple message</messageML>";
    MessageMLParser.parse(xml);
  }

}

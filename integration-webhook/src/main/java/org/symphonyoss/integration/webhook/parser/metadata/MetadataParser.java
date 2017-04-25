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

package org.symphonyoss.integration.webhook.parser.metadata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.symphonyoss.integration.json.JsonUtils;
import org.symphonyoss.integration.model.message.Message;
import org.symphonyoss.integration.model.message.MessageMLVersion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Abstract parser class responsible to read an XML input file that contains metadata objects to
 * be used to create an Entity JSON and also read MessageML template file.
 *
 * This class should be extended by all the parsers that produces messageML v2.
 *
 * Created by rsanchez on 29/03/17.
 */
public abstract class MetadataParser {

  private static final Logger LOGGER = LoggerFactory.getLogger(MetadataParser.class);

  private static final String BASE_METADATA_PATH = "metadata/";

  private static final String BASE_TEMPLATE_PATH = "templates/";

  private Unmarshaller unmarshaller;

  private Metadata metadata;

  private String messageMLTemplate;

  /**
   * Initializes the JAXB context and unmarshaller object.
   * @throws IllegalStateException Failure to initialize JAXB context
   */
  public MetadataParser() {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(Metadata.class);
      this.unmarshaller = jaxbContext.createUnmarshaller();
    } catch (JAXBException e) {
      throw new IllegalStateException("Fail to initialize JAXB context", e);
    }
  }

  /**
   * Callback method to read the metadata objects and MessageML template only once.
   */
  @PostConstruct
  public void init() {
    readMetadataFile();
    readTemplateFile();
  }

  /**
   * Read XML document which contains metadata objects.
   *
   * This document must follow the specification from {@link Metadata}
   */
  private void readMetadataFile() {
    try {
      String fileLocation = BASE_METADATA_PATH + getMetadataFile();

      InputStream resource = getClass().getClassLoader().getResourceAsStream(fileLocation);

      if (resource == null) {
        LOGGER.error("Cannot read the metadata file {}. File not found.", fileLocation);
      } else {
        this.metadata = (Metadata) unmarshaller.unmarshal(resource);
      }
    } catch (JAXBException e) {
      LOGGER.error("Cannot read the metadata file " + getMetadataFile(), e);
    }
  }

  /**
   * Read a MessageML template file.
   *
   * This template file must be inside the directory templates on the classpath and should have
   * the MessageML v2 tags used to render integration messages.
   */
  private void readTemplateFile() {
    String fileLocation = BASE_TEMPLATE_PATH + getTemplateFile();

    InputStream resource = getClass().getClassLoader().getResourceAsStream(fileLocation);

    if (resource == null) {
      LOGGER.error("Cannot read the template file {}. File not found.", fileLocation);
      return;
    }

    try(BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
      String line;
      StringBuilder responseData = new StringBuilder();

      while ((line = reader.readLine()) != null) {
        responseData.append(line);
        responseData.append('\n');
      }

      this.messageMLTemplate = responseData.toString();
    } catch (IOException e) {
      LOGGER.error("Cannot read the template file " + fileLocation, e);
    }
  }

  /**
   * Generates MessageML V2 object according to the messageML template file and generated entity JSON
   * parsing the JSON input payload.
   *
   * @param node JSON node received from the third-party service
   * @return MessageML v2 object
   */
  public Message parse(JsonNode node) {
    if (StringUtils.isEmpty(messageMLTemplate)) {
      return null;
    }

    String entityJSON = getEntityJSON(node);

    if (StringUtils.isNotEmpty(entityJSON)) {
      Message message = new Message();
      message.setMessage(messageMLTemplate);
      message.setData(entityJSON);
      message.setVersion(MessageMLVersion.V2);

      return message;
    }

    return null;
  }

  /**
   * Retrieves the Entity JSON based on metadata objects.
   *
   * @param node JSON input payload
   * @return Entity JSON or null if there are no metadata objects to be processed.
   */
  private String getEntityJSON(JsonNode node) {
    if (metadata == null) {
      return null;
    }

    EntityObject root = new EntityObject(metadata.getType(), getVersion());
    List<MetadataObject> objects = metadata.getObjects();

    preProcessInputData(node);
    processMetadataObjects(root, node, objects);
    postProcessOutputData(root, node);

    try {
      Map<String, Object> result = new LinkedHashMap<>();
      result.put(metadata.getName(), root);

      return JsonUtils.writeValueAsString(result);
    } catch (JsonProcessingException e) {
      LOGGER.error("Fail to parse incoming payload", e);
      return null;
    }
  }

  /**
   * Perform a pre-processing on the input data.
   *
   * This method can be overridden by the concrete parser classes to perform any data manipulation
   * on the JSON input payload or augment the payload received with internal Symphony data retrieved
   * through the Symphony API's.
   *
   * Examples:
   * - Replace '\n' to '<br/>' tags
   * - Uppercase JSON field content
   * - Scape characters
   * - Include user ID and username retrieved from the User API
   *
   * @param input JSON input payload
   */
  protected void preProcessInputData(JsonNode input) {
    // Do nothing
  }

  /**
   * Process metadata objects to generate Entity JSON. This method is called recursively for
   * the nested metadata objects. The metadata fields inside each metadata object have your own
   * logic to build the corresponding field into Entity JSON.
   *
   * Basically, this method gets the JSON object received from the third-party service and generates
   * the Entity JSON intended according to the metadata objects defined in a XML document.
   *
   * Each metadata object defined must generate a JSON object inside the Entity JSON and the metadata
   * fields must generate fields inside those JSON objects.
   *
   * @param root Root object from Entity JSON
   * @param node JSON node received from the third-party service
   * @param objects List of metadata objects
   */
  private void processMetadataObjects(EntityObject root, JsonNode node, List<MetadataObject> objects) {
    for (MetadataObject object : objects) {
      EntityObject entity = new EntityObject(object.getType(), object.getVersion());

      if (object.getFields() != null) {
        for (MetadataField field : object.getFields()) {
          field.process(entity, node);
        }
      }

      if (object.getChildren() != null) {
        processMetadataObjects(entity, node, object.getChildren());
      }

      if (!entity.getContent().isEmpty()) {
        root.addContent(object.getId(), entity);
      }
    }
  }

  /**
   * Perform a post-processing on the output data.
   *
   * This method can be overridden by the concrete parser classes to include additional fields
   * on the output Entity JSON which weren't be processed directly from the JSON input payload.
   *
   * Example:
   * - Array of JSON objects
   *
   * @param output Output Entity JSON
   * @param input JSON input payload
   */
  protected void postProcessOutputData(EntityObject output, JsonNode input) {
    // Do nothing
  }

  /**
   * Get the MessageML template filename that must be read by the parser during the bootstrap.
   * @return MessageML template filename
   */
  protected abstract String getTemplateFile();

  /**
   * Get the XML filename that contains the metadata objects.
   * @return XML filename
   */
  protected abstract String getMetadataFile();

  /**
   * Get the event version. This version should use the notation 'Major.minor'.
   * Default version is '1.0'
   * @return Entity JSON version
   */
  protected String getVersion() {
    return metadata.getVersion();
  }

}

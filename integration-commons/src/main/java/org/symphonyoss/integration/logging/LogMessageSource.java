package org.symphonyoss.integration.logging;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;
import org.symphonyoss.integration.exception.ExceptionMessageFormatter;

import java.io.IOException;
import java.util.Locale;

import javax.annotation.PostConstruct;

/**
 * This utility class provides access and formatting methods for message resources from resource bundle files, located
 * at all of the application class paths. The resource bundles should contain application logging messages only,
 * as the purpose of this component is supporting the construction of output information for application logging.
 *
 * In order to use this component, add a your-component-log-messages.properties to your component's classpath. The file
 * will be automatically loaded as long as it ends with "-log-messages.properties". Make sure you create a unique name
 * for your file, so that your resources don't get overridden by another conflicting file. Likewise, make sure the
 * resource ids on your file have a name convention that avoid conflicts with other resources, i.e. avoid simple names
 * such as "configuration.not.found". Add your component identifier as a prefix for resource ids to avoid such
 * problem.
 *
 * Resources from all application components are made available through this component, but different application
 * components should not rely on each other resources, unless they are naturally dependent on each other.
 *
 * Created by Evandro Carrenho on 30/01/17.
 */
@Component
public class LogMessageSource {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogMessageSource.class);

  private static final String UNABLE_TO_LOAD_LOG_MESSAGES = "integration.commons.unable.to.load.log.messages";

  private static final String UNABLE_TO_LOAD_LOG_MESSAGES_SOLUTION =
      "integration.commons.unable.to.load.log.messages.solution";

  private static final String INTEGRATION_COMMONS_LOG_MESSAGES = "integration-commons-log-messages";

  private static final String CLASSPATH_LOG_MESSAGES_PROPERTIES = "classpath*:*-log-messages.properties";

  private static final String RESOURCE_NOT_FOUND = "Unable to record log information. Log message not found for "
      + "resource id %s.";

  @Autowired
  private ResourceLoader resourceLoader;

  private ResourceBundleMessageSource messageSource;

  private MessageSourceAccessor accessor;

  @PostConstruct
  private void init() {
    messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename(INTEGRATION_COMMONS_LOG_MESSAGES);
    messageSource.setDefaultEncoding("UTF-8");
    accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);

    try {
      Resource[] logResources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
              .getResources(CLASSPATH_LOG_MESSAGES_PROPERTIES);


      String[] baseNames = new String[logResources.length];
      for (int i = 0; i < logResources.length; i++) {
        baseNames[i] = FilenameUtils.removeExtension(logResources[i].getFilename());
      }

      messageSource.addBasenames(baseNames);
    } catch (IOException e) {
      LOGGER.error(ExceptionMessageFormatter.format("Log Message Source",
          this.getMessage(UNABLE_TO_LOAD_LOG_MESSAGES),
          this.getMessage(UNABLE_TO_LOAD_LOG_MESSAGES_SOLUTION)));
    }
  }

  /**
   * Retrieves the indicated resource and substitutes the placeholders with the given string arguments.
   * @param resId The resource id to be retrieved.
   * @param args The strings to fill in placeholders.
   * @return Formatted string.
   */
  public String getMessage(String resId, String... args) {
    try {
      return accessor.getMessage(resId, args);
    } catch (NoSuchMessageException e) {
      return String.format(RESOURCE_NOT_FOUND, resId);
    }
  }

}

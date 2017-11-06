package org.symphonyoss.integration.logging;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * This utility class provides access and formatting methods for message resources from resource bundle files, located
 * at all of the application class paths. The resource bundles should contain application messages.
 *
 * Created by rsanchez on 16/08/17.
 */
public class MessageUtils {

  private static final String RESOURCE_NOT_FOUND = "Message not found for resource %s.";

  private String bundleFileName;

  private MessageSourceAccessor accessor;

  /**
   * Return an instance of {@link MessageUtils} and initializes it using the provided bundle
   * filename.
   *
   * @param bundleFileName Bundle filename
   * @return MessageUtils
   */
  public MessageUtils(String bundleFileName) {
    this.bundleFileName = bundleFileName;

    init();
  }

  /**
   * Initializes the resource bundle according to the bundle filename and classloader.
   */
  private void init() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename(bundleFileName);
    messageSource.setDefaultEncoding("UTF-8");

    accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
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
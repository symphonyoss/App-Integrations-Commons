package org.symphonyoss.integration.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * Builder for building exception expected messages
 *
 * Created by apimentel on 23/06/17.
 */
public class ExpectedMessageBuilder {
  public static final String EXPECTED_SOLUTION_NO_SOLUTION =
      "No solution has been cataloged for troubleshooting this problem.";

  public static final String EXPECTED_SOLUTION_NO_SOLUTIONS =
      "No solutions has been cataloged for troubleshooting this problem.";

  private static final String COMPONENT_BASE = "Component: %s\n";
  private static final String MESSAGE_BASE = "Message: %s\n";
  private static final String SOLUTION_BASE = "Solutions: \n%s\n";
  private static final String STACKTRACE_BASE = "Stack trace: %s\n";

  private String expectedMessage;
  private String component;
  private String message;
  private String solution;
  private String stackTrace;

  public ExpectedMessageBuilder() {
    this.expectedMessage = "\n";
  }

  public ExpectedMessageBuilder component(String componentDescription) {
    this.component = String.format(COMPONENT_BASE, componentDescription);
    return this;
  }

  public ExpectedMessageBuilder message(String solutionDescription) {
    this.message = String.format(MESSAGE_BASE, solutionDescription);
    return this;
  }

  public ExpectedMessageBuilder solutions(String ...solutionDescription) {
    this.solution = String.format(SOLUTION_BASE, StringUtils.join(solutionDescription, "\n"));
    return this;
  }

  public ExpectedMessageBuilder stackTrace(String stackTraceDescription) {
    this.stackTrace = String.format(STACKTRACE_BASE, stackTraceDescription);
    return this;
  }

  public String build() {
    if (StringUtils.isNotEmpty(component)) {
      this.expectedMessage += component;
    }

    if (StringUtils.isNotEmpty(message)) {
      this.expectedMessage += message;
    }

    if (StringUtils.isNotEmpty(solution)) {
      this.expectedMessage += solution;
    }

    if (StringUtils.isNotEmpty(stackTrace)) {
      this.expectedMessage += stackTrace;
    }

    return expectedMessage;
  }
}

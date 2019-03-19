package by.courses.java.plugin.checkup.exception;

import org.apache.maven.plugin.MojoFailureException;

public class JavaCodeConventionException extends MojoFailureException {

  public JavaCodeConventionException(Object source, String shortMessage, String longMessage) {
    super(source, shortMessage, longMessage);
  }

  public JavaCodeConventionException(String message) {
    super(message);
  }

  public JavaCodeConventionException(String message, Throwable cause) {
    super(message, cause);
  }
}
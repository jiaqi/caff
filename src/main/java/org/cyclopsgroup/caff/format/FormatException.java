package org.cyclopsgroup.caff.format;

/**
 * Formatting failure
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class FormatException extends RuntimeException {
  private static final long serialVersionUID = -5798512658644125136L;

  /**
   * @param message Descriptive message about this error
   */
  FormatException(String message) {
    super(message);
  }

  /**
   * @param message Descriptive message about this error
   * @param cause Root cause
   */
  FormatException(String message, Throwable cause) {
    super(message, cause);
  }
}

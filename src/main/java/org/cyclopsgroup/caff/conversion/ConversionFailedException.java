package org.cyclopsgroup.caff.conversion;

/**
 * Exception that stands for conversion failure
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class ConversionFailedException extends RuntimeException {
  private static final long serialVersionUID = 6702759460929375871L;

  /**
   * @param message Descriptive message
   * @param cause Root cause
   */
  public ConversionFailedException(String message, Throwable cause) {
    super(message, cause);
  }

  /** @param message Message without a root cause */
  public ConversionFailedException(String message) {
    super(message);
  }
}

package org.cyclopsgroup.caff.format;

/**
 * Facade class for format package
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public final class Formats {
  private Formats() {}

  /**
   * Create new text format for fix-length syntax
   *
   * @param <T> Type of bean
   * @param beanType Type of bean
   * @return Fix length format of given type
   */
  public static <T> Format<T> newFixLengthFormat(Class<T> beanType) {
    return new FixLengthFormat<T>(beanType);
  }

  /**
   * Create new text format for CSV syntax
   *
   * @param <T> Type of bean
   * @param beanType Type of bean
   * @return CSV implementation of format
   */
  public static <T> Format<T> newCSVFormat(Class<T> beanType) {
    return new CSVFormat<T>(beanType);
  }
}

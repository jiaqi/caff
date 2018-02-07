package org.cyclopsgroup.caff.format;

import java.io.IOException;

/**
 * A base class that requries children to provide syntax conversion logic
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 * @param <T> Type of bean to convert from/to
 */
public abstract class AbstractSyntaxSupport<T> {
  /**
   * Converter string to object
   *
   * @param string String form of object
   * @return Object result
   * @throws IOException Allows IOExceptoin
   */
  protected abstract T fromString(String string) throws IOException;

  /**
   * Convert object to string
   *
   * @param bean Object to convert from
   * @return String form of object
   * @throws IOException Allows IOException
   */
  protected abstract String toString(T bean) throws IOException;
}

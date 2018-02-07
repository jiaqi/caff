package org.cyclopsgroup.caff.format;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.cyclopsgroup.caff.CharIterator;

/**
 * Format implementation based on CSV syntax
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 * @param <T> Type of bean to format or parse
 */
public class CSVFormat<T> extends Format<T> {
  private final CSVImpl<T> impl;

  /**
   * @param beanType Type of bean to format or parse
   */
  public CSVFormat(Class<T> beanType) {
    super(beanType);
    impl = new CSVImpl<T>(beanType);
  }

  @Override
  public T parse(CharSequence content) throws IOException {
    T bean = createBean();
    impl.populate(bean, CharIterator.instanceOf(content));
    return bean;
  }

  @Override
  public void populate(T bean, Reader reader) throws IOException {
    impl.populate(bean, CharIterator.instanceOf(reader));
  }

  @Override
  public void print(T object, Writer output) throws IOException {
    impl.print(object, output);
  }
}

package org.cyclopsgroup.caff.format;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.cyclopsgroup.caff.CharArrayCharSequence;

/**
 * Format implementation that parse and format fix length fields
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 * @param <T> Type of bean to format/parse
 */
public class FixLengthFormat<T> extends Format<T> {
  private final FixLengthImpl<T> impl;

  /**
   * @param beanType Type of bean
   */
  public FixLengthFormat(Class<T> beanType) {
    super(beanType);
    impl = new FixLengthImpl<T>(beanType);
  }

  @Override
  public char[] formatToCharArray(T object) {
    return impl.print(object);
  }

  @Override
  public T parse(CharSequence content) {
    T bean = createBean();
    populate(bean, content);
    return bean;
  }

  @Override
  public T parse(Reader reader) throws IOException {
    char[] line = new char[impl.type.length()];
    reader.read(line);
    return parse(new CharArrayCharSequence(line));
  }

  private void populate(T object, CharSequence line) {
    impl.populate(object, line);
  }

  @Override
  public void populate(T object, Reader reader) throws IOException {
    char[] line = new char[impl.type.length()];
    reader.read(line);
    populate(object, new CharArrayCharSequence(line));
  }

  @Override
  public void print(T object, Writer out) throws IOException {
    char[] output = impl.print(object);
    out.write(output);
  }
}

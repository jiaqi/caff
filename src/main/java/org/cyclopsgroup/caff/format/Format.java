package org.cyclopsgroup.caff.format;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import org.cyclopsgroup.caff.CharArrayCharSequence;

/**
 * Base class that knows how to format/parse beans
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 * @param <T> Type of bean to format or parse
 */
public abstract class Format<T> {
  private final Class<T> beanType;

  /** @param beanType Type of bean to format or parse */
  protected Format(Class<T> beanType) {
    if (beanType == null) {
      throw new NullPointerException("Type of bean can't be NULL");
    }
    this.beanType = beanType;
  }

  /** @return A new instance of bean */
  final T createBean() {
    try {
      return beanType.newInstance();
    } catch (InstantiationException e) {
      throw new InvalidTypeException("Can't create instance of " + beanType, e);
    } catch (IllegalAccessException e) {
      throw new InvalidTypeException("Can't create instance of " + beanType, e);
    }
  }

  /**
   * @param object Object to print
   * @return Char array of output
   * @throws IOException If IO operation fails
   */
  public char[] formatToCharArray(T object) throws IOException {
    CharArrayWriter output = new CharArrayWriter();
    print(object, output);
    output.flush();
    return output.toCharArray();
  }

  public String formatToString(T object) throws IOException {
    return new String(formatToCharArray(object));
  }

  public T parse(CharSequence content) throws IOException {
    return parse(new CharArrayReader(CharArrayCharSequence.sequenceToArray(content)));
  }

  /**
   * Parses provided {@link Reader} and returns typed result.
   *
   * @param reader with content of bean to parse.
   * @return the new instance of bean.
   * @throws IOException allows IO exceptions.
   */
  public T parse(Reader reader) throws IOException {
    T bean = createBean();
    populate(bean, reader);
    return bean;
  }

  /**
   * @param object Bean object to populate
   * @param reader Input of values
   * @throws IOException If IO operation fails
   */
  public abstract void populate(T object, Reader reader) throws IOException;

  /**
   * @param object Object to print out
   * @param output Output to print to
   * @throws IOException If operation fails
   */
  public abstract void print(T object, Writer output) throws IOException;
}

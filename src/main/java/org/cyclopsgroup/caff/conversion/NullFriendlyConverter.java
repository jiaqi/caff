package org.cyclopsgroup.caff.conversion;

/**
 * A converter that maps between empty CharSequence and NULL
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of value to convert from/to
 */
public class NullFriendlyConverter<T> implements Converter<T> {
  private static final String EMPTY = "";

  private final Converter<T> proxy;

  /**
   * @param proxy Internal converter that does actual conversion
   */
  public NullFriendlyConverter(Converter<T> proxy) {
    if (proxy == null) {
      throw new NullPointerException("Input proxy converter can't be NULL");
    }
    this.proxy = proxy;
  }

  @Override
  public T fromCharacters(CharSequence text) {
    if (text == null || text.length() == 0) {
      return null;
    }
    return proxy.fromCharacters(text);
  }

  @Override
  public CharSequence toCharacters(T value) {
    if (value == null) {
      return EMPTY;
    }
    return proxy.toCharacters(value);
  }
}

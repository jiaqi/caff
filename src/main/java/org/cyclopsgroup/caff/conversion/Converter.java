package org.cyclopsgroup.caff.conversion;

/**
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of object to convert from/to
 */
public interface Converter<T> {
  /**
   * @param text Character sequence form of value
   * @return Converted result
   */
  T fromCharacters(CharSequence text);

  /**
   * @param value Object instance
   * @return Text form of value
   */
  CharSequence toCharacters(T value);
}

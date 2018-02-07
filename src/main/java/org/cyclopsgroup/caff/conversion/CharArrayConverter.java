package org.cyclopsgroup.caff.conversion;

import org.cyclopsgroup.caff.CharArrayCharSequence;

/**
 * Converter implementation for character array
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class CharArrayConverter implements Converter<char[]> {
  @Override
  public char[] fromCharacters(CharSequence text) {
    return CharArrayCharSequence.sequenceToArray(text);
  }

  @Override
  public CharSequence toCharacters(char[] value) {
    return new CharArrayCharSequence(value);
  }
}

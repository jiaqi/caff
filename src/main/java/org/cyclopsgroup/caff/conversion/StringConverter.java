package org.cyclopsgroup.caff.conversion;

/**
 * Converter implementation for String
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class StringConverter implements Converter<String> {
  @Override
  public String fromCharacters(CharSequence text) {
    return text.toString();
  }

  @Override
  public CharSequence toCharacters(String value) {
    return value;
  }
}

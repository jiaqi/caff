package org.cyclopsgroup.caff.conversion;

/**
 * No-op implementation of Converter for {@link CharSequence}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class CharSequenceConverter implements Converter<CharSequence> {
  @Override
  public CharSequence fromCharacters(CharSequence text) {
    return text;
  }

  @Override
  public CharSequence toCharacters(CharSequence value) {
    return value;
  }
}

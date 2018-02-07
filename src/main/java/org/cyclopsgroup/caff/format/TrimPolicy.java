package org.cyclopsgroup.caff.format;

/**
 * Define what should layout do when value is longer than limit
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public enum TrimPolicy {
  /**
   * Default value means, truncate right when align left, or truncate left when align right
   */
  FORWARD {
    @Override
    CharSequence trim(CharSequence src, int length, AlignPolicy align) {
      if (src.length() <= length) {
        throw new AssertionError(
            "Length of source[" + src.length() + " must be greater than " + length);
      }
      CharSequence truncated;
      switch (align) {
        case LEFT:
          truncated = src.subSequence(0, length);
          break;
        case RIGHT:
          truncated = src.subSequence(src.length() - length, src.length());
          break;
        default:
          throw new AssertionError("No such thing " + align);
      }
      return truncated;
    }
  },
  /**
   * Truncate left when align left, or truncate right when align right
   */
  REVERSE {
    @Override
    CharSequence trim(CharSequence src, int length, AlignPolicy align) {
      if (src.length() <= length) {
        throw new AssertionError(
            "Length of source[" + src.length() + " must be greater than " + length);
      }
      CharSequence truncated;
      switch (align) {
        case LEFT:
          truncated = src.subSequence(src.length() - length, src.length());
          break;
        case RIGHT:
          truncated = src.subSequence(0, length);
          break;
        default:
          throw new AssertionError("No such thing " + align);
      }
      return truncated;
    }
  },
  /**
   * When value is too long, throw {@link IllegalArgumentException} instead of truncating
   */
  DISALLOW {
    @Override
    CharSequence trim(CharSequence src, int length, AlignPolicy align) {
      if (src.length() <= length) {
        throw new AssertionError(
            "Length of source[" + src.length() + " must be greater than " + length);
      }
      throw new FormatException("Value [" + src + "] is bigger than available space " + length);
    }
  };

  /**
   * Truncate given long content to given length based on alignment option
   *
   * @param src Source content to truncate
   * @param length Length of result of truncation
   * @param align Alignment option
   * @return Truncated result
   */
  abstract CharSequence trim(CharSequence src, int length, AlignPolicy align);
}

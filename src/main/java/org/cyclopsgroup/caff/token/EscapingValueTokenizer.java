package org.cyclopsgroup.caff.token;

/**
 * An implementation that escapes special character with an escape character
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class EscapingValueTokenizer implements ValueTokenizer {
  private static enum ProcessingState {
    /** Followd by escape character */
    ESCAPING,
    /** Ready for new word */
    READY,
    /** Appending a word */
    WORD;
  }

  private final char delimiter;

  private final char escaper;

  /**
   * Default constructor sets escape character with back slash, and imply white space as delimiter
   */
  public EscapingValueTokenizer() {
    this(' ', '\\');
  }

  /**
   * @param delimiter Delimiter character
   * @param escaper Given escape character
   */
  public EscapingValueTokenizer(char delimiter, char escaper) {
    this.escaper = escaper;
    this.delimiter = delimiter;
  }

  @Override
  public String escape(String output) {
    if (output.indexOf(escaper) == -1 && output.indexOf(delimiter) == -1) {
      return output;
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0, j = 0; i < output.length(); ) {
      int e = output.indexOf(escaper, i);
      int d = output.indexOf(delimiter, i);
      if (e == -1 && d == -1) {
        sb.append(output.substring(i));
        break;
      }
      if (e >= 0 && d >= 0) {
        j = Math.min(e, d);
      } else {
        j = Math.max(e, d);
      }
      sb.append(output.substring(i, j)).append(escaper).append(output.charAt(j));
      i = ++j;
    }
    return sb.toString();
  }

  /** @return Delimiter character */
  public final char getDelimiter() {
    return delimiter;
  }

  /** @return Escape character */
  public final char getEscaper() {
    return escaper;
  }

  @Override
  public void parse(CharSequence input, TokenEventHandler handler) {
    ProcessingState state = ProcessingState.READY;
    StringBuilder word = null;
    int wordStart = 0;
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      switch (state) {
        case READY:
          if (c == delimiter) {
            continue;
          } else {
            wordStart = i;
            word = new StringBuilder();
            if (c == escaper) {
              state = ProcessingState.ESCAPING;
            } else {
              state = ProcessingState.WORD;
              word.append(c);
            }
          }
          break;
        case WORD:
          if (c == delimiter) {
            state = ProcessingState.READY;
            TokenEvent ev = new TokenEvent(word.toString(), wordStart, i, true);
            handler.handleEvent(ev);
            word = null;
          } else if (c == escaper) {
            state = ProcessingState.ESCAPING;
          } else {
            word.append(c);
          }
          break;
        case ESCAPING:
          state = ProcessingState.WORD;
          word.append(c);
      }
    }
    if (word != null) {
      TokenEvent ev = new TokenEvent(word.toString(), wordStart, input.length(), false);
      handler.handleEvent(ev);
    }
  }
}

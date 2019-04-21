package org.cyclopsgroup.caff.dp.wiki;

import java.io.PrintWriter;
import org.apache.commons.lang3.StringUtils;
import org.cyclopsgroup.caff.dp.Instrument;

public class ListInstrument extends Instrument {
  private static final String PREFIX = LINE_START + "* ";

  @Override
  public int searchToOpen(String segment, Instrument parent) {
    return parent == null && segment.startsWith(PREFIX) ? 0 : -1;
  }

  @Override
  public int open(String segment, PrintWriter out) {
    out.print("<ul><li>");
    return PREFIX.length();
  }

  @Override
  public int searchToClose(String segment) {
    return segment.equals(LINE_START) ? 0 : -1;
  }

  @Override
  public int close(String segment, PrintWriter out) {
    out.print("</li></ul>");
    return LINE_START.length();
  }

  @Override
  public void printText(String text, PrintWriter out) {
    if (text.startsWith(PREFIX)) {
      out.print("</li><li>");
      text = StringUtils.removeStart(text, PREFIX);
    }
    if (text.startsWith(LINE_START)) {
      text = " " + StringUtils.removeStart(text, LINE_START);
    }
    out.print(text);
  }
}

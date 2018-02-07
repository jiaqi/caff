package org.cyclopsgroup.caff.dp.wiki;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.cyclopsgroup.caff.dp.Instrument;

public class PreservedInstrument extends Instrument {
  private static final String PREFIX = LINE_START + "  ";

  @Override
  public int close(String segment, PrintWriter out) {
    out.print("</pre></p>");
    return LINE_START.length();
  }

  @Override
  public int open(String segment, PrintWriter out) {
    out.print("<p><pre>");
    return PREFIX.length();
  }

  @Override
  public void printText(String text, PrintWriter out) throws IOException {
    if (text.startsWith(PREFIX)) {
      text = System.lineSeparator() + StringUtils.removeStart(text, PREFIX);
    }
    StringEscapeUtils.ESCAPE_HTML4.translate(text, out);
  }

  @Override
  public int searchToClose(String segment) {
    return segment.equals(LINE_START) ? 0 : -1;
  }

  @Override
  public int searchToOpen(String segment, Instrument parent) {
    return parent == null && segment.startsWith(PREFIX) ? 0 : -1;
  }
}

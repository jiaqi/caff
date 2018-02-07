package org.cyclopsgroup.caff.dp.wiki;

import java.io.PrintWriter;

import org.cyclopsgroup.caff.dp.Instrument;

public class ParagraphInstrument extends Instrument {
  @Override
  public int searchToOpen(String segment, Instrument parent) {
    if (parent != null || segment.length() <= LINE_START.length()) {
      return -1;
    }
    if (segment.startsWith(LINE_START)
        && Character.isLetterOrDigit(segment.charAt(LINE_START.length()))) {
      return 0;
    }
    return -1;
  }

  @Override
  public int open(String segment, PrintWriter out) {
    out.write("<p>");
    return LINE_START.length();
  }

  @Override
  public int close(String segment, PrintWriter out) {
    out.write("</p>" + System.lineSeparator());
    return LINE_START.length();
  }

  @Override
  public int searchToClose(String segment) {
    return segment.equals(LINE_START) ? 0 : -1;
  }
}

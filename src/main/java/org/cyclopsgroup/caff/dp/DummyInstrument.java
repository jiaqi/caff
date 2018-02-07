package org.cyclopsgroup.caff.dp;

import java.io.PrintWriter;

public class DummyInstrument extends Instrument {
  @Override
  public int searchToOpen(String segment, Instrument parent) {
    return segment.startsWith(LINE_START) && segment.length() > LINE_START.length()
        && parent == null ? 0 : -1;
  }

  @Override
  public int open(String segment, PrintWriter out) {
    return LINE_START.length();
  }

  @Override
  public int searchToClose(String segment) {
    return segment.equals(LINE_START) ? 0 : -1;
  }

  @Override
  public int close(String segment, PrintWriter out) {
    return LINE_START.length();
  }
}

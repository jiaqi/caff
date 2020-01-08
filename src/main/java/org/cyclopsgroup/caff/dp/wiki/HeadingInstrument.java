package org.cyclopsgroup.caff.dp.wiki;

import com.google.common.base.Preconditions;
import java.io.PrintWriter;
import org.apache.commons.lang3.StringUtils;
import org.cyclopsgroup.caff.dp.Instrument;

public class HeadingInstrument extends Instrument {
  private final int length;

  private final String mark;

  public HeadingInstrument(int length) {
    Preconditions.checkArgument(length > 0 && length <= 5, "Invalid length %s.", length);
    this.length = length;
    this.mark = StringUtils.repeat("=", length);
  }

  @Override
  public int close(String segment, PrintWriter out) {
    out.write("</h" + length + ">");
    return LINE_START.length();
  }

  @Override
  public int open(String segment, PrintWriter out) {
    out.write("<h" + length + ">");
    return LINE_START.length() + length;
  }

  @Override
  public int searchToClose(String segment) {
    return segment.equals(LINE_START) ? 0 : -1;
  }

  @Override
  public int searchToOpen(String segment, Instrument parent) {
    if (segment.startsWith(LINE_START + mark)) {
      return 0;
    }
    return -1;
  }
}

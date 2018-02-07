package org.cyclopsgroup.caff.dp;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface DocumentProcessor {
  void process(Reader input, Writer output) throws IOException;
}

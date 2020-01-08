package org.cyclopsgroup.caff.dp.wiki;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.cyclopsgroup.caff.dp.DocumentProcessor;
import org.cyclopsgroup.caff.dp.DummyInstrument;
import org.cyclopsgroup.caff.dp.Instrument;
import org.cyclopsgroup.caff.dp.InstrumentedDocumentProcessor;

public class WikiDocumentProcessor implements DocumentProcessor {
  private final DocumentProcessor proc;

  public WikiDocumentProcessor() {
    List<Instrument> instruments = new ArrayList<Instrument>();
    instruments.add(new ParagraphInstrument());
    instruments.add(new ExternalLinkInstrument());
    instruments.add(new PreservedInstrument());
    instruments.add(new ListInstrument());
    for (int i = 5; i > 0; i--) {
      instruments.add(new HeadingInstrument(i));
    }
    instruments.add(new DummyInstrument());
    this.proc = new InstrumentedDocumentProcessor(instruments);
  }

  @Override
  public void process(Reader input, Writer output) throws IOException {
    proc.process(input, output);
  }
}

package org.cyclopsgroup.caff.dp.wiki;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.cyclopsgroup.caff.dp.DocumentProcessor;
import org.junit.Test;

public class WikiDocumentProcessorTest {
  private final DocumentProcessor proc = new WikiDocumentProcessor();

  @Test
  public void testHeader() throws IOException {
    verify("<h3>abc</h3>", "===abc\n\n");
  }

  @Test
  public void testList() throws IOException {
    verify("<ul><li>a</li><li>b</li></ul>", "* a\n* b\n\n");
  }

  @Test
  public void testNoMatching() throws IOException {
    verify("<a>ddddd</a>", "<a>ddddd</a>");
  }

  @Test
  public void testParagraph() throws IOException {
    verify("<p>a a aaa  b bb</p>", "a a aaa\n b bb\n\n");
  }

  @Test
  public void testParagraphAndLink() throws IOException {
    verify("<p>aa  <a href=\"xyz\">x</a> </p>", "aa [xyz|x]\n\n");
  }

  @Test
  public void testPreserved() throws IOException {
    verify("<p><pre>abc&amp;de&lt;f</pre></p>", "  abc&de<f\n\n");
  }

  private void verify(String expected, String input) throws IOException {
    StringWriter out = new StringWriter();
    proc.process(new StringReader(input), out);
    assertEquals(expected, out.toString().trim());
  }
}

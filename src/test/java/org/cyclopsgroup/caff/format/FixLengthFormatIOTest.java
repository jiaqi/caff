package org.cyclopsgroup.caff.format;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import org.cyclopsgroup.caff.ABean;

/**
 * A test that verifies {@link FixLengthFormat} dealing with {@link Reader} and {@link Writer}
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class FixLengthFormatIOTest extends AbstractFixLengthTestCase {
  private final Format<ABean> format = Formats.newFixLengthFormat(ABean.class);

  @Override
  protected ABean fromString(String string) throws IOException {
    return format.parse(new StringReader(string));
  }

  @Override
  protected String toString(ABean bean) throws IOException {
    StringWriter out = new StringWriter();
    format.print(bean, out);
    return out.toString();
  }
}

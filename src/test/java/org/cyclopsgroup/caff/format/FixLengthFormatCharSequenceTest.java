package org.cyclopsgroup.caff.format;

import java.io.IOException;

import org.cyclopsgroup.caff.ABean;

/**
 * A test case that tests {@link FixLengthFormat} with {@link CharSequence} input and output
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class FixLengthFormatCharSequenceTest extends AbstractFixLengthTestCase {
  private Format<ABean> format = Formats.newFixLengthFormat(ABean.class);

  @Override
  protected ABean fromString(String string) throws IOException {
    return format.parse(string);
  }

  @Override
  protected String toString(ABean bean) throws IOException {
    return format.formatToString(bean);
  }
}

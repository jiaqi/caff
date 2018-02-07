package org.cyclopsgroup.caff.format;

import org.cyclopsgroup.caff.ABean;

/**
 * Test case of {@link FixLengthImpl}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class FixLengthImplTest extends AbstractFixLengthTestCase {
  private final FixLengthImpl<ABean> impl = new FixLengthImpl<ABean>(ABean.class);

  @Override
  protected ABean fromString(String string) {
    ABean bean = new ABean();
    impl.populate(bean, string);
    return bean;
  }

  @Override
  protected String toString(ABean bean) {
    char[] output = impl.print(bean);
    return new String(output);
  }
}

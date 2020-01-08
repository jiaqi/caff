package org.cyclopsgroup.caff.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.cyclopsgroup.caff.ABean;
import org.junit.Test;

/**
 * Test case of {@link FixLengthImpl}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public abstract class AbstractFixLengthTestCase extends AbstractSyntaxSupport<ABean> {
  /**
   * Verify result of populate
   *
   * @throws IOException Allows IOException
   */
  @Test
  public void testPopulate() throws IOException {
    ABean bean = fromString("03519691122Bender    Rod       1");
    assertEquals(35, bean.getAge());
    assertEquals("Bender", bean.getFirstName());
    assertEquals("Rod", bean.lastName);
    assertTrue(bean.isRetired());
    assertEquals("1969-11-22", new SimpleDateFormat("yyyy-MM-dd").format(bean.getBirthDay()));
  }

  /**
   * Verify printing result
   *
   * @throws ParseException Should never happen
   * @throws IOException Allows IOException
   */
  @Test
  public void testPrintNormally() throws ParseException, IOException {
    ABean bean = new ABean();
    bean.setAge(35);
    bean.setBirthDay(new SimpleDateFormat("yyyyMMdd").parse("19871204"));
    bean.setFirstName("Bender");
    bean.lastName = "Rod";
    assertEquals("03519871204Bender    Rod       0", toString(bean).trim());
  }

  /**
   * Test that has a long field value
   *
   * @throws IOException Allows IOException
   */
  @Test
  public void testPrintWithOverflow() throws IOException {
    ABean bean = new ABean();
    bean.setAge(1234);
    bean.setFirstName("01234567890123456789");
    bean.setRetired(true);
    bean.lastName = "Rod";
    assertEquals("234        0123456789Rod       1", toString(bean).trim());
  }
}

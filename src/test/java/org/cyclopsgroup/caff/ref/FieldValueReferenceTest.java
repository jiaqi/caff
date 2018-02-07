package org.cyclopsgroup.caff.ref;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.cyclopsgroup.caff.ABean;
import org.junit.Test;

/**
 * Test case of {@link FieldValueReference}
 * 
 * @author jiaqi
 */
public class FieldValueReferenceTest {
  /**
   * Verify field value reference does its job
   * 
   * @throws Exception
   */
  @Test
  public void testPublicField() throws Exception {
    ABean bean = new ABean();
    bean.lastName = "Guo";
    Field field = ABean.class.getField("lastName");
    FieldValueReference<ABean> ref = new FieldValueReference<ABean>(field);
    assertTrue(ref.isReadable());
    assertTrue(ref.isWritable());
    assertEquals("Guo", ref.readValue(bean));
    ref.writeValue("Rod", bean);
    assertEquals("Rod", bean.lastName);
  }
}

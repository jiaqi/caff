package org.cyclopsgroup.caff.ref;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.PropertyUtils;
import org.cyclopsgroup.caff.ABean;
import org.junit.Test;

/**
 * Test case of {@link PropertyValueReference}
 * 
 * @author jiaqi
 */
public class PropertyValueReferenceTest {
  /**
   * Verify property value reference correctly reference getter and setter
   * 
   * @throws Exception Allows all exceptions
   */
  @Test
  public void testPublicPublic() throws Exception {
    ABean bean = new ABean();
    bean.setAge(100);
    PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(bean, "age");
    PropertyValueReference<ABean> ref = new PropertyValueReference<ABean>(desc);
    assertTrue(ref.isReadable());
    assertTrue(ref.isWritable());
    assertEquals(Integer.valueOf(100), ref.readValue(bean));
    ref.writeValue(40, bean);
    assertEquals(40, bean.getAge());
  }
}

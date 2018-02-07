package org.cyclopsgroup.caff.ref;

import static org.junit.Assert.assertTrue;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

import org.apache.commons.beanutils.PropertyUtils;
import org.cyclopsgroup.caff.ABean;
import org.junit.Test;

/**
 * Test case of {@link ValueReference}.
 */
public class ValuerReferenceTest {
  /**
   * Verifies creation of field referrence.
   */
  @Test
  public void testInstanceOfWithField() throws Exception {
    Field field = ABean.class.getField("lastName");
    ValueReference<ABean> fieldRef = ValueReference.instanceOf(field);
    assertTrue(fieldRef instanceof FieldValueReference);
  }

  /**
   * Verifies the creation of property reference.
   */
  @Test
  public void testInstanceOfWithProperty() throws Exception {
    PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(new ABean(), "age");
    ValueReference<ABean> propRef = ValueReference.instanceOf(desc);
    assertTrue(propRef instanceof PropertyValueReference);
  }
}

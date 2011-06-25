package org.cyclopsgroup.caff.ref;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

import org.apache.commons.beanutils.PropertyUtils;
import org.cyclopsgroup.caff.ABean;
import org.junit.Test;

/**
 * Test case of {@link ValueReference}
 * 
 * @author jiaqi
 */
public class ValuerReferenceTest
{
    /**
     * Verify creation of {@link FieldValueRefernce}
     * 
     * @throws Exception Allows any exception
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testInstanceOfWithField() throws Exception
    {
        Field field = ABean.class.getField("lastName");
        ValueReference<ABean> fieldRef = ValueReference.instanceOf(field);
        assertTrue(fieldRef instanceof FieldValueReference);
    }
    
    /**
     * Verify creation of {@link PropertyValuereference}
     * 
     * @throws Exception Allowed any exception
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testInstanceOfWithProperty() throws Exception
    {
        PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(new ABean(), "age");
        ValueReference<ABean> propRef = ValueReference.instanceOf(desc);
        assertTrue(propRef instanceof PropertyValueReference);
    }
}

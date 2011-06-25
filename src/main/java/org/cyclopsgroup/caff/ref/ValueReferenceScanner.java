package org.cyclopsgroup.caff.ref;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * A class that iterate through all property descriptors and fields and look for possible value references.
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 * @param <T> Type of bean to inspect
 */
public class ValueReferenceScanner<T>
{
    /**
     * A listener interface to handle found references
     *
     * @param <T> Type of bean to inspect
     * @param <H> Type of hint object as the second parameter
     */
    public static interface Listener<T, H>
    {
        /**
         * @param reference Found reference that meets requirement
         * @param hint A hint object
         * @param access Access point, a method or field, where the reference comes from
         */
        void handleReference( ValueReference<T> reference, H hint, AccessibleObject access );
    }

    private final Class<T> beanType;

    /**
     * @param beanType Type of bean to scan
     */
    public ValueReferenceScanner( Class<T> beanType )
    {
        if ( beanType == null )
        {
            throw new NullPointerException( "Given bean type can't be NULL" );
        }
        this.beanType = beanType;
    }

    private <H extends Annotation> AccessibleObject findAnnotatedAccess( PropertyDescriptor desc,
                                                                         Class<H> expectedAnnotation )
    {
        Field field;
        try
        {
            field = beanType.getClass().getField( desc.getName() );
        }
        catch ( Throwable e )
        {
            field = null;
        }
        List<AccessibleObject> accesses =
            Arrays.asList( (AccessibleObject) desc.getReadMethod(), desc.getWriteMethod(), field );
        for ( AccessibleObject access : accesses )
        {
            if ( access != null && access.isAnnotationPresent( expectedAnnotation ) )
            {
                return access;
            }
        }
        return null;
    }

    /**
     * Scan given class and look for possible references annotated by given annotation
     *
     * @param <H> Only field or method annotated with this annotation is inspected
     * @param expectedAnnotation Only field or method annotated with this annotation is inspected
     * @param listener Listener to handle found references
     */
    public <H extends Annotation> void scanForAnnotation( Class<H> expectedAnnotation, Listener<T, H> listener )
    {
        BeanInfo beanInfo;
        try
        {
            beanInfo = Introspector.getBeanInfo( beanType );
        }
        catch ( IntrospectionException e )
        {
            throw new RuntimeException( "Can't get bean info of " + beanType );
        }
        for ( PropertyDescriptor desc : beanInfo.getPropertyDescriptors() )
        {
            AccessibleObject access = findAnnotatedAccess( desc, expectedAnnotation );
            if ( access == null )
            {
                continue;
            }
            ValueReference<T> reference;
            if ( access instanceof Field )
            {
                reference = ValueReference.instanceOf( (Field) access );
            }
            else
            {
                reference = ValueReference.instanceOf( desc );
            }
            listener.handleReference( reference, access.getAnnotation( expectedAnnotation ), access );
        }
        for ( Field field : beanType.getFields() )
        {
            H annotation = field.getAnnotation( expectedAnnotation );
            if ( annotation == null )
            {
                continue;
            }
            ValueReference<T> reference = ValueReference.instanceOf( field );
            listener.handleReference( reference, annotation, field );
        }
    }
}

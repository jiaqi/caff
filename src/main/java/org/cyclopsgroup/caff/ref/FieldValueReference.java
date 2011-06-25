package org.cyclopsgroup.caff.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Implementation of value reference that based on a public field
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of value
 */
class FieldValueReference<T>
    extends ValueReference<T>
{
    private final Field field;

    /**
     * @param field Reflection field object
     */
    FieldValueReference( Field field )
    {
        if ( field == null )
        {
            throw new NullPointerException( "Field can't be NULL" );
        }
        if ( !Modifier.isPublic( field.getModifiers() ) )
        {
            throw new IllegalArgumentException( "Only public field can be directly referenced: " + field );
        }
        this.field = field;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getName()
    {
        return field.getName();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Class<?> getType()
    {
        return field.getType();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isReadable()
    {
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isWritable()
    {
        return !Modifier.isFinal( field.getModifiers() );
    }

    /**
     * @inheritDoc
     */
    @Override
    public Object readValue( T owner )
        throws AccessFailureException
    {
        try
        {
            return field.get( owner );
        }
        catch ( IllegalAccessException e )
        {
            throw new AccessFailureException( "Can't get field " + field + " of " + owner, e );
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void writeValue( Object value, T owner )
        throws AccessFailureException
    {
        if ( value == null && field.getType().isPrimitive() )
        {
            return;
        }
        try
        {
            field.set( owner, value );
        }
        catch ( IllegalAccessException e )
        {
            throw new AccessFailureException( "Can't set field " + field + " of " + owner + " to " + value, e );
        }
    }
}

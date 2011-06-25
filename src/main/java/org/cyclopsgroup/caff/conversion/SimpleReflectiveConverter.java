package org.cyclopsgroup.caff.conversion;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Implementation of converter that calls constructor with string parameter and toString method for conversion
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of value to convert from/to
 */
public class SimpleReflectiveConverter<T>
    implements Converter<T>
{
    private final Constructor<T> constructor;

    private final Method toStringMethod;

    private final Class<T> type;

    /**
     * @param type Type of value to convert from/to
     */
    public SimpleReflectiveConverter( Class<T> type )
    {
        this.type = type;
        try
        {
            constructor = type.getConstructor( String.class );
            toStringMethod = type.getMethod( "toString" );
        }
        catch ( SecurityException e )
        {
            throw new IllegalArgumentException( "Can't find constructor with string for type " + type, e );
        }
        catch ( NoSuchMethodException e )
        {
            throw new IllegalArgumentException( "Can't find constructor with string for type " + type, e );
        }
    }

    /**
     * @inheritDoc
     */
    public T fromCharacters( CharSequence text )
    {
        try
        {
            return constructor.newInstance( text.toString() );
        }
        catch ( InstantiationException e )
        {
            throw new ConversionFailedException( "Can't call " + type + "(String) with " + text, e );
        }
        catch ( IllegalAccessException e )
        {
            throw new ConversionFailedException( "Can't call " + type + "(String) with " + text, e );
        }
        catch ( InvocationTargetException e )
        {
            throw new ConversionFailedException( "Can't call " + type + "(String) with " + text, e );
        }
    }

    public CharSequence toCharacters( T value )
    {
        try
        {
            return (CharSequence) toStringMethod.invoke( value );
        }
        catch ( IllegalAccessException e )
        {
            throw new ConversionFailedException( "Can't call " + type + ".toString() on " + value, e );
        }
        catch ( InvocationTargetException e )
        {
            throw new ConversionFailedException( "Can't call " + type + ".toString() on " + value, e );
        }
    }
}

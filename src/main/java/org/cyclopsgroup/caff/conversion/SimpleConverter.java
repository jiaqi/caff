package org.cyclopsgroup.caff.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Converter implementation that does naive conversion for {@link String}, {@link CharSequence}, char[],
 * {@link BigDecimal}, {@link BigInteger} and all primitives
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of value to convert from/to
 */
public class SimpleConverter<T>
    implements Converter<T>
{
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPES;
    static
    {
        Map<Class<?>, Class<?>> primitiveTypes = new HashMap<Class<?>, Class<?>>();
        primitiveTypes.put( Boolean.TYPE, Boolean.class );
        primitiveTypes.put( Short.TYPE, Short.class );
        primitiveTypes.put( Integer.TYPE, Integer.class );
        primitiveTypes.put( Long.TYPE, Long.class );
        primitiveTypes.put( Float.TYPE, Float.class );
        primitiveTypes.put( Double.TYPE, Double.class );
        PRIMITIVE_TYPES = Collections.unmodifiableMap( primitiveTypes );
    }

    private final Converter<T> proxy;

    public T fromCharacters( CharSequence text )
    {
        return proxy.fromCharacters( text );
    }

    public CharSequence toCharacters( T value )
    {
        return proxy.toCharacters( value );
    }

    /**
     * @param type Type of value to convert from/to
     */
    @SuppressWarnings( "unchecked" )
    public SimpleConverter( Class<T> type )
    {
        if ( type == null )
        {
            throw new NullPointerException( "Type can't be NULL" );
        }
        if ( type == String.class )
        {
            proxy = (Converter<T>) new StringConverter();
        }
        else if ( type == CharSequence.class )
        {
            proxy = (Converter<T>) new CharSequenceConverter();
        }
        else if ( type == char[].class )
        {
            proxy = (Converter<T>) new CharArrayConverter();
        }
        else if ( type.isPrimitive() )
        {
            if ( PRIMITIVE_TYPES.containsKey( type ) )
            {
                proxy = (Converter<T>) new SimpleReflectiveConverter( PRIMITIVE_TYPES.get( type ) );
            }
            else
            {
                throw new IllegalArgumentException( "Type " + type + " is not supported by " + getClass() );
            }
        }
        else if ( type.isEnum() )
        {
            proxy = (Converter<T>) new EnumConverter( type );
        }
        else
        {
            proxy = (Converter<T>) new SimpleReflectiveConverter( type );
        }
    }
}

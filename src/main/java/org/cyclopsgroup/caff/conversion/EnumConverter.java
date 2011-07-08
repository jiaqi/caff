package org.cyclopsgroup.caff.conversion;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.cyclopsgroup.caff.NormalizedValue;

/**
 * Converter that converts enum
 *
 * @author jiaqi
 * @param <T> Type of enum to convert from/to
 */
public class EnumConverter<T extends Enum<T>>
    implements Converter<T>
{
    private final Map<String, T> stringToValue;

    private final Map<T, String> valueToString;

    /**
     * @param enumType Type of enum to convert
     */
    public EnumConverter( Class<T> enumType )
    {
        boolean normalized = NormalizedValue.class.isAssignableFrom( enumType );
        Map<String, T> stringValues = new HashMap<String, T>();
        Map<T, String> valueStrings = new HashMap<T, String>();
        for ( T e : EnumSet.allOf( enumType ) )
        {
            String key = normalized ? ( (NormalizedValue<?>) e ).getIdentifier().toString() : e.name();
            stringValues.put( key, e );
            valueStrings.put( e, key );
        }
        stringToValue = Collections.unmodifiableMap( stringValues );
        valueToString = Collections.unmodifiableMap( valueStrings );
    }

    /**
     * @inheritDoc
     */
    public T fromCharacters( CharSequence text )
    {
        T result = stringToValue.get( text.toString() );
        if ( result == null )
        {
            throw new ConversionFailedException( "Value " + text + " is unknown" );
        }
        return result;
    }

    /**
     * @inheritValue
     */
    public CharSequence toCharacters( T value )
    {
        return valueToString.get( value );
    }
}
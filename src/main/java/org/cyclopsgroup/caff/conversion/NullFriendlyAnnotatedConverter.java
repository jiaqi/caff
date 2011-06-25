package org.cyclopsgroup.caff.conversion;

import java.lang.reflect.AccessibleObject;

/**
 * This converter is NULL friendly, and annotation based. When annotation is found, internally
 * {@link AnnotatedConverter} is used, otherwise {@link SimpleConverter} is used
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 * @param <T> Type of the value to convert from/to
 */
public class NullFriendlyAnnotatedConverter<T>
    implements Converter<T>
{
    private Converter<T> proxy;

    /**
     * @param valueType Type of value to convert from/to
     * @param access Accessible object where the value comes from
     */
    public NullFriendlyAnnotatedConverter( Class<T> valueType, AccessibleObject access )
    {
        Converter<T> c;
        if ( AnnotatedConverter.isConversionSupported( access ) )
        {
            c = new AnnotatedConverter<T>( valueType, access );
        }
        else
        {
            c = new SimpleConverter<T>( valueType );
        }
        proxy = new NullFriendlyConverter<T>( c );
    }

    /**
     * @inheritDoc
     */
    public T fromCharacters( CharSequence text )
    {
        return proxy.fromCharacters( text );
    }

    /**
     * @inheritDoc
     */
    public CharSequence toCharacters( T value )
    {
        return proxy.toCharacters( value );
    }
}

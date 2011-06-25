package org.cyclopsgroup.caff.conversion;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

/**
 * Converter that converts based on rules defined in annotation
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of value to converter from/to
 */
public class AnnotatedConverter<T>
    implements Converter<T>
{
    private static class Builder<T>
    {
        private Annotation annotation;

        private Converter<T> toConverter( Class<T> type )
        {
            if ( annotation == null )
            {
                throw new NullPointerException( "Input annotation can't be NULL" );
            }
            ConversionSupport support = annotation.annotationType().getAnnotation( ConversionSupport.class );
            if ( support == null )
            {
                throw new IllegalArgumentException( "Annotation " + annotation + " is not annotated with "
                    + ConversionSupport.class );
            }
            try
            {
                return support.factoryType().newInstance().getConverterFor( type, annotation );
            }
            catch ( InstantiationException e )
            {
                throw new RuntimeException( "Can't create converter for " + annotation, e );
            }
            catch ( IllegalAccessException e )
            {
                throw new RuntimeException( "Can't create converter for " + annotation, e );
            }
        }

        private Builder<T> withAccess( AccessibleObject access )
        {
            for ( Annotation a : access.getAnnotations() )
            {
                if ( a.annotationType().isAnnotationPresent( ConversionSupport.class ) )
                {
                    annotation = a;
                    return this;
                }
            }
            throw new IllegalArgumentException( "Access " + access + " isn't annotated with any conversion annotation" );
        }

        private Builder<T> withAnnotation( Annotation annotation )
        {
            this.annotation = annotation;
            return this;
        }
    }

    /**
     * Verify if an access point marked as convertable
     *
     * @param access Accessible object to verify
     * @return True if it's marked
     */
    public static boolean isConversionSupported( AccessibleObject access )
    {
        for ( Annotation a : access.getAnnotations() )
        {
            if ( a.annotationType().isAnnotationPresent( ConversionSupport.class ) )
            {
                return true;
            }
        }
        return false;
    }

    private final Converter<T> proxy;

    /**
     * @param type Type to convert from/to
     * @param access Access object where conversion annotation is placed
     */
    public AnnotatedConverter( Class<T> type, AccessibleObject access )
    {
        this.proxy = new Builder<T>().withAccess( access ).toConverter( type );
    }

    /**
     * @param type Value type to convert from/to
     * @param annotation Annotation that defines conversion rule
     */
    public AnnotatedConverter( Class<T> type, Annotation annotation )
    {
        this.proxy = new Builder<T>().withAnnotation( annotation ).toConverter( type );
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

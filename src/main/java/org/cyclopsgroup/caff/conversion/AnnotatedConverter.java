package org.cyclopsgroup.caff.conversion;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

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
                return new SimpleConverter<T>( type );
            }
            ConversionSupport support = annotation.annotationType().getAnnotation( ConversionSupport.class );
            if ( support == null )
            {
                throw new AssertionError( "Annotation " + annotation + " is not annotated with "
                    + ConversionSupport.class );
            }
            try
            {
                Converter<T> converter = support.factoryType().newInstance().getConverterFor( type, annotation );
                return new NullFriendlyConverter<T>( converter );
            }
            catch ( InstantiationException e )
            {
                throw new IllegalStateException( "Can't create converter for " + annotation, e );
            }
            catch ( IllegalAccessException e )
            {
                throw new IllegalStateException( "Can't create converter for " + annotation, e );
            }
        }

        private Builder<T> withAccess( AnnotatedElement... elements )
        {
            for ( AnnotatedElement access : elements )
            {
                if ( access == null )
                {
                    continue;
                }
                for ( Annotation a : access.getAnnotations() )
                {
                    if ( a.annotationType().isAnnotationPresent( ConversionSupport.class ) )
                    {
                        annotation = a;
                        return this;
                    }
                }
            }
            return this;
        }

        private Builder<T> withAnnotation( Annotation annotation )
        {
            this.annotation = annotation;
            return this;
        }
    }

    private final Converter<T> proxy;

    /**
     * Constructor with an annotated element with annotations
     *
     * @param type Type to convert from/to
     * @param access Access object where conversion annotation is placed
     */
    public AnnotatedConverter( Class<T> type, AnnotatedElement... access )
    {
        this.proxy = new Builder<T>().withAccess( access ).toConverter( type );
    }

    /**
     * Constructor with given Annotation
     *
     * @param type Value type to convert from/to
     * @param annotation Annotation that defines conversion rule
     */
    public AnnotatedConverter( Class<T> type, Annotation annotation )
    {
        this.proxy = new Builder<T>().withAnnotation( annotation ).toConverter( type );
    }

    /**
     * Constructor with a property descriptor
     *
     * @param type Type of value to convert
     * @param descriptor Property descriptor
     */
    public AnnotatedConverter( Class<T> type, PropertyDescriptor descriptor )
    {
        this.proxy =
            new Builder<T>().withAccess( descriptor.getReadMethod(), descriptor.getWriteMethod() ).toConverter( type );
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

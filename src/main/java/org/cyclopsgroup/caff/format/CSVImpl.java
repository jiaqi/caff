package org.cyclopsgroup.caff.format;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.AccessibleObject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.cyclopsgroup.caff.CharIterator;
import org.cyclopsgroup.caff.conversion.AnnotatedConverter;
import org.cyclopsgroup.caff.conversion.Converter;
import org.cyclopsgroup.caff.ref.ValueReference;
import org.cyclopsgroup.caff.ref.ValueReferenceScanner;

/**
 * Internal class that really does CSV format and parsing
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 * @param <T> Type of bean to convert from/to
 */
class CSVImpl<T>
{
    private class Slot
    {
        private final Converter<Object> converter;

        private final ValueReference<T> reference;

        private Slot( ValueReference<T> reference, Converter<Object> converter )
        {
            this.reference = reference;
            this.converter = converter;
        }

        private CharSequence read( T bean )
        {
            return converter.toCharacters( reference.readValue( bean ) );
        }

        private void write( T bean, CharSequence value )
        {
            reference.writeValue( converter.fromCharacters( value ), bean );
        }
    }

    private final int fields;

    private final Map<Integer, Slot> slots;

    /**
     * @param beanType Type of bean to parse or format
     */
    CSVImpl( final Class<T> beanType )
    {
        CSVType typeAnnotation = beanType.getAnnotation( CSVType.class );
        if ( typeAnnotation == null )
        {
            throw new IllegalArgumentException( "Type " + beanType + " isn't annotated with " + CSVType.class );
        }
        fields = typeAnnotation.fields();
        if ( fields <= 0 )
        {
            throw new IllegalArgumentException( "Type " + beanType + " defines invalid number of CSV fields: " + fields );
        }
        ValueReferenceScanner<T> scanner = new ValueReferenceScanner<T>( beanType );

        final Map<Integer, Slot> slots = new HashMap<Integer, Slot>();
        scanner.scanForAnnotation( CSVField.class, new ValueReferenceScanner.Listener<T, CSVField>()
        {
            @SuppressWarnings( "unchecked" )
            public void handleReference( ValueReference<T> reference, CSVField field, AccessibleObject access )
            {
                Slot slot =
                    new Slot( reference, new AnnotatedConverter<Object>( (Class<Object>) reference.getType(), access ) );
                slots.put( field.position(), slot );
            }
        } );
        this.slots = Collections.unmodifiableMap( slots );
    }

    /**
     * @param bean Bean to parse
     * @param in Char iterator of input
     * @throws IOException If IO writing fails
     */
    void populate( final T bean, CharIterator in )
        throws IOException
    {
        CSVParser parser = new CSVParser()
        {
            @Override
            protected void handleField( int position, CharSequence content )
                throws IOException
            {
                Slot slot = slots.get( position );
                if ( slot != null )
                {
                    slot.write( bean, content );
                }
            }
        };
        parser.parse( in );
    }

    /**
     * @param bean Bean to format
     * @param out Output writer
     * @throws IOException If writing fails
     */
    void print( T bean, Writer out )
        throws IOException
    {
        for ( int i = 0; i < fields; i++ )
        {
            if ( i != 0 )
            {
                out.write( ',' );
            }
            Slot slot = slots.get( i );
            if ( slot == null )
            {
                continue;
            }
            CharSequence value = slot.read( bean );
            // TODO Consider truncation and double quote
            out.write( value.toString() );
        }
    }
}

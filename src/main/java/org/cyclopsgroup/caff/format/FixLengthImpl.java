package org.cyclopsgroup.caff.format;

import java.lang.reflect.AccessibleObject;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.cyclopsgroup.caff.conversion.Converter;
import org.cyclopsgroup.caff.conversion.NullFriendlyAnnotatedConverter;
import org.cyclopsgroup.caff.ref.ValueReference;
import org.cyclopsgroup.caff.ref.ValueReferenceScanner;

/**
 * Internal actual fix-length algorithm
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of bean to format/parse
 */
class FixLengthImpl<T>
{
    private class Slot
    {
        private final Converter<Object> converter;

        private final FixLengthField field;

        private final char fill;

        private final ValueReference<T> reference;

        private Slot( FixLengthField field, Converter<Object> converter, ValueReference<T> reference )
        {
            this.field = field;
            this.converter = converter;
            this.reference = reference;
            this.fill = field.fill() == 0 ? type.fill() : field.fill();
        }
    }

    private final Map<String, Slot> slots;

    /**
     * Message level meta data
     */
    final FixLengthType type;

    /**
     * @param beanType type of bean to format/parse
     */
    FixLengthImpl( Class<T> beanType )
    {
        FixLengthType type = beanType.getAnnotation( FixLengthType.class );
        if ( type == null )
        {
            throw new IllegalArgumentException( "Type " + beanType + " isn't annotated with " + FixLengthType.class );
        }
        this.type = type;

        final Map<String, Slot> slots = new HashMap<String, Slot>();
        ValueReferenceScanner<T> scanner = new ValueReferenceScanner<T>( beanType );
        scanner.scanForAnnotation( FixLengthField.class, new ValueReferenceScanner.Listener<T, FixLengthField>()
        {
            @SuppressWarnings( "unchecked" )
            public void handleReference( ValueReference<T> reference, FixLengthField hint, AccessibleObject access )
            {
                Slot slot =
                    new Slot( hint, new NullFriendlyAnnotatedConverter<Object>( (Class<Object>) reference.getType(),
                                                                                access ), reference );
                slots.put( reference.getName(), slot );
            }
        } );
        this.slots = Collections.unmodifiableMap( slots );
    }

    /**
     * @param bean Empty bean to populate
     * @param input A line of input
     */
    void populate( T bean, CharSequence input )
    {
        for ( Slot slot : slots.values() )
        {
            if ( !slot.reference.isWritable() )
            {
                continue;
            }
            if ( slot.field.start() >= input.length() )
            {
                continue;
            }
            CharSequence content =
                input.subSequence( slot.field.start(), Math.min( slot.field.start() + slot.field.length(),
                                                                 input.length() ) );
            content = slot.field.align().trim( content, slot.fill );
            Object value = slot.converter.fromCharacters( content );
            slot.reference.writeValue( value, bean );
        }
    }

    /**
     * Print bean into given char array FIXME It's not completely working yet
     *
     * @param bean Bean to print
     * @return Char array of result
     */
    char[] print( T bean )
    {
        char[] output = new char[type.length()];
        Arrays.fill( output, type.fill() );
        for ( Slot slot : slots.values() )
        {
            if ( !slot.reference.isReadable() )
            {
                continue;
            }
            Object value = slot.reference.readValue( bean );
            CharSequence content = slot.converter.toCharacters( value );
            if ( content.length() > slot.field.length() )
            {
                content = slot.field.trim().trim( content, slot.field.length(), slot.field.align() );
            }
            slot.field.align().fill( content, output, slot.field.start(), slot.field.length(), slot.fill );

        }
        return output;
    }
}

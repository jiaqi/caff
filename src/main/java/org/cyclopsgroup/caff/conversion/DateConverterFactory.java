package org.cyclopsgroup.caff.conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class DateConverterFactory
    implements ConverterFactory
{
    private class DateConverter
        implements Converter<Date>
    {
        private final SimpleDateFormat formatter;

        private final String format;

        private DateConverter( String format )
        {
            formatter = new SimpleDateFormat( format );
            this.format = format;
        }

        public Date fromCharacters( CharSequence text )
        {
            try
            {
                return formatter.parse( text.toString() );
            }
            catch ( ParseException e )
            {
                throw new ConversionFailedException( "Can't parse " + text + " as a date in " + format, e );
            }
        }

        public CharSequence toCharacters( Date value )
        {
            return formatter.format( value );
        }
    }

    /**
     * @inheritDoc
     */
    @SuppressWarnings( "unchecked" )
    public <T> Converter<T> getConverterFor( Class<T> valueType, Object hint )
    {
        String format = ( (DateField) hint ).format();
        if ( valueType == Date.class )
        {
            return (Converter<T>) new DateConverter( format );
        }
        throw new IllegalStateException( "Type " + valueType + " is not allowed" );
    }
}

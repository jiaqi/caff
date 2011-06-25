package org.cyclopsgroup.caff.conversion;

/**
 * Converter factory for boolean conversion
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class BooleanConverterFactory
    implements ConverterFactory
{
    private static class BooleanConverter
        implements Converter<Boolean>
    {
        private BooleanConverter( BooleanField field )
        {
            this.field = field;
        }

        private final BooleanField field;

        /**
         * @inheritDoc
         */
        public Boolean fromCharacters( CharSequence text )
        {
            return text.toString().equals( field.yes() );
        }

        /**
         * @inheritDoc
         */
        public CharSequence toCharacters( Boolean value )
        {
            return value ? field.yes() : field.no();
        }
    }

    /**
     * @inheritDoc
     */
    @SuppressWarnings( "unchecked" )
    public <T> Converter<T> getConverterFor( Class<T> valueType, Object hint )
    {
        return (Converter<T>) new BooleanConverter( (BooleanField) hint );
    }
}

package org.cyclopsgroup.caff.conversion;

/**
 * Converter implementation for String
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class StringConverter
    implements Converter<String>
{
    /**
     * @inheritDoc
     */
    public String fromCharacters( CharSequence text )
    {
        return text.toString();
    }

    /**
     * @inheritDoc
     */
    public CharSequence toCharacters( String value )
    {
        return value;
    }
}

package org.cyclopsgroup.caff.conversion;

/**
 * No-op implementation of Converter for {@link CharSequence}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class CharSequenceConverter
    implements Converter<CharSequence>
{
    /**
     * @inheritDoc
     */
    public CharSequence fromCharacters( CharSequence text )
    {
        return text;
    }

    /**
     * @inheritDoc
     */
    public CharSequence toCharacters( CharSequence value )
    {
        return value;
    }
}

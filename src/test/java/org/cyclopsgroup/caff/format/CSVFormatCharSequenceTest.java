package org.cyclopsgroup.caff.format;

import java.io.IOException;

/**
 * A test case that tests {@link FixLengthFormat} with {@link CharSequence} input and output
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class CSVFormatCharSequenceTest
    extends AbstractCSVTestCase
{
    private Format<CSVBean> format = Formats.newCSVFormat( CSVBean.class );

    /**
     * @inheritDoc
     */
    @Override
    protected CSVBean fromString( String string )
        throws IOException
    {
        return format.parse( string );
    }

    /**
     * @inheritDoc
     */
    @Override
    protected String toString( CSVBean bean )
        throws IOException
    {
        return format.formatToString( bean );
    }

}

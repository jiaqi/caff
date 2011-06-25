package org.cyclopsgroup.caff.format;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * A test that verifies {@link FixLengthFormat} dealing with {@link Reader} and {@link Writer}
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class CSVFormatIOTest
    extends AbstractCSVTestCase
{
    private final Format<CSVBean> format = Formats.newCSVFormat( CSVBean.class );

    /**
     * @inheritDoc
     */
    @Override
    protected CSVBean fromString( String string )
        throws IOException
    {
        return format.parse( new StringReader( string ) );
    }

    /**
     * @inheritDoc
     */
    @Override
    protected String toString( CSVBean bean )
        throws IOException
    {
        StringWriter out = new StringWriter();
        format.print( bean, out );
        return out.toString();
    }
}

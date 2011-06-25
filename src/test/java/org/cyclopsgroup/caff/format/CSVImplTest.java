package org.cyclopsgroup.caff.format;

import java.io.IOException;
import java.io.StringWriter;

import org.cyclopsgroup.caff.CharIterator;

/**
 * Test that covers {@link CSVImpl}
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class CSVImplTest
    extends AbstractCSVTestCase
{
    private final CSVImpl<CSVBean> impl = new CSVImpl<CSVBean>( CSVBean.class );

    /**
     * @inheritDoc
     */
    @Override
    protected CSVBean fromString( String string )
        throws IOException
    {
        CSVBean bean = new CSVBean();
        impl.populate( bean, CharIterator.instanceOf( string ) );
        return bean;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected String toString( CSVBean bean )
        throws IOException
    {
        StringWriter out = new StringWriter();
        impl.print( bean, out );
        return out.toString();
    }
}

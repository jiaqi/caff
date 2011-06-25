package org.cyclopsgroup.caff.format;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.junit.Test;

/**
 * Base class of test cases that verify CSV syntax
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public abstract class AbstractCSVTestCase
    extends AbstractSyntaxSupport<CSVBean>
{
    /**
     * Parse a normal CSV string
     *
     * @throws IOException Allows IOException
     */
    @Test
    public void testParseNormally()
        throws IOException
    {
        CSVBean bean = fromString( "Rod,Bender,,35,19781204" );
        assertEquals( "Rod", bean.lastName );
        assertEquals( "Bender", bean.getFirstName() );
        assertEquals( 35, bean.getAge() );
        assertEquals( "1978-12-04", new SimpleDateFormat( "yyyy-MM-dd" ).format( bean.getBirthDay() ) );
    }

    /**
     * Verify quoting and escaping
     *
     * @throws IOException Allows IOException
     */
    @Test
    public void testWithEscaping()
        throws IOException
    {
        CSVBean bean = fromString( "Rod, \"Ben\"\"der\",,," );
        assertEquals( "Rod", bean.lastName );
        assertEquals( "Ben\"der", bean.getFirstName() );
    }

    /**
     * Verify printing code logic with a normal case
     *
     * @throws IOException Allows IOException
     */
    @Test
    public void testPrintNormally()
        throws IOException
    {
        CSVBean bean = new CSVBean();
        bean.lastName = "Rod";
        bean.setFirstName( "Bender" );
        bean.setAge( 35 );
        assertEquals( "Rod,Bender,,35,", toString( bean ) );
    }
}

package org.cyclopsgroup.caff.format;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cyclopsgroup.caff.CharIterator;
import org.junit.Test;

/**
 * Test that covers {@link CSVParser}
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class CSVParserTest
{
    private void parseAndVerify( String input, String... expectedResults )
    {
        final List<String> fields = new ArrayList<String>( expectedResults.length );
        CSVParser parser = new CSVParser()
        {
            @Override
            protected void handleField( int position, CharSequence content )
                throws IOException
            {
                fields.add( position, content.toString() );
            }
        };
        try
        {
            parser.parse( CharIterator.instanceOf( input ) );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "IOException that shoulnd't happen in test", e );
        }
        assertEquals( expectedResults.length, fields.size() );
        for ( int i = 0; i < expectedResults.length; i++ )
        {
            assertEquals( expectedResults[i], fields.get( i ) );
        }
    }

    /**
     * Verify double quote is correctly recognized
     */
    @Test
    public void testDoubleQuote()
    {
        parseAndVerify( "a, \"bb\",\"ccc\",d", "a", "bb", "ccc", "d" );
    }

    /**
     * Verify normal case without quoting or escaping
     */
    @Test
    public void testNormalCase()
    {
        parseAndVerify( "a, bb,ccc,d", "a", "bb", "ccc", "d" );
    }

    /**
     * Verify escape character is taking effect
     */
    @Test
    public void testEscaping()
    {
        parseAndVerify( "a, b\"b, \"c\"\"\", \"d\"\"d\"", "a", "b\"b", "c\"", "d\"d" );
    }
}

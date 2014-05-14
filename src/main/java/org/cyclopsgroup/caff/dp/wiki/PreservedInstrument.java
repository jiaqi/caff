package org.cyclopsgroup.caff.dp.wiki;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.cyclopsgroup.caff.dp.Instrument;

public class PreservedInstrument
    extends Instrument
{
    private static final String PREFIX = LINE_START + "  ";

    @Override
    public int close( String segment, PrintWriter out )
    {
        out.print( "</pre></p>" );
        return LINE_START.length();
    }

    @Override
    public int open( String segment, PrintWriter out )
    {
        out.print( "<p><pre>" );
        return PREFIX.length();
    }

    @Override
    public void printText( String text, PrintWriter out )
        throws IOException
    {
        if ( text.startsWith( PREFIX ) )
        {
            text =
                SystemUtils.LINE_SEPARATOR
                    + StringUtils.removeStart( text, PREFIX );
        }
        StringEscapeUtils.escapeHtml( out, text );
    }

    @Override
    public int searchToClose( String segment )
    {
        return segment.equals( LINE_START ) ? 0 : -1;
    }

    @Override
    public int searchToOpen( String segment, Instrument parent )
    {
        return parent == null && segment.startsWith( PREFIX ) ? 0 : -1;
    }
}

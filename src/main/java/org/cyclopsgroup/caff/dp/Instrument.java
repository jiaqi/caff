package org.cyclopsgroup.caff.dp;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang.StringUtils;

public abstract class Instrument
{
    public static final String LINE_START = "$LINE_START$";

    public abstract int searchToOpen( String segment, Instrument parent );

    public abstract int open( String segment, PrintWriter out );

    public abstract int searchToClose( String segment );

    public abstract int close( String segment, PrintWriter out );

    public void printText( String text, PrintWriter out )
        throws IOException
    {
        if ( text.startsWith( LINE_START ) )
        {
            text = " " + StringUtils.remove( text, LINE_START );
        }
        out.print( text );
    }
}

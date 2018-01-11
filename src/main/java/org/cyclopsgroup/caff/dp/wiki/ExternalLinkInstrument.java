package org.cyclopsgroup.caff.dp.wiki;

import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;
import org.cyclopsgroup.caff.dp.Instrument;

public class ExternalLinkInstrument
    extends Instrument
{
    @Override
    public int searchToOpen( String segment, Instrument parent )
    {
        if ( parent != null && parent instanceof PreservedInstrument )
        {
            return -1;
        }
        return segment.indexOf( '[' );
    }

    @Override
    public int open( String segment, PrintWriter out )
    {
        int end = segment.indexOf( ']' );
        String content = segment.substring( 1, end );
        String[] parts = StringUtils.split( content, '|' );

        String href, text;
        if ( parts.length == 1 )
        {
            href = content;
            text = content;
        }
        else
        {
            href = parts[0];
            text = parts[1];
        }
        printLink( href, text, out );
        return end;
    }

    private void printLink( String href, String text, PrintWriter out )
    {
        if ( text == null )
        {
            text = "link";
        }
        out.write( " <a href=\"" + href + "\">" + text + "</a> " );
    }

    @Override
    public int searchToClose( String segment )
    {
        return segment.indexOf( ']' );
    }

    @Override
    public int close( String segment, PrintWriter out )
    {
        return 1;
    }
}

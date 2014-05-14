package org.cyclopsgroup.caff.dp;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class InstrumentedDocumentProcessor
    implements DocumentProcessor
{
    private final List<Instrument> instruments;

    public InstrumentedDocumentProcessor( List<Instrument> instruments )
    {
        this.instruments =
            Collections.unmodifiableList( new ArrayList<Instrument>(
                                                                     instruments ) );
    }

    private void doProcess( LineNumberReader input, PrintWriter output )
        throws IOException
    {
        Deque<Instrument> stack = new ArrayDeque<Instrument>();
        for ( String line = input.readLine(); line != null; line =
            input.readLine() )
        {
            try
            {
                processSegment( Instrument.LINE_START + line, output, stack );
            }
            catch ( RuntimeException e )
            {
                throw new IOException( "Can't parse line "
                    + input.getLineNumber() + ":" + line, e );
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void process( Reader input, Writer output )
        throws IOException
    {
        LineNumberReader in;
        if ( input instanceof LineNumberReader )
        {
            in = (LineNumberReader) input;
        }
        else
        {
            in = new LineNumberReader( input );
        }

        PrintWriter out;
        if ( output instanceof PrintWriter )
        {
            out = (PrintWriter) output;
        }
        else
        {
            out = new PrintWriter( output );
        }
        doProcess( in, out );
    }

    private void processSegment( String segment, PrintWriter out,
                                 Deque<Instrument> stack )
        throws IOException
    {
        int position = segment.length() + 1;
        Instrument selected = null;
        boolean toClose = false;

        for ( Instrument i : instruments )
        {
            int p = i.searchToOpen( segment, stack.peek() );
            if ( p == -1 )
            {
                continue;
            }
            if ( p < position )
            {
                selected = i;
                position = p;
            }
        }

        if ( stack.peek() != null )
        {
            int p = stack.peek().searchToClose( segment );
            if ( p != -1 && p < position )
            {
                selected = stack.peek();
                toClose = true;
                position = p;
            }
        }

        if ( selected == null )
        {
            writeText( segment, stack, out );
            return;
        }
        if ( position > 0 )
        {
            writeText( segment.substring( 0, position ), stack, out );
        }

        int consumed;
        if ( toClose )
        {
            consumed = stack.pop().close( segment.substring( position ), out );
        }
        else
        {
            consumed = selected.open( segment.substring( position ), out );
            stack.push( selected );
        }
        if ( position + consumed < segment.length() )
        {
            processSegment( segment.substring( position + consumed ), out,
                            stack );
        }
    }

    private void writeText( String text, Deque<Instrument> stack,
                            PrintWriter output )
        throws IOException
    {
        if ( stack.peek() == null )
        {
            output.print( StringUtils.removeStart( text, Instrument.LINE_START ) );
        }
        else
        {
            stack.peek().printText( text, output );
        }
    }
}

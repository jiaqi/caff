package org.cyclopsgroup.caff.token;

/**
 * String tokenizer which split string into segments considering quotation and character escaping
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class QuotedValueTokenizer
    implements ValueTokenizer
{
    private enum ParsingState
    {
        /**
         * Ready for escaping next character or end a quoted segment
         */
        ESCAPED_OR_QUOTE_END,
        /**
         * Quotation started
         */
        QUOTED,
        /**
         * Ready for new word
         */
        READY,
        /**
         * Word started without quotation
         */
        WORD_STARTED;
    }

    private final char delimiter;

    private final char quotation;

    /**
     * Default constructor that uses white space as delimiter and &quot; as quotation character
     */
    public QuotedValueTokenizer()
    {
        this( ' ', '\"' );
    }

    /**
     * @param delimiter Delimiter character
     * @param quotation Quotation character
     */
    public QuotedValueTokenizer( char delimiter, char quotation )
    {
        this.delimiter = delimiter;
        this.quotation = quotation;
    }

    /**
     * @inheritDoc
     */
    public void parse( CharSequence input, TokenEventHandler handler )
    {
        ParsingState state = ParsingState.READY;
        StringBuilder buf = null;
        int wordStart = 0;
        for ( int i = 0; i < input.length(); i++ )
        {
            char c = input.charAt( i );
            switch ( state )
            {
                case READY:
                    assert buf == null;
                    if ( c == delimiter )
                    {
                        continue;
                    }
                    else if ( c == quotation )
                    {
                        state = ParsingState.QUOTED;
                        wordStart = i;
                        buf = new StringBuilder();
                    }
                    else
                    {
                        state = ParsingState.WORD_STARTED;
                        wordStart = i;
                        buf = new StringBuilder();
                        buf.append( c );
                    }
                    break;
                case WORD_STARTED:
                    assert buf != null;
                    if ( c == delimiter )
                    {
                        state = ParsingState.READY;
                        handler.handleEvent( new TokenEvent( buf.toString(), wordStart, i, true ) );
                        buf = null;
                    }
                    else
                    {
                        buf.append( c );
                    }
                    break;
                case QUOTED:
                    assert buf != null;
                    if ( c == quotation )
                    {
                        state = ParsingState.ESCAPED_OR_QUOTE_END;
                    }
                    else
                    {
                        buf.append( c );
                    }
                    break;
                case ESCAPED_OR_QUOTE_END:
                    assert buf != null;
                    if ( c == delimiter )
                    {
                        state = ParsingState.READY;
                        handler.handleEvent( new TokenEvent( buf.toString(), wordStart, i, true, true ) );
                        buf = null;
                    }
                    else
                    {
                        buf.append( c );
                        state = ParsingState.QUOTED;
                    }
                    break;
            }
        }
        if ( buf != null )
        {
            handler.handleEvent( new TokenEvent( buf.toString(), wordStart, input.length(), false,
                                                 state == ParsingState.QUOTED ) );
        }
    }

    public String escape( String output )
    {
        int d = output.indexOf( delimiter );
        int q = output.indexOf( quotation );

        if ( d == -1 && q == -1 )
        {
            return output;
        }
        StringBuffer sb = new StringBuffer().append( quotation );
        for ( int i = 0, j = 0; i < output.length(); )
        {
            j = output.indexOf( quotation, i );
            if ( j == -1 )
            {
                sb.append( output.substring( i ) );
                break;
            }
            sb.append( output.substring( i, j ) ).append( quotation ).append( output.charAt( j ) );
            i = ++j;
        }
        sb.append( quotation );
        return sb.toString();
    }
}

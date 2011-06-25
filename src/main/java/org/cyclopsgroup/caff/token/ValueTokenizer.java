package org.cyclopsgroup.caff.token;

/**
 * A parser that reads input and generates a series of {@link TokenEvent}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public interface ValueTokenizer
{
    /**
     * Format output according to the parsing rule
     *
     * @param output Output to escape
     * @return Output after escape
     */
    String escape( String output );

    /**
     * Parse input and generate events to given event handler
     *
     * @param input Characters of input
     * @param handler Handler of token events
     */
    void parse( CharSequence input, TokenEventHandler handler );
}

package org.cyclopsgroup.caff;

import java.io.IOException;
import java.io.Reader;

/**
 * An internal class to allow to iterator through a sequence of characters
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public abstract class CharIterator
{
    /**
     * @return True if there's remaining character to read
     */
    public abstract boolean hasNext();

    /**
     * @return Next character from input
     * @throws IOException Allows IO exceptions
     */
    public abstract char next()
        throws IOException;

    /**
     * @param seq Char sequence as input
     * @return A implementation to read {@link CharSequence}
     */
    public static CharIterator instanceOf( final CharSequence seq )
    {
        return new CharIterator()
        {
            private int position = 0;

            public boolean hasNext()
            {
                return position < seq.length();
            }

            public char next()
            {
                return seq.charAt( position++ );
            }
        };
    }

    /**
     * @param reader IO reader as input
     * @return An implementation to read from {@link Reader}
     *
     * @throws IOException Allows reading exception from {@link Reader}
     */
    public static CharIterator instanceOf( final Reader reader )
        throws IOException
    {
        return new CharIterator()
        {
            private int next = reader.read();

            @Override
            public boolean hasNext()
            {
                return next != -1;
            }

            @Override
            public char next()
                throws IOException
            {
                char n = (char) next;
                next = reader.read();
                return n;
            }
        };
    }
}

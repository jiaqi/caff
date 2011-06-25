package org.cyclopsgroup.caff;

/**
 * CharSequence implementation based on a char array. This class is immutable and threadsafe
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class CharArrayCharSequence
    implements CharSequence
{
    private final char[] chars;

    private final int length;

    private final int start;

    private static char[] createCopy( char[] chars )
    {
        char[] copy = new char[chars.length];
        System.arraycopy( chars, 0, copy, 0, chars.length );
        return copy;
    }

    /**
     * Create new instance for given char[]. This constructor does defensive copy for char[] input.
     *
     * @param chars Char array of the whole content
     */
    public CharArrayCharSequence( char[] chars )
    {
        this( createCopy( chars ), 0, chars.length );
    }

    /**
     * @param chars Char array that contains the content
     * @param start Start position of content
     * @param length Length of content
     */
    private CharArrayCharSequence( char[] chars, int start, int length )
    {
        if ( start + length > chars.length )
        {
            throw new IndexOutOfBoundsException( "Length " + length + " overflow" );
        }
        this.chars = chars;
        this.start = start;
        this.length = length;
    }

    /**
     * @inheritDoc
     */
    public char charAt( int index )
    {
        if ( index >= length )
        {
            throw new IndexOutOfBoundsException( "Index " + index + " is greater than length " + length );
        }
        return chars[index + start];
    }

    /**
     * @inheritDoc
     */
    public int length()
    {
        return length;
    }

    /**
     * @inheritDoc
     */
    public CharSequence subSequence( int start, int end )
    {
        if ( end < start )
        {
            throw new IndexOutOfBoundsException( "Invalid input start=" + start + ", end=" + end );
        }
        if ( start == 0 && end == length )
        {
            return this;
        }
        return new CharArrayCharSequence( chars, this.start + start, end - start );
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString()
    {
        return new String( chars, start, length );
    }

    /**
     * Utility method to converter {@link CharSequence} to char array
     *
     * @param seq Given sequence object
     * @return An array that has same content
     */
    public static char[] sequenceToArray( CharSequence seq )
    {
        if ( seq == null )
        {
            throw new NullPointerException( "CharSequence can't be NULL" );
        }
        char[] chars = new char[seq.length()];
        for ( int i = 0; i < seq.length(); i++ )
        {
            chars[i] = seq.charAt( i );
        }
        return chars;
    }
}

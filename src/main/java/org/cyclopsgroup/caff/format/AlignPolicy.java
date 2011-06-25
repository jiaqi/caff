package org.cyclopsgroup.caff.format;

/**
 * Defines should field value align left or right
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public enum AlignPolicy
{
    /**
     * Left is default value, truncate right.
     */
    LEFT
    {
        @Override
        CharSequence trim( CharSequence input, char empty )
        {
            int lengthToKeep = input.length();
            while ( lengthToKeep > 0 && input.charAt( lengthToKeep - 1 ) == empty )
            {
                lengthToKeep--;
            }
            return input.subSequence( 0, lengthToKeep );
        }

        @Override
        void fill( CharSequence src, char[] dest, int start, int length, char empty )
        {
            for ( int i = 0; i < length; i++ )
            {
                char c;
                if ( i < src.length() )
                {
                    c = src.charAt( i );
                }
                else
                {
                    c = empty;
                }
                dest[start + i] = c;
            }
        }
    },
    /**
     * Align right, truncate left.
     */
    RIGHT
    {
        @Override
        CharSequence trim( CharSequence input, char empty )
        {
            int start = 0;
            while ( start < input.length() && input.charAt( start ) == empty )
            {
                start++;
            }
            return input.subSequence( start, input.length() );
        }

        @Override
        void fill( CharSequence src, char[] dest, int start, int length, char empty )
        {
            for ( int i = 0; i < length; i++ )
            {
                char c;
                if ( i < src.length() )
                {
                    c = src.charAt( src.length() - i - 1 );
                }
                else
                {
                    c = empty;
                }
                dest[start + length - i - 1] = c;
            }
        }
    };

    /**
     * Trim the leading or trailing empty characters based on alignment
     *
     * @param input Content that possibly has empty character
     * @param empty Character that stands for empty
     * @return Trimmed result
     */
    abstract CharSequence trim( CharSequence input, char empty );

    /**
     * Fill content into given char array
     *
     * @param src Content to fill
     * @param dest Char array that content is filled into
     * @param start Zero based start position of char array
     * @param length Length of slot to fill in char array
     * @param empty Empty character to fill
     */
    abstract void fill( CharSequence src, char[] dest, int start, int length, char empty );
}

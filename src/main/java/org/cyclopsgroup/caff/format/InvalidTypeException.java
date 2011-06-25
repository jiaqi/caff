package org.cyclopsgroup.caff.format;

/**
 * Exception that tells class definition of a bean has problem
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class InvalidTypeException
    extends FormatException
{
    private static final long serialVersionUID = 1013242349732621546L;

    /**
     * @param message Descriptive message
     * @param cause Root cause exception
     */
    InvalidTypeException( String message, Throwable cause )
    {
        super( message, cause );
    }
}

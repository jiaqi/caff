package org.cyclopsgroup.caff.format;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a field as fix length field
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @see {@link FixLengthType}
 */
@Documented
@Target( { ElementType.FIELD, ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
public @interface FixLengthField
{
    /**
     * @return Align left or right
     */
    AlignPolicy align() default AlignPolicy.LEFT;

    /**
     * @return Fill empty slots with given character. Default value is what {@link FixLengthType#fill()} specifies.
     */
    char fill() default 0;

    /**
     * @return Number of characters in this field
     */
    int length();

    /**
     * @return 0 based starting position of field
     */
    int start();

    /**
     * @return Handle the value that is longer than limit
     */
    TrimPolicy trim() default TrimPolicy.FORWARD;
}

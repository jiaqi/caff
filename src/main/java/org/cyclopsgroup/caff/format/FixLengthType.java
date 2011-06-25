package org.cyclopsgroup.caff.format;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that marks a bean as fix length message
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixLengthType
{
    /**
     * @return Character that fills empty slots
     */
    char fill() default ' ';

    /**
     * @return Total length of fix length format
     */
    int length();
}

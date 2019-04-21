package org.cyclopsgroup.caff.format;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for a CSV formatted Java bean. When a bean is flagged with this annotation, {@link
 * CSVFormat} is able to parse or format it.
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CSVType {
  /** @return Total number of fields in a line */
  int fields();
}

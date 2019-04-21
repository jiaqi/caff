package org.cyclopsgroup.caff.conversion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a field that is converted as date type
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@ConversionSupport(factoryType = DateConverterFactory.class)
public @interface DateField {
  /** @return Simple date format of date syntax */
  String format() default "yyyy-MM-dd'T'HH:mm:ss.SSS";
}

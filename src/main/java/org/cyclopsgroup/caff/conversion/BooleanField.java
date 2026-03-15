package org.cyclopsgroup.caff.conversion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Field for boolean object conversion
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@ConversionSupport(factoryType = BooleanConverterFactory.class)
public @interface BooleanField {
  /**
   * @return String form for true value
   */
  String yes() default "yes";

  /**
   * @return String form for false value
   */
  String no() default "no";
}

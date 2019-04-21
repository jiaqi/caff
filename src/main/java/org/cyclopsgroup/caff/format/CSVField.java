package org.cyclopsgroup.caff.format;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Flag field as a CSV field
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface CSVField {
  /** @return Always wrap value with double quots */
  boolean alwaysQuote() default false;

  /**
   * @return Max number of characters the field can contain. Default value is -1 which means field
   *     length is unlimited
   */
  int maxLength() default -1;

  /** @return Zero based position of field */
  int position();
}

package org.cyclopsgroup.caff.format;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that marks a field as fix length field.
 * 
 * @see FixLengthType
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FixLengthField {
  /**
   * @return align left or right.
   */
  AlignPolicy align() default AlignPolicy.LEFT;

  /**
   * @return fill empty slots with given character. Default value is what
   *         {@link FixLengthType#fill()} specifies.
   */
  char fill() default 0;

  /**
   * @return number of characters in this field.
   */
  int length();

  /**
   * @return 0 based starting position of field.
   */
  int start();

  /**
   * @return the handle the value that is longer than limit.
   */
  TrimPolicy trim() default TrimPolicy.FORWARD;
}

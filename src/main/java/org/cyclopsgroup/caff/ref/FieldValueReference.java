package org.cyclopsgroup.caff.ref;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * Implementation of value reference that based on a public field
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of value
 */
class FieldValueReference<T> extends ValueReference<T> {
  private final Field field;
  private final boolean publicField;

  /**
   * @param field Reflection field object
   */
  FieldValueReference(Field field) {
    this.field = Preconditions.checkNotNull(field, "An input field is required.");
    this.publicField = (field.getModifiers() & Modifier.PUBLIC) > 0;
  }

  @Override
  public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
    return field.getAnnotation(annotationType);
  }

  @Override
  public ImmutableList<AnnotatedElement> getAnontatedElements() {
    return ImmutableList.of(field);
  }

  @Override
  public String getName() {
    return field.getName();
  }

  @Override
  public Class<?> getType() {
    return field.getType();
  }

  @Override
  public boolean isReadable() {
    return true;
  }

  @Override
  public boolean isWritable() {
    return !Modifier.isFinal(field.getModifiers());
  }

  @Override
  public Object readValue(final T owner) throws AccessFailureException {
    if (!publicField && !field.isAccessible()) {
      field.setAccessible(true);
    }
    try {
      return field.get(owner);
    } catch (IllegalAccessException e) {
      throw new AccessFailureException("Can't get field " + field + " of " + owner, e);
    }
  }

  @Override
  public void writeValue(Object value, T owner) throws AccessFailureException {
    if (value == null && field.getType().isPrimitive()) {
      return;
    }
    if (!publicField && !field.isAccessible()) {
      field.setAccessible(true);
    }
    try {
      field.set(owner, value);
    } catch (IllegalAccessException e) {
      throw new AccessFailureException("Can't set field " + field + " of " + owner + " to " + value,
          e);
    }
  }
}

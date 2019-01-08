package org.cyclopsgroup.caff.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import com.google.common.base.Preconditions;

/**
 * Implementation of value reference that based on a public field
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of value
 */
class FieldValueReference<T> extends ValueReference<T> {
  private final Field field;

  /**
   * @param field Reflection field object
   */
  FieldValueReference(Field field) {
    this.field = Preconditions.checkNotNull(field, "An input field is required.");
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
    try {
      return AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
        @Override
        public Object run() throws IllegalAccessException {
          return field.get(owner);
        }
      });
    } catch (PrivilegedActionException e) {
      throw new AccessFailureException("Can't get field " + field + " of " + owner, e);
    }
  }

  @Override
  public void writeValue(Object value, T owner) throws AccessFailureException {
    if (value == null && field.getType().isPrimitive()) {
      return;
    }
    try {
      field.set(owner, value);
    } catch (IllegalAccessException e) {
      throw new AccessFailureException("Can't set field " + field + " of " + owner + " to " + value,
          e);
    }
  }
}

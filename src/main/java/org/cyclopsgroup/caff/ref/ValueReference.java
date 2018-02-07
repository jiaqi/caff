package org.cyclopsgroup.caff.ref;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * Interface that allow to read a value from given owner or write value to owner
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of owner object
 */
public abstract class ValueReference<T> {
  /**
   * @param <T> Type of owner
   * @param field A reflective field
   * @return Value reference for given field
   */
  public static <T> ValueReference<T> instanceOf(Field field) {
    return new FieldValueReference<T>(field);
  }

  /**
   * @param <T> Type of owner
   * @param prop A Java bean field descriptor
   * @return Value reference for given descriptor
   */
  public static <T> ValueReference<T> instanceOf(PropertyDescriptor prop) {
    return new PropertyValueReference<T>(prop);
  }

  /**
   * @return A unique name for this holder
   */
  public abstract String getName();

  /**
   * @return Type of value
   */
  public abstract Class<?> getType();

  /**
   * @return True if value really is readable
   */
  public abstract boolean isReadable();

  /**
   * @return True if value really is writable
   */
  public abstract boolean isWritable();

  /**
   * @param owner Object from which value is read
   * @return Reading result
   * @throws AccessFailureException If reading operation failed
   */
  public abstract Object readValue(T owner) throws AccessFailureException;

  /**
   * @param value Value to write to owner
   * @param owner Owner object where value is written to
   * @throws AccessFailureException If writing operation failed
   */
  public abstract void writeValue(Object value, T owner) throws AccessFailureException;
}

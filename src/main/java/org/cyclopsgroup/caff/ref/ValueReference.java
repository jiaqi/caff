package org.cyclopsgroup.caff.ref;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * Interface that allow to read a value from given owner or write value to owner
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of owner object
 */
public abstract class ValueReference<T> {
  @Nullable
  private static Method findMethod(Class<?> type, String methodName, Class<?>... parameterTypes)
      throws SecurityException {
    try {
      return type.getDeclaredMethod(methodName, parameterTypes);
    } catch (NoSuchMethodException e) {
    }
    try {
      return type.getMethod(methodName, parameterTypes);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }

  public static <T> ValueReference<T> forProperty(String propertyName, Class<T> type,
      Class<?> propertyType) {
    Preconditions.checkArgument(propertyName != null && !propertyName.isEmpty(),
        "Invalid property name " + propertyName);
    Preconditions.checkNotNull(type, "Bean type can't be null.");
    String name = Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
    Method getter = findMethod(type, "get" + name);
    if (getter != null) {
      Preconditions.checkArgument(propertyType.isAssignableFrom(getter.getReturnType()),
          "Property type is %s while getter returns %s.", propertyType, getter.getReturnType());
    }
    Method setter = findMethod(type, "set" + name, propertyType);
    return new PropertyValueReference<>(propertyName, propertyType, getter, setter);
  }

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
   * Gets annotation with given type or null if not found.
   * 
   * @param annotationType the type of annotation to find.
   * @param <A> the type of annotation to find and return.
   * @return The annotation that matches given type or null if nothing is found.
   */
  @Nullable
  public abstract <A extends Annotation> A getAnnotation(Class<A> annotationType);

  /**
   * Get all annotated elements.
   * 
   * @return List of found annotated elements.
   */
  public abstract ImmutableList<AnnotatedElement> getAnontatedElements();

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

package org.cyclopsgroup.caff.ref;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Implementation of value holder based on reader and writer methods
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of owner object
 */
class PropertyValueReference<T> extends ValueReference<T> {
  private static Method nullIfNotPublic(Method method) {
    if (method == null) {
      return null;
    }
    return Modifier.isPublic(method.getModifiers()) ? method : null;
  }

  private final String name;

  private final Method reader;

  private final Class<?> type;

  private final Method writer;

  /**
   * @param descriptor Property descriptor of property
   */
  PropertyValueReference(PropertyDescriptor descriptor) {
    name = descriptor.getName();
    reader = nullIfNotPublic(descriptor.getReadMethod());
    writer = nullIfNotPublic(descriptor.getWriteMethod());
    type = descriptor.getPropertyType();
  }

  @Override
  public final String getName() {
    return name;
  }

  @Override
  public Class<?> getType() {
    return type;
  }

  @Override
  public boolean isReadable() {
    return reader != null;
  }

  @Override
  public boolean isWritable() {
    return writer != null;
  }

  @Override
  public Object readValue(T owner) {
    if (reader == null) {
      throw new IllegalStateException("Property " + name + " isn't readable");
    }
    try {
      return reader.invoke(owner);
    } catch (IllegalAccessException e) {
      throw new AccessFailureException("Can't read property " + name + " from object " + owner, e);
    } catch (InvocationTargetException e) {
      throw new AccessFailureException("Can't read property " + name + " from object " + owner, e);
    }
  }

  @Override
  public void writeValue(Object value, T owner) {
    if (writer == null) {
      throw new IllegalStateException("Property " + name + " isn't writable");
    }
    if (value == null && type.isPrimitive()) {
      return;
    }
    try {
      writer.invoke(owner, value);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(
          "Can't set property " + name + " of object " + owner + " to " + value, e);
    } catch (IllegalAccessException e) {
      throw new AccessFailureException(
          "Can't set property " + name + " of object " + owner + " to " + value, e);
    } catch (InvocationTargetException e) {
      throw new AccessFailureException(
          "Can't set property " + name + " of object " + owner + " to " + value, e);
    }
  }
}

package org.cyclopsgroup.caff.ref;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

/**
 * Implementation of value holder based on reader and writer methods
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of owner object
 */
class PropertyValueReference<T> extends ValueReference<T> {
  private final String name;
  private final Method reader;
  private final boolean readerPublic;
  private final Class<?> type;
  private final Method writer;
  private final boolean writerPublic;

  /** @param descriptor Property descriptor of property */
  PropertyValueReference(PropertyDescriptor descriptor) {
    this(
        descriptor.getName(),
        descriptor.getPropertyType(),
        descriptor.getReadMethod(),
        descriptor.getWriteMethod());
  }

  PropertyValueReference(
      String name, Class<?> type, @Nullable Method reader, @Nullable Method writer) {
    this.name = Preconditions.checkNotNull(name, "Property name is required.");
    this.type = Preconditions.checkNotNull(type, "Property type is required.");
    this.reader = reader;
    this.readerPublic = reader != null && (reader.getModifiers() & Modifier.PUBLIC) > 0;
    this.writer = writer;
    this.writerPublic = writer != null && (writer.getModifiers() & Modifier.PUBLIC) > 0;
  }

  @Override
  public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
    return Arrays.asList(reader, writer).stream()
        .filter(m -> m != null)
        .map(m -> m.getAnnotation(annotationType))
        .filter(a -> a != null)
        .findAny()
        .orElse(null);
  }

  @Override
  public ImmutableList<AnnotatedElement> getAnontatedElements() {
    return FluentIterable.<AnnotatedElement>of(reader, writer).filter(e -> e != null).toList();
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
    if (!readerPublic && !reader.isAccessible()) {
      reader.setAccessible(true);
    }
    try {
      return reader.invoke(owner);
    } catch (IllegalAccessException | InvocationTargetException e) {
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
    if (!writerPublic && !writer.isAccessible()) {
      writer.setAccessible(true);
    }
    try {
      writer.invoke(owner, value);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new IllegalStateException(
          "Can't set property " + name + " of object " + owner + " to " + value, e);
    }
  }
}

package org.cyclopsgroup.caff.conversion;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Converter that converts based on rules defined in annotation
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of value to converter from/to
 */
public class AnnotatedConverter<T> implements Converter<T> {
  private static class Builder<T> {
    private Annotation annotation;

    @SuppressWarnings("unchecked")
    private Converter<T> toConverter(Class<T> type) {
      if (annotation == null) {
        return new SimpleConverter<T>(type);
      }
      ConversionSupport support =
          annotation.annotationType().getAnnotation(ConversionSupport.class);
      if (support == null) {
        throw new AssertionError(
            "Annotation " + annotation + " is not annotated with " + ConversionSupport.class);
      }
      try {
        ConverterFactory<T> factory = (ConverterFactory<T>) support.factoryType().newInstance();
        Converter<T> converter = factory.getConverterFor(type, annotation);
        return new NullFriendlyConverter<T>(converter);
      } catch (InstantiationException e) {
        throw new IllegalStateException("Can't create converter for " + annotation, e);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException("Can't create converter for " + annotation, e);
      }
    }

    private Builder<T> withAccess(ImmutableList<AnnotatedElement> elements) {
      for (AnnotatedElement access : elements) {
        if (access == null) {
          continue;
        }
        for (Annotation a : access.getAnnotations()) {
          if (a.annotationType().isAnnotationPresent(ConversionSupport.class)) {
            annotation = a;
            return this;
          }
        }
      }
      return this;
    }

    private Builder<T> withAnnotation(Annotation annotation) {
      this.annotation = annotation;
      return this;
    }
  }

  private final Converter<T> proxy;

  /**
   * Constructor with an annotated element with annotations.
   *
   * @param type the kind of bean to convert from/to.
   * @param firstElement is the first element in array to add.
   * @param additionalElements the rest of elements as an array.
   */
  public AnnotatedConverter(
      Class<T> type, AnnotatedElement firstElement, AnnotatedElement... additionalElements) {
    this(type, FluentIterable.of(firstElement).append(additionalElements).toList());
  }

  /**
   * Constructor with given Annotation
   *
   * @param type Value type to convert from/to
   * @param annotation Annotation that defines conversion rule
   */
  public AnnotatedConverter(Class<T> type, Annotation annotation) {
    this.proxy = new Builder<T>().withAnnotation(annotation).toConverter(type);
  }

  /**
   * Constructor with an annotated element with annotations.
   *
   * @param type Type to convert from/to.
   * @param elements Iterable of annotated elements.
   */
  public AnnotatedConverter(Class<T> type, Iterable<AnnotatedElement> elements) {
    this.proxy = new Builder<T>().withAccess(ImmutableList.copyOf(elements)).toConverter(type);
  }

  /**
   * Constructor with a property descriptor
   *
   * @param type Type of value to convert
   * @param descriptor Property descriptor
   */
  public AnnotatedConverter(Class<T> type, PropertyDescriptor descriptor) {
    this.proxy =
        new Builder<T>()
            .withAccess(ImmutableList.of(descriptor.getReadMethod(), descriptor.getWriteMethod()))
            .toConverter(type);
  }

  @Override
  public T fromCharacters(CharSequence text) {
    return proxy.fromCharacters(text);
  }

  @Override
  public CharSequence toCharacters(T value) {
    return proxy.toCharacters(value);
  }
}

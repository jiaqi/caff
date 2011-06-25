package org.cyclopsgroup.caff.conversion;

/**
 * Interface that manages what converter to use
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public interface ConverterFactory
{
    /**
     * @param <T> Type of value to convert from/to
     * @param valueType Type of value to convert from/to
     * @param hint A optional object, value is determined by implementation
     * @return Converter instance that does actual conversion
     */
    <T> Converter<T> getConverterFor(Class<T> valueType, Object hint );
}

package org.cyclopsgroup.caff;

/**
 * A class that defines a primitive identifier. When an emnu implements this interface, the conversion framework will
 * convert enum to its identifier instead of name, and vice versa.
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 * @param <T> Type of identifier
 */
public interface NormalizedValue<T>
{
    /**
     * @return Unified identifier of this value
     */
    T getIdentifier();
}

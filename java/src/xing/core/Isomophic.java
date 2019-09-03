package xing.core;

/**
 * Interface for classes having isomophic property.
 * @author Xing Yu
 */
public interface Isomophic<T>
{
    /**
     * Check if the instance is isomophic to another
     * @param other instance to compare with
     * @return true if two instances are isomophic
     */
    public boolean isomophicTo(final T other);
} // interface Isomophic

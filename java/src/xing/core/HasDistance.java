package xing.core;

/**
 * For classes having distance property.
 * @author Xing Yu
 */
public interface HasDistance<E>
{
    /**
     * Calculate distance to another vertex in the graph
     * @param other instance to compute the distance
     * @return real value distance
     */
    public double distanceTo(final E other);
} // interface HasDistance

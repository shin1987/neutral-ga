package xing.core;

/**
 * Utilities for histogram computation
 * @author Xing Yu
 */
public interface OutputHistogram extends InputHistogram
{
    /**
     * Vote for a value, the bin index is calcualted based on input value
     * @param x value from which the bin index is calcualted
     * @param weight weight of the vote
     */
    public void vote(final double x, final double weight);
    /**
     * Vote into a specific bin
     * @param index index of the bin
     * @param weight weight of the vote
     */
    public void voteInto(final int index, final double weight);
} // interface Histogram

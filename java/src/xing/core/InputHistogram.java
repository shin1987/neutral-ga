package xing.core;

/**
 * Interface represent a read-only histogram.
 * @author Xing Yu
 */
public interface InputHistogram
{
    public int       getNumberOfBins();
    public double    getStepSize();
    public double    getLowerBound();
    public double    getUpperBound();
    /**
     * Get number of votes in a bin.
     * @param index index to the bin
     * @return number of votes
     */
    public double    get(final int index);
    /**
     * Conver the histogram to raw array
     * @return histogram array whose length equals to number of bins
     */
    public double [] toArray();
    public double    getTotalVote();

    /**
     * Normalise histogram
     * @return a normalised version of the histogram
     */
    public InputHistogram normalise();
} // interface InputHistogram

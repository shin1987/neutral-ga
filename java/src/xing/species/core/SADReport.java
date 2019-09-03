package xing.species.core;
import xing.core.InputHistogram;

/**
 * @author Xing Yu
 */
public interface SADReport
{
    /**
     * Report species abundance distribution as a histogram.
     * @param sad distribution
     * @return histogram summarise sad
     */
    public InputHistogram report(final int [] sad);
} // interface SADReport

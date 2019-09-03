package xing.base;
import xing.core.InputHistogram;
import xing.core.OutputHistogram;
import java.util.Arrays;

/**
 * @author Xing Yu
 */
public final class Histogram implements OutputHistogram
{
    public static final int DEFAULT_BIN_NUM = 10;
    public static final double DEFAULT_STEP = 1.;
    public static final double DEFAULT_LOWER_BOUND = 0.;
    public static final double PRECISION = 1e-8;
    
    private final double step, lower;
    private final double [] histogram;
    private double sum = 0;

    public static class Builder
    {
        private Double step = null, lower = null;
        private Integer n = null;

        public Builder setNumberOfBins(final int N)
        {
            this.n = N;
            return this;
        }

        public Builder setStepSize(final double step)
        {
            this.step = step;
            return this;
        }

        public Builder setLowerBound(final double bound)
        {
            this.lower = bound;
            return this;
        }

        public Histogram getInstance(final double [] x, final double [] w)
        {
            lower = lower == null ? Double.POSITIVE_INFINITY : lower;
            double max = Double.NEGATIVE_INFINITY;
            for (double value : x)
                max = Math.max(max, value);
            // Fix lower
            for (double value : x)
                lower = Math.min(lower, value);

            double range = max - lower;
            range = (range == 0) ? PRECISION : range;
            
            if (n == null && step != null) { // derive n
                n = (int)Math.ceil(range * (1. + PRECISION) / step);
            } else if (step == null) { // n is set
                n = (n == null) ? DEFAULT_BIN_NUM : n;
                step = range * (1. + PRECISION) / n;
            } else if (n != null && lower + n * step <= max) {
                step = range * (1. + PRECISION) / n;
            }
            
            double [] hist = new double[n];
            Arrays.fill(hist, 0);
            Histogram ret = new Histogram(step, lower, hist, 0.0);

            for (int i = 0; i < x.length; ++i)
                ret.vote(x[i], w[i]);
            return ret;
        }
        
        public Histogram getInstance()
        {
            n = n == null ? DEFAULT_BIN_NUM : n;
            lower = lower == null ? DEFAULT_LOWER_BOUND : lower;
            step = step == null ? DEFAULT_STEP : step;
            
            double [] hist = new double[n];
            Arrays.fill(hist, 0);
            return new Histogram(step, lower, hist, 0.0);
        }
        
        public Histogram getInstance(final double [] histogram)
        {
            lower = lower == null ? DEFAULT_LOWER_BOUND : lower;
            step = step == null ? DEFAULT_STEP : step;
            double sum = 0.0;
            for (double x : histogram)
                sum += x;
            double [] hist = Arrays.copyOf(histogram, histogram.length);
            
            return new Histogram(step, lower, hist, sum);
        }
    }

    private Histogram(final double step,
                      final double lower,
                      final double [] histogram,
                      final double sum)
    {
        this.step = step;
        this.lower = lower;
        this.histogram = histogram;
        this.sum = sum;
    }
                      
    @Override
    public double getLowerBound()
    {
        return lower;
    }

    @Override
    public double getUpperBound()
    {
        return getLowerBound() + getStepSize() * getNumberOfBins();
    }
    
    @Override
    public double getStepSize()
    {
        return step;
    }

    @Override
    public int getNumberOfBins()
    {
        return histogram.length;
    }

    @Override
    public void voteInto(final int index, final double vote)
    {
        histogram[index] += vote;
        sum += vote;
    }

    @Override
    public void vote(final double x, final double vote)
    {
        double range = x - getLowerBound();
        double scale = range / getStepSize();
        voteInto((int)Math.floor(scale), vote);
    }

    @Override
    public double getTotalVote()
    {
        return sum;
    }

    @Override
    public double [] toArray()
    {
        return Arrays.copyOf(histogram, histogram.length);
    }

    @Override
    public double get(final int index)
    {
        return histogram[index];
    }

    @Override
    public InputHistogram normalise()
    {
        double [] hist = toArray();
        if (sum > 0)
        {
            for (int i = 0; i < hist.length; ++i)
                hist[i] /= sum;
        }
        return new Histogram(step, lower, hist, 1.0);
    }
} // class Histogram

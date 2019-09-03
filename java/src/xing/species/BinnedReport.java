package xing.species;
import xing.species.core.SADReport;
import xing.core.InputHistogram;
import xing.base.Histogram;
import java.util.Arrays;

/**
 * @author Xing Yu
 */
public class BinnedReport implements SADReport
{
    @FunctionalInterface
    public static interface Transform
    {
        public double bin(final double x);
    }

    private final Transform transform;

    public BinnedReport(final Transform transform)
    {
        this.transform = transform;
    }

    public BinnedReport()
    {
        this(1.0);
    }

    public BinnedReport(final double step)
    {
        this(x -> x / step);
    }

    @Override
    public InputHistogram report(final int [] sad)
    {
        double [] weight = new double[sad.length];
        Arrays.fill(weight, 1.);

        double [] transformed = new double[sad.length];
        for (int i = 0; i < sad.length; ++i) 
            transformed[i] = transform.bin(sad[i]);

        Histogram hist = new Histogram.Builder()
                         .setLowerBound(0)
                         .setStepSize(1.0)
                         .getInstance(transformed, weight);
        return hist;
    }
} // class BinnedReport

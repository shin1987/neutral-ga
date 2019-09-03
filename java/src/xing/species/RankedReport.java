package xing.species;
import xing.species.core.SADReport;
import xing.core.InputHistogram;
import xing.base.Histogram;
import java.util.Arrays;
/**
 * @author Xing Yu
 */
public class RankedReport implements SADReport
{
    @Override
    public InputHistogram report(final int [] sad)
    {
        Arrays.sort(sad);
        Histogram hist = new Histogram.Builder()
                         .setNumberOfBins(sad.length).getInstance();
        for (int i = 0; i < sad.length; ++i)
            hist.voteInto(i, sad[sad.length - 1 - i]);
        return hist;
    }
} // class RankedReport

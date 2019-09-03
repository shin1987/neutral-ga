package xing.base;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.*;
import java.util.ArrayList;
/**
 * @author Xing Yu
 */
public class TestHistogram
{
    private static final double [] X = {1, 2, 2, 3, 5, 7, 9, 8, 9, 9};
    private static final double [] W = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    private static final double EPS = Math.sqrt(Histogram.PRECISION);
    @Test
    void testDefaultConstructor()
    {
        Histogram h = new Histogram.Builder().getInstance();
        assertThat(h.getNumberOfBins(), equalTo(10));
        assertThat(h.getLowerBound(), equalTo(0.0));
        assertThat(h.getUpperBound(), equalTo(10.0));
        assertThat(h.getStepSize(), equalTo(1.0));
        assertThat(h.getTotalVote(), equalTo(0.0));
        for (int i = 0; i < h.getNumberOfBins(); ++i)
            assertThat(h.get(i), equalTo(0.0));
    }

    @Test
    void testInitialiser()
    {
        Histogram h = new Histogram.Builder().getInstance(W);
        assertThat(h.getNumberOfBins(), equalTo(W.length));
        assertThat(h.getLowerBound(), equalTo(0.0));
        assertThat(h.getUpperBound(), equalTo(W.length * 1.0));
        assertThat(h.getStepSize(), equalTo(1.0));
        assertThat(h.getTotalVote(), equalTo(W.length * 1.0));
        for (int i = 0; i < h.getNumberOfBins(); ++i)
            assertThat(h.get(i), equalTo(1.0));        
    }

    @Test
    void testInitialiserSetBinNum()
    {
        Histogram h = new Histogram.Builder()
                      .setNumberOfBins(12)
                      .getInstance(W);
        assertThat(h.getNumberOfBins(), equalTo(W.length));
        assertThat(h.getLowerBound(), equalTo(0.0));
        assertThat(h.getUpperBound(), equalTo(W.length * 1.0));
        assertThat(h.getStepSize(), equalTo(1.0));
        assertThat(h.getTotalVote(), equalTo(W.length * 1.0));
        for (int i = 0; i < h.getNumberOfBins(); ++i)
            assertThat(h.get(i), equalTo(1.0));        
    }

    @Test
    void testInitialiserSetStepSize()
    {
        Histogram h = new Histogram.Builder()
                      .setStepSize(1.2)
                      .getInstance(W);
        assertThat(h.getNumberOfBins(), equalTo(W.length));
        assertThat(h.getLowerBound(), equalTo(0.0));
        assertThat(h.getUpperBound(), equalTo(W.length * 1.2));
        assertThat(h.getStepSize(), equalTo(1.2));
        assertThat(h.getTotalVote(), equalTo(W.length * 1.));
        for (int i = 0; i < h.getNumberOfBins(); ++i)
            assertThat(h.get(i), equalTo(1.0));        
    }

    @Test
    void testInitialiserSetLowerBound()
    {
        Histogram h = new Histogram.Builder()
                      .setLowerBound(1.2)
                      .getInstance(W);
        assertThat(h.getNumberOfBins(), equalTo(W.length));
        assertThat(h.getLowerBound(), equalTo(1.2));
        assertThat(h.getUpperBound(), equalTo(W.length + 1.2));
        assertThat(h.getStepSize(), equalTo(1.0));
        assertThat(h.getTotalVote(), equalTo(W.length * 1.));
        for (int i = 0; i < h.getNumberOfBins(); ++i)
            assertThat(h.get(i), equalTo(1.0));        

    }

    @Test
    void testInitVoteSNL()
    {

        Histogram h = new Histogram.Builder()
                      .setStepSize(2)
                      .setLowerBound(-1)
                      .setNumberOfBins(8)
                      .getInstance(X, W);
        assertThat(h.getNumberOfBins(), equalTo(8));
        assertThat(h.getLowerBound(), equalTo(-1.));
        assertThat(h.getUpperBound(), equalTo(15.));
        assertThat(h.getStepSize(), equalTo(2.));
        assertThat(h.getTotalVote(), equalTo(W.length * 1.));
        assertThat(toList(h), contains(0., 3., 1., 1., 2., 3., 0., 0.));
    }

    @Test
    void testInitVote()
    {
        Histogram h = new Histogram.Builder()
                      .getInstance(X, W);
        assertThat(h.getNumberOfBins(), equalTo(10));
        assertThat(h.getLowerBound(), equalTo(1.));
        assertThat(h.getUpperBound(),
                   allOf(closeTo(9., EPS), greaterThan(9.)));
        assertThat(h.getStepSize(),
                   allOf(closeTo(0.8, EPS), greaterThan(0.8)));
        assertThat(h.getTotalVote(), equalTo(W.length * 1.));
        assertThat(toList(h), contains(1., 2., 1., 0., 1., 0., 0., 1., 1., 3.));
    }

    @Test
    void testInitVoteL()
    {
        Histogram h = new Histogram.Builder()
                      .setLowerBound(-2).getInstance(X, W);
        assertThat(h.getLowerBound(), equalTo(-2.0));
        assertThat(h.getNumberOfBins(), equalTo(10));
        assertThat(h.getUpperBound(),
                   allOf(closeTo(9.0, EPS), greaterThan(9.0)));
        assertThat(h.getStepSize(), allOf(closeTo(1.1, EPS), greaterThan(1.1)));
        assertThat(h.getTotalVote(), equalTo(W.length * 1.));
        assertThat(toList(h), contains(0., 0., 1., 2., 1., 0., 1., 0., 1., 4.));
    }

    @Test
    void testInitVoteN()
    {
        Histogram h = new Histogram.Builder()
                      .setNumberOfBins(5).getInstance(X, W);
        assertThat(h.getNumberOfBins(), equalTo(5));
        assertThat(h.getLowerBound(), equalTo(1.0));
        assertThat(h.getUpperBound(), allOf(closeTo(9., EPS), greaterThan(9.)));
        assertThat(h.getStepSize(), allOf(closeTo(1.6, EPS), greaterThan(1.6)));
        assertThat(toList(h), contains(3., 1., 1., 1., 4.));
    }

    @Test
    void testInitVoteS()
    {
        Histogram h = new Histogram.Builder()
                      .setStepSize(2.0).getInstance(X, W);
        assertThat(h.getStepSize(), equalTo(2.0));
        assertThat(h.getNumberOfBins(), equalTo(5));
        assertThat(h.getLowerBound(), equalTo(1.));
        assertThat(h.getUpperBound(), equalTo(11.));
        assertThat(toList(h), contains(3., 1., 1., 2., 3.));
    }

    @Test
    void testInitVoteNL()
    {
        Histogram h = new Histogram.Builder()
                      .setNumberOfBins(5).setLowerBound(.5).getInstance(X, W);
        assertThat(h.getNumberOfBins(), equalTo(5));
        assertThat(h.getLowerBound(), equalTo(.5));
        assertThat(h.getUpperBound(),
                   allOf(closeTo(9., EPS), greaterThan(9.)));
        assertThat(h.getStepSize(),
                   allOf(closeTo(1.7, EPS), greaterThan(1.7)));
        assertThat(toList(h), contains(3., 1., 1., 1., 4.));
    }

    @Test
    void testInitVoteSL()
    {
        Histogram h = new Histogram.Builder()
                      .setStepSize(1.9).setLowerBound(0.0).getInstance(X, W);
        assertThat(h.getLowerBound(), equalTo(0.0));
        assertThat(h.getStepSize(), equalTo(1.9));
        assertThat(h.getUpperBound(), equalTo(9.5));
        assertThat(h.getNumberOfBins(), equalTo(5));
        assertThat(toList(h), contains(1., 3., 1., 1., 4.));
    } 

    @Test
    void testInitVoteSNAutoAdjustS()
    {
        Histogram h = new Histogram.Builder()
                      .setStepSize(1).setNumberOfBins(8).getInstance(X, W);
        assertThat(h.getStepSize(), allOf(closeTo(1., EPS), greaterThan(1.)));
        assertThat(h.getNumberOfBins(), equalTo(8));
        assertThat(h.getLowerBound(), equalTo(1.0));
        assertThat(h.getUpperBound(), allOf(closeTo(9., EPS), greaterThan(9.)));
        assertThat(toList(h), contains(3., 1., 0., 1., 0., 1., 1., 3.));
    }

    @Test
    void testInitVoteSN()
    {
        Histogram h = new Histogram.Builder()
                      .setStepSize(1).setNumberOfBins(9).getInstance(X, W);
        assertThat(h.getStepSize(), equalTo(1.));
        assertThat(h.getNumberOfBins(), equalTo(9));
        assertThat(h.getLowerBound(), equalTo(1.0));
        assertThat(h.getUpperBound(), equalTo(10.0));
        assertThat(toList(h), contains(1., 2., 1., 0., 1., 0., 1., 1., 3.));
    }

    
    private static ArrayList<Double> toList(final Histogram a)
    {
        ArrayList<Double> result = new ArrayList<Double>();
        for (double x : a.toArray())
            result.add(x);
        return result;
    }
} // class TestHistogram


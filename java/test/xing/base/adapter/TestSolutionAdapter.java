package xing.base.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import xing.core.HasDistance;
import xing.base.adapter.SolutionAdapter;
import java.util.ArrayList;
import xing.base.Problem;

/**
 * @author Xing Yu
 */
public class TestSolutionAdapter
{
    @BeforeAll
    public static void init()
    {
        ArrayList<Point1D> city = new ArrayList<Point1D>();
        city.add(new Point1D(3.0));
        city.add(new Point1D(4.0));
        city.add(new Point1D(5.0));
        city.add(new Point1D(2.0));
        city.add(new Point1D(7.0));

        Problem problem = new Problem(city);
        
        Solution.init(problem);
    }
    
    @Test
    public void testDistanceCalculation()
    {
        int [] solution = {0, 1, 2, 3, 4};
        Solution target = new Solution(solution);
        assertThat(target.getScore(), is(14.0));
    }

    @Test
    public void testForwardIsomophic()
    {
        int [] order1 = {2, 3, 4, 0, 1};
        Solution s1 = new Solution(order1);
        int [] order2 = {1, 2, 3, 4, 0};
        Solution s2 = new Solution(order2);
        assertTrue(s1.isomophicTo(s2));
        assertTrue(s2.isomophicTo(s1));
    }

    @Test
    public void testBackwardIsomophic()
    {
        int [] order1 = {2, 3, 4, 1, 0};
        int [] order2 = {3, 2, 0, 1, 4};
        Solution s1 = new Solution(order1), s2 = new Solution(order2);
        assertTrue(s1.isomophicTo(s2));
        assertTrue(s2.isomophicTo(s1));
    }

    @Test
    public void testNotIsomophic()
    {
        int [] order1 = {0, 1, 2, 3, 4};
        int [] order2 = {0, 1, 2, 4, 3};
        Solution s1 = new Solution(order1), s2 = new Solution(order2);
        assertFalse(s1.isomophicTo(s2));
        assertFalse(s2.isomophicTo(s1));
    }
} // class TestSolutionAdapter

class Solution extends SolutionAdapter
{
    Solution(final int [] s)
    {
        super(s);
    }
}

class Point1D implements HasDistance<Point1D>
{
    private final double x;
    Point1D(final double x)
    {
        this.x = x;
    }

    @Override
    public double distanceTo(Point1D other)
    {
        return Math.abs(other.x - x);
    }
}

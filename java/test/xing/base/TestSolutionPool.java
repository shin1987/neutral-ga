package xing.base;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Xing Yu
 */
public class TestSolutionPool
{
    public static final class Solution
            implements xing.core.Solution
    {
        public final double score;
        public final int id;
        public Solution(final int id, final double score)
        {
            this.score = score;
            this.id = id;
        }
        @Override
        public double getScore()
        {
            return score;
        }

        @Override
        public int at(final int n)
        {
            throw new RuntimeException("Method not implemented");
        }

        @Override
        public int getLength()
        {
            throw new RuntimeException("Method not implemented");
        }

        @Override
        public int getStart()
        {
            throw new RuntimeException("Method not implemented");
        }

        @Override
        public boolean isomophicTo(final xing.core.Solution x)
        {
            throw new RuntimeException("Method not implemented");
        }
    }

    @Test
    public void testSolutionPool()
    {
        SolutionPool<Solution> pool = new SolutionPool<Solution>();
        for (int i = 0; i < 10; ++i)
            pool.add(new Solution(i, 1.0 / (i + 1)));
        assertThat(pool.getSize(), is(10));
        assertThat(pool.getBestSolution(), is(pool.at(9)));
        for (int i = 0; i < 10; ++i)
            assertThat(pool.at(i).id, is(i));
    }
} // class TestSolutionPool


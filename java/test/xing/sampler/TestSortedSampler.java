package xing.sampler;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import static xing.base.TestSolutionPool.Solution;
import xing.base.RandomEngine;
import xing.base.SolutionPool;

/**
 * @author Xing Yu
 */
public class TestSortedSampler
{
    private static final SolutionPool<Solution> pool =
            new SolutionPool<Solution>();
    
    @BeforeAll
    static void setup()
    {
        RandomEngine rng = new RandomEngine(0);
        for (int i = 0; i < 100; ++i)
            pool.add(new Solution(i, rng.nextDouble(0, 100)));
        boolean ordered = true;
        for (int i = 1; i < pool.getSize() && ordered; ++i)
            ordered &= pool.at(i).getScore() >= pool.at(i - 1).getScore();
        assumeFalse(ordered, "The pool is already sorted");
    }

    @Test
    void testSortedSampler()
    {
        SortedSampler<Solution> sample = new SortedSampler<Solution>(pool);
        Solution last = sample.next();
        for (int i = 1; i < pool.getSize(); ++i)
        {
            Solution s = sample.next();
            assertThat(last.getScore(), lessThanOrEqualTo(s.getScore()));
            last = s;
        }
    }
} // class TestSortedSampler


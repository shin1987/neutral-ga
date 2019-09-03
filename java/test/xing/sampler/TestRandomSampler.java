package xing.sampler;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import xing.base.SolutionPool;
import xing.base.RandomEngine;
import static xing.base.TestSolutionPool.Solution;


/**
 * @author Xing Yu
 */
public class TestRandomSampler
{
    private static final SolutionPool<Solution> pool =
            new SolutionPool<Solution>();

    @BeforeAll
    static void init()
    {
        while (pool.getSize() < 10)
            pool.add(new Solution(pool.getSize(), 0));
    }

    @Test
    public void testRandomSampler()
    {
        final int seed = 0;
        RandomEngine rng = new RandomEngine(seed);
        RandomSampler<Solution> sample = new RandomSampler<Solution>(
            new RandomEngine(seed), pool);
        final int N = pool.getSize() - 1;
        for (int i = 0; i < 100; ++i)
            assertThat(sample.next().id, is(rng.nextInt(0, N)));
    }
} // class TestRandomSampler


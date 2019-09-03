package xing.sampler;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.lessThan;
import xing.base.RandomEngine;
import xing.base.SolutionPool;
import xing.base.TestSolutionPool.Solution;
/**
 * @author Xing Yu
 */
public class TestStochasticSampler
{
    private static final SolutionPool<Solution> pool =
            new SolutionPool<Solution>();

    @BeforeAll
    static void init()
    {
        for (int i = 0; i < 10; ++i)
            pool.add(new Solution(i, i * 0.2));
    }

    @Test
    public void testStochasticSampler()
    {
        StochasticSampler<Solution> sample =
                new StochasticSampler<Solution>(pool, new RandomEngine(0));
        final int [] hit = new int[pool.getSize()];
        java.util.Arrays.fill(hit, 0);

        final int N = 1000000;
        for (int i = 0; i < N; ++i)
            hit[sample.next().id]++;
        for (int i = 1; i < hit.length; ++i) {
            assertThat(hit[i], lessThan(hit[i - 1]));
        }
    }

} // class TestStochasticSampler


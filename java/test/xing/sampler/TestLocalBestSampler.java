package xing.sampler;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static xing.base.TestSolutionPool.Solution;
import xing.base.SolutionPool;
/**
 * @author Xing Yu
 */
public class TestLocalBestSampler
{
    private static final SolutionPool<Solution> pool =
            new SolutionPool<Solution>();
    private static final int N = 4;
    private static final double [] score = {
        0.1, 0.02, 0.4, 0.3,
        0.3, 0.7, 0.31, 0.5,
        0.6, 0.65, 0.55, 0.551,
        0.9, 0.8, 0.7, 0.1};

    @BeforeAll
    static void init()
    {
        while (pool.getSize() < score.length)
        {
            int i = pool.getSize();
            pool.add(new Solution(i, score[i]));
        }
    }

    @Test
    public void testLocalBestSampler()
    {
        LocalBestSampler<Solution> sample =
                new LocalBestSampler<Solution>(N, pool);

        assertThat(sample.next().id, is(1));
        assertThat(sample.next().id, is(4));
        assertThat(sample.next().id, is(10));
        assertThat(sample.next().id, is(15));

    }
} // class TestLocalBestSampler


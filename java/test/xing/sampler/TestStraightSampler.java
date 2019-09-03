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
public class TestStraightSampler
{
    private static final SolutionPool<Solution> pool =
            new SolutionPool<Solution>();
    
    @BeforeAll
    public static void init()
    {
        for (int i = 0; i < 10; ++i)
            pool.add(new Solution(i, i));
    }
    
    @Test
    public void testStraightSampler()
    {
        StraightSampler<Solution> sample = new StraightSampler<Solution>(pool);
        for (int i = 0; i < pool.getSize() * 10; ++i)
            assertThat(sample.next().id, is(i % pool.getSize()));
    }
} // class TestStraightSampler


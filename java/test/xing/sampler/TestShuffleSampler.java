package xing.sampler;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import static xing.base.TestSolutionPool.Solution;
import xing.base.SolutionPool;
import xing.base.RandomEngine;

/**
 * @author Xing Yu
 */
public class TestShuffleSampler
{
    private static final SolutionPool<Solution> pool =
            new SolutionPool<Solution>();
    
    @BeforeAll
    static void init()
    {
        for (int i = 0; i < 100; ++i)
            pool.add(new Solution(i, 0.0));
    }
    
    @Test
    void testShuffleSampler()
    {
        final int S = 0;
        ShuffleSampler.Factory<Solution> factory =
                new ShuffleSampler.Factory<Solution>(new RandomEngine(S));
        ShuffleSampler<Solution> sampler = factory.getInstance(pool);
        final int [] order = new int[pool.getSize()];
        RandomEngine rng = new RandomEngine(S);
        rng.randperm(order);
        for (int x : order)
            assertThat(sampler.next().id, is(x));
    }

    @Test
    void testShuffleSamplerTwoRound()
    {
        final int S = 0;
        ShuffleSampler.Factory<Solution> factory =
                new ShuffleSampler.Factory<Solution>(new RandomEngine(S));
        ShuffleSampler<Solution> sampler = factory.getInstance(pool);
        final int [] order = new int[pool.getSize()];
        RandomEngine rng = new RandomEngine(S);
        rng.randperm(order);
        for (int x : order)
            sampler.next();

        for (int x : order)
            assertThat(sampler.next().id, is(x));
    }
    
    @Test
    void testShuffleSamplerSecondSampler()
    {
        final int S = 0;
        ShuffleSampler.Factory<Solution> factory =
                new ShuffleSampler.Factory<Solution>(new RandomEngine(S));
        ShuffleSampler<Solution> sampler = factory.getInstance(pool);
        sampler = factory.getInstance(pool);

        final int [] order = new int[pool.getSize()];
        RandomEngine rng = new RandomEngine(S);
        rng.randperm(order);
        rng.randperm(order);

        for (int x : order)
            assertThat(sampler.next().id, is(x));
    }

} // class TestShuffleSampler


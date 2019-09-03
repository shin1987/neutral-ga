package xing.sampler;
import xing.core.SolutionSampler;
import xing.core.Solution;
import xing.core.InputSolutionPool;
import xing.core.RandomEngine;
import java.util.ArrayList;

/**
 * Randomly sample solutions - assuming uniform distribution on samples.
 * @author Xing Yu
 */
public class RandomSampler<E extends Solution>
        implements SolutionSampler<E>
{
    public static class Factory<T extends Solution>
            implements SolutionSampler.Factory<T>
    {
        private final RandomEngine rng;
        
        public Factory(final RandomEngine rng)
        {
            this.rng = rng;
        }

        @Override
        public RandomSampler<T> getInstance(
            final InputSolutionPool<T> pool)
        {
            return new RandomSampler<T>(rng, pool);
        }
    }
    
    private final RandomEngine rng;
    private final ArrayList<E> buffer = new ArrayList<E>();

    public RandomSampler(final RandomEngine rng,
                         final InputSolutionPool<E> pool)
    {
        this(pool.getSize(), rng, new StraightSampler<E>(pool));
    }
    
    public RandomSampler(final int N,
                         final RandomEngine rng,
                         final SolutionSampler<E> sampler)
    {
        this.rng = rng;
        while (buffer.size() < N)
            buffer.add(sampler.next());
    }

    @Override
    public E next()
    {
        final int index = rng.nextInt(0, buffer.size() - 1);
        return buffer.get(index);
    }
} // class RandomSampler

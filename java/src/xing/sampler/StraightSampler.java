package xing.sampler;
import xing.core.SolutionSampler;
import xing.core.InputSolutionPool;
import xing.core.Solution;

/**
 * Sample solutions in straight order.
 * @author Xing Yu
 */
public final class StraightSampler<E extends Solution>
        implements SolutionSampler<E>
{
    public static class Factory<T extends Solution>
            implements SolutionSampler.Factory<T>
    {
        @Override
        public StraightSampler<T> getInstance(
            final InputSolutionPool<T> pool)
        {
            return new StraightSampler<T>(pool);
        }
    }

    private final InputSolutionPool<E> pool;
    private int index = 0;

    public StraightSampler(final InputSolutionPool<E> pool)
    {
        this.pool = pool;
    }

    @Override
    public E next()
    {
        E retval = pool.at(index);
        index = (index + 1) % pool.getSize();
        return retval;
    }
} // class StraightSampler

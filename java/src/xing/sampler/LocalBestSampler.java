package xing.sampler;
import xing.core.SolutionSampler;
import xing.core.Solution;
import xing.core.InputSolutionPool;

/**
 * Divide the solution pool into chunks, and each time returns the best 
 * solution within the chunk.
 * @author Xing Yu
 */
public class LocalBestSampler<E extends Solution>
        implements SolutionSampler<E>
{
    private final SolutionSampler<E> sample;
    private final int N;

    public static class Factory<T extends Solution>
            implements SolutionSampler.Factory<T>
    {
        private final int N;
        public Factory(final int N)
        {
            this.N = N;
        }
        
        @Override
        public LocalBestSampler<T> getInstance(final InputSolutionPool<T> pool)
        {
            return new LocalBestSampler<T>(N, pool);
        }
    }

    public LocalBestSampler(final int N, final InputSolutionPool<E> pool)
    {
        this(N, new StraightSampler<E>(pool));
    }
    
    public LocalBestSampler(final int N, final SolutionSampler<E> sample)
    {
        this.sample = sample;
        this.N = N;
    }

    @Override
    public E next()
    {
        E best = sample.next();
        for (int i = 1; i < N; ++i)
        {
            E s = sample.next();
            if (s.getScore() < best.getScore())
                best = s;
        }
        return best;
    }
} // class LocalBestSampler

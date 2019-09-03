package xing.sampler;
import xing.core.Solution;
import xing.core.InputSolutionPool;
import xing.core.SolutionSampler;
import xing.core.RandomEngine;
import xing.util.core.Probability;
import java.util.ArrayList;

/**
 * Sample solution based on some probabilistic distribution.
 * @author Xing Yu
 */
public class StochasticSampler<E extends Solution>
        implements SolutionSampler<E>
{
    public static final Probability DEFAULT_PROBABILITY =
            x -> Math.exp(-.5 * x * x) / Math.sqrt(2 * Math.PI);

    public static class Factory<E extends Solution>
            implements SolutionSampler.Factory<E>
    {
        private final RandomEngine rng;
        private final Probability probability;
        
        public Factory(final RandomEngine rng,
                       final Probability probability)
        {
            this.rng = rng;
            this.probability = probability;
        }

        public Factory(final RandomEngine rng)
        {
            this(rng, StochasticSampler.DEFAULT_PROBABILITY);
        }

        @Override
        public StochasticSampler<E> getInstance(final InputSolutionPool<E> src)
        {
            return new StochasticSampler<E>(src, rng, probability);
        }
    }

    private final ArrayList<E> solution = new ArrayList<E>();
    private final double [] accumulated;
    private final RandomEngine rng;

    public E next()
    {
        final double upper = accumulated[accumulated.length - 1];
        final double th = rng.nextDouble(0, upper);
        int i = 0;
        while (i < solution.size() - 1 && accumulated[i] < th)
            ++i;
        return solution.get(i);
    }

    public StochasticSampler(final InputSolutionPool<E> pool,
                             final RandomEngine rng,
                             final Probability probability)
    {
        this(pool.getSize(), new StraightSampler<E>(pool), rng, probability);
    }

    public StochasticSampler(final InputSolutionPool<E> pool,
                             final RandomEngine rng)
    {
        this(pool, rng, DEFAULT_PROBABILITY);
    }

    public StochasticSampler(final int N,
                             final SolutionSampler<E> source,
                             final RandomEngine rng)
    {
        this(N, source, rng, DEFAULT_PROBABILITY);
    }

    public StochasticSampler(final int N,
                             final SolutionSampler<E> source,
                             final RandomEngine rng,
                             final Probability probability)
    {
        this.rng = rng;
        
        accumulated = new double[N];

        for (int i = 0; i < N; ++i)
        {
            final E s = source.next();
            solution.add(s);
            accumulated[i] = probability.convert(s.getScore());
        }

        for (int i = 1; i < N; ++i)
            accumulated[i] += accumulated[i - 1];        
    }
} // class StochasticSampler


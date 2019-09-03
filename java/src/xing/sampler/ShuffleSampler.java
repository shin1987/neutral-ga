package xing.sampler;
import java.util.ArrayList;
import xing.core.SolutionSampler;
import xing.core.RandomEngine;
import xing.core.InputSolutionPool;
import xing.core.Solution;

/**
 * Shuffle the order of solutions in the given solution source.
 * @author Xing Yu
 */
public class ShuffleSampler<E extends Solution>
        implements SolutionSampler<E>
{
    private final ArrayList<E> buffer = new ArrayList<E>();
    private int nextIndex = -1;

    public static class Factory<E extends Solution>
            implements SolutionSampler.Factory<E>
    {
        private final RandomEngine rng;

        public Factory(final RandomEngine rng)
        {
            this.rng = rng;
        }

        @Override
        public ShuffleSampler<E> getInstance(final InputSolutionPool<E> pool)
        {
            return new ShuffleSampler<E>(pool, rng);
        }
    }
    
    public ShuffleSampler(final InputSolutionPool<E> pool,
                          final RandomEngine rng)
    {
        this(pool.getSize(), new StraightSampler<E>(pool), rng);
    }
    
    public ShuffleSampler(final int N,
                          final SolutionSampler<E> sample,
                          final RandomEngine rng)
    {
        ArrayList<E> sequence = new ArrayList<E>();
        while (sequence.size() < N)
            sequence.add(sample.next());
        int [] index = new int[N];
        rng.randperm(index);
        for (int i : index)
            buffer.add(sequence.get(i));
    }
    @Override
    public E next()
    {
        nextIndex = (nextIndex + 1) % buffer.size();
        return buffer.get(nextIndex);
    }
} // class ShuffleSampler

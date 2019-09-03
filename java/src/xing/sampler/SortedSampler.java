package xing.sampler;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;

import xing.core.Solution;
import xing.core.InputSolutionPool;
import xing.core.SolutionSampler;

/**
 * Output solutions in the ascending order of their scores.
 * @author Xing Yu
 */
public class SortedSampler<E extends Solution>
        implements SolutionSampler<E>
{
    public static class Factory<E extends Solution>
            implements SolutionSampler.Factory<E>
    {
        @Override
        public SortedSampler<E> getInstance(
            final InputSolutionPool<E> pool)
        {
            return new SortedSampler<E>(pool);
        }
    }
    private final ArrayList<E> buffer = new ArrayList<E>();
    private int index = 0;

    public SortedSampler(final InputSolutionPool<E> pool)
    {
        this(pool.getSize(), new StraightSampler<E>(pool));
    }
    public SortedSampler(final int N,
                         final SolutionSampler<E> sample)
    {
        while (buffer.size() < N)
            buffer.add(sample.next());
        // Sort the buffer
        Collections.sort(buffer,
                         new Comparator<E>() {
                             @Override
                             public int compare(final E a, final E b)
                             {
                                 double diff = a.getScore() - b.getScore();
                                 return (int)(diff / Math.abs(diff));
                             }
            });
    }
    
    @Override
    public E next()
    {
        final int i = index;
        index = (index + 1) % buffer.size();
        return buffer.get(i);
    }
} // class SortedSampler

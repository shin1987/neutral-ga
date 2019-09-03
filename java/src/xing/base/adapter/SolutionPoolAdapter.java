package xing.base.adapter;

import xing.core.SolutionPool;
import xing.core.Solution;
import java.util.Collection;

/**
 * Template for solution pool implementation.
 * It summarises how best solution is store and updated in the pool. Sub-classes
 * should provide the actual storage and the add solution operation.
 * @author Xing Yu
 */
public abstract class SolutionPoolAdapter<E extends Solution>
        implements SolutionPool<E>
{
    private final SolutionComparator selector = new SolutionComparator();
    private E bestSolution = null;

    @Override
    public E getBestSolution()
    {
        return this.bestSolution;
    }

    @Override
    public void getAllSolutions(final Collection<? super E> result)
    {
        for (int i = 0; i < this.getSize(); ++i)
            result.add(this.at(i));
    }
    
    /**
     * Add a solution and update the current best solution in the pool.
     * It depends on the sub-class to implement the actual appending operation.
     * @param solution new solution to be addedc.
     */
    @Override
    public void add(final E solution)
    {
        this.addSolution(solution);
        bestSolution = selector.compare(bestSolution, solution);
    }

    /**
     * Subclasses should implement how a new solution is added to its storage.
     * @param solution a new solution to be added.
     */
    protected abstract void addSolution(final E solution);
} // class SolutionPoolAdapter

class SolutionComparator
{
    private class Comparator
    {
        <E extends Solution> E compare(final E a, final E b)
        {
            return b;
        }
    }

    private class ProperComparator extends Comparator
    {
        @Override
        <E extends Solution> E compare(final E a, final E b)
        {
            return (a.getScore() < b.getScore()) ? a : b;
        }
    }

    private Comparator selector = new Comparator();
    private final Comparator proper = new ProperComparator();
    
    <E extends Solution> E compare(final E current, final E x)
    {
        final E ret = selector.compare(current, x);
        selector = proper;
        return ret;
    }
}

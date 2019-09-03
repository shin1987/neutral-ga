package xing.base;
import xing.base.adapter.SolutionPoolAdapter;
import java.util.ArrayList;
import java.util.Collection;
/**
 * An implementation of solution pool.
 * @author Xing Yu
 */
public final class SolutionPool<E extends xing.core.Solution>
        extends SolutionPoolAdapter<E>
{
    private final ArrayList<E> storage = new ArrayList<E>();

    /**
     * Implementation of SolutionPool factory.
     */
    public static class Factory<E extends xing.core.Solution>
            implements xing.core.SolutionPool.Factory<E>
    {
        @Override
        public SolutionPool<E> getInstance()
        {
            return new SolutionPool<E>();
        }
    }

    @Override
    public void getAllSolutions(final Collection<? super E> result)
    {
        result.addAll(storage);
    }
    
    @Override
    protected void addSolution(final E solution)
    {
        storage.add(solution);
    }

    @Override
    public int getSize()
    {
        return storage.size();
    }

    @Override
    public E at(final int index)
    {
        return storage.get(index);
    }
} // class SolutionPool

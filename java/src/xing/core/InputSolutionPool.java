package xing.core;
import java.util.Collection;

/**
 * Read only interface for solution pool.
 * @author Xing Yu
 */
public interface InputSolutionPool<E extends Solution>
{
    /**
     * Get solution at a position.
     * @param index position of the solution
     * @return the solution
     */
    public E at(final int index);

    public int getSize();

    /**
     * Get the solution with the lowest score.
     * @return best solution in the solution pool
     */
    public E getBestSolution();

    /**
     * Get all solutions.
     * @param result output collection
     */
    public void getAllSolutions(final Collection<? super E> result);
} // interface InputSolutionPool

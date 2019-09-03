package xing.core;

/**
 * Interface for interative solvers
 * @author Xing Yu
 */
public interface Solver<E extends Solution>
{
    /**
     * Solve the problem
     * @param iteration maximum iterations to run
     * @return the best solution
     */
    public E solve(final int iteration);

    /**
     * Terminate current running process.
     */
    public void kill();
} // interface Solver

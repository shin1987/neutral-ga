package xing.core;

/**
 * Observer that reacts to a new solution pool.
 * @author Xing Yu
 */
public interface SolutionMonitor
{
    public void monitor(final InputSolutionPool<? extends Solution> candidate);
} // interface SolutionMonitor

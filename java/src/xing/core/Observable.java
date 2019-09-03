package xing.core;

/**
 * Provides entry for adding SolutionMonitor.
 * @author Xing Yu
 */
public interface Observable
{
    /**
     * Add a new solution pool monitor.
     * @param monitor solution pool monitor.
     */
    public void addSolutionMonitor(final SolutionMonitor monitor);
} // interface Observable

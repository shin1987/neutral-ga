package xing.ui.core;
import xing.core.Solution;
import xing.core.Algorithm;

/**
 * Interface implemented by GUI applications.
 * @author Xing Yu
 */
public interface Application
{
    /**
     * Execute the application.
     */
    public void start();

    /**
     * Associate the solver - observable with the GUI application.
     * @param solver Solver instance to be monitored.
     */
    public void registerSolver(final Algorithm solver);
} // interface Application

package xing.ui.core;
import xing.core.Solution;

/**
 * A panel can directly interpret solution as plot.
 * @author Xing Yu
 */
public interface SolutionPlot
{
    /**
     * Turn a specific solution into a plot.
     * @param solution input solution to be drawn.
     */
    public void plotSolution(final Solution solution);
} // interface SolutionPlot

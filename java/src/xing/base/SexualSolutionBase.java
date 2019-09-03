package xing.base;
import xing.base.adapter.SolutionAdapter;

/**
 * Template for SexualSolutions
 * @author Xing Yu
 */
public abstract class SexualSolutionBase
        extends SolutionAdapter
        implements xing.core.SexualSolution
{
    /**
     * Constructor
     * @param solution traversal order
     */
    public SexualSolutionBase(final int [] solution)
    {
        super(solution);
    }
} // class SexualSolution

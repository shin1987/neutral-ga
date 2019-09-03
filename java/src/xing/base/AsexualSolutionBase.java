package xing.base;
import xing.base.adapter.SolutionAdapter;

/**
 * Template for AsexualSolution
 * @author Xing Yu
 */
public abstract class AsexualSolutionBase
        extends SolutionAdapter
        implements xing.core.AsexualSolution
{
    /**
     * Constructor
     * @param solution traversal order
     */
    public AsexualSolutionBase(final int [] solution)
    {
        super(solution);
    }
} // class AsexualSolution

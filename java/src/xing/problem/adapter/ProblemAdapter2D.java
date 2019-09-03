package xing.problem.adapter;
import xing.core.Problem;
import xing.core.ProblemGenerator2D;
import xing.core.Point;
import java.util.ArrayList;

/**
 * Common implementation for ProblemGenerator2D implementation.
 * @author Xing Yu
 */
public abstract class ProblemAdapter2D
        extends ProblemAdapter implements ProblemGenerator2D
{
    public ProblemAdapter2D(final String [] args)
    {
        super(args);
    }

    @Override
    public Problem generate()
    {
        ArrayList<Point> workspace = new ArrayList<Point>();
        return this.generate(workspace);
    }
} // class ProblemBase2D

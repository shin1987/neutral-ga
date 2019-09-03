package xing.problem.adapter;
import xing.util.ArgumentCollection;
import xing.core.ProblemGenerator;

/**
 * Base class shared by problems.
 * @author Xing Yu
 */
public abstract class ProblemAdapter
        implements ProblemGenerator
{
    public static final int    DEFAULT_PROBLEM_SIZE = 10;
    public static final double DEFAULT_RADIUS = 1.0;
    
    protected final int     problemSize;
    protected final double  radius;

    private final ArgumentCollection argument =
            new ArgumentCollection()
            .add("--problem-size", "" + DEFAULT_PROBLEM_SIZE)
            .add("--radius", "" + DEFAULT_RADIUS);

    protected ProblemAdapter(final String [] args)
    {
        argument.capture(args);
        problemSize = Integer.parseInt(argument.get("--problem-size"));
        radius = Double.parseDouble(argument.get("--radius"));
    }
} // class ProblemBase

package xing.problem.adapter;
import xing.util.ArgumentCollection;

/**
 * @author Xing Yu
 */
public abstract class RandomisedProblemAdapter extends ProblemAdapter
{
    public static final long   DEFAULT_SEED   = 0L;
    public static final double DEFAULT_NOISE  = 0.05;
    public static final String DEFAULT_NOISE_TYPE   = "WHITE";

    protected final long    seed;
    protected final boolean gaussianNoise;
    protected final double  noise;

    private final ArgumentCollection argument =
            new ArgumentCollection()
            .add("--problem-seed", "" + DEFAULT_SEED)
            .add("--noise", "" + DEFAULT_NOISE)
            .add("--noise-type", DEFAULT_NOISE_TYPE);

    protected RandomisedProblemAdapter(final String [] args)
    {
        super(args);
        argument.capture(args);
        gaussianNoise = argument.get("--noise-type")
                        .toUpperCase()
                        .equals("GAUSSIAN");
        noise = Double.parseDouble(argument.get("--noise"));
        seed  = Long.parseLong(argument.get("--problem-seed"));
    }
} // class RandomisedProblemAdapter

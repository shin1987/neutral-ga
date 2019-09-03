package xing.algorithm.adapter;
import xing.core.ProgressMonitor;
import xing.core.Progressive;
import xing.core.Solver;
import xing.core.Problem;
import xing.core.Solution;
import xing.core.Algorithm;
import xing.core.Observable;
import xing.core.SolutionMonitor;
import xing.util.ArgumentCollection;
import java.util.ArrayList;
/**
 * @author Xing Yu
 */
public abstract class AlgorithmAdapter
        implements Observable, Algorithm, Progressive
{
    public static final int DEFAULT_POOL_SIZE = 512;
    public static final int DEFAULT_ITERATION = 512;
    public static final long DEFAULT_SEED = 0;

    private final ArrayList<SolutionMonitor> solutionMonitor =
            new ArrayList<SolutionMonitor>();
    private final ArrayList<ProgressMonitor> progressMonitor =
            new ArrayList<ProgressMonitor>();

    private final ArgumentCollection argument =
            new ArgumentCollection()
            .add("--population", "" + DEFAULT_POOL_SIZE)
            .add("--algorithm-seed", "" + DEFAULT_SEED)
            .add("--iteration", "" + DEFAULT_ITERATION);
    
    protected final int population;
    protected final int iteration;
    protected final long seed;
    private Solver<? extends Solution> solver = null;

    protected AlgorithmAdapter(final String [] args)
    {
        argument.capture(args);
        this.population = Integer.parseInt(argument.get("--population"));
        this.seed = Long.parseLong(argument.get("--algorithm-seed"));
        this.iteration = Integer.parseInt(argument.get("--iteration"));
    }

    public void addProgressMonitor(final ProgressMonitor monitor)
    {
        progressMonitor.add(monitor);
        monitor.monitor(0, iteration);
    }

    protected abstract
    Solver<? extends Solution> newSolver(final Problem problem);

    public void addSolutionMonitor(final SolutionMonitor monitor)
    {
        solutionMonitor.add(monitor);
    }

    @Override
    public void kill()
    {
        if (solver != null)
            solver.kill();
    }
    
    public Solution execute(final Problem problem)
    {
        solver = newSolver(problem);
        if (solver instanceof Progressive) {
            Progressive s = (Progressive)solver;
            for (ProgressMonitor m : progressMonitor)
                s.addProgressMonitor(m);
        }
        if (solver instanceof Observable) {
            Observable s = (Observable)solver;
            for (SolutionMonitor m : solutionMonitor)
                s.addSolutionMonitor(m);
        }
        return solver.solve(this.iteration);
    }
} // class AlgorithmBase

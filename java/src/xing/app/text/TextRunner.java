package xing.app.text;
import xing.core.Algorithm;
import xing.core.Observable;
import xing.core.SolutionMonitor;
import xing.core.Solution;
import xing.core.Solver;
import xing.core.ProblemGenerator;
import xing.core.Problem;
import xing.core.ProgressMonitor;
import xing.core.Progressive;
import xing.core.InputSolutionPool;
import xing.ui.core.Application;
import xing.util.Timer;
import xing.util.ClassLoader;
import xing.util.InternalClassScanner;
import xing.util.ArgumentCollection;
import java.util.HashSet;

/**
 * @author Xing Yu
 */
public class TextRunner
        implements Application, SolutionMonitor, ProgressMonitor
{
    private final Algorithm algorithm;
    private final Problem problem;
    private final Stats stats = new Stats();

    public TextRunner(final Algorithm algorithm,
                      final Problem problem)
    {
        this.problem = problem;
        this.algorithm = algorithm;
        this.registerSolver(algorithm);
    }

    @Override
    public void monitor(final long progress, final long total)
    {
        stats.monitorTimer.tic();
        stats.progress.total = total;
        stats.progress.progress = progress;
        stats.monitorTimer.toc();
    }
    
    @Override
    public void monitor(final InputSolutionPool<? extends Solution> pool)
    {
        stats.monitorTimer.tic();
        stats.iteration++;
        // pool.getAllSolutions(stats.history); // Temporarily disabled
        Solution candidate = pool.getBestSolution();
        if (stats.best == null || candidate.getScore() < stats.best.getScore())
        {
            stats.best = candidate;
            stats.lastUpdate = stats.iteration;
        }
        stats.monitorTimer.toc();
    }

    @Override
    public void registerSolver(final Algorithm algorithm)
    {
        if (algorithm instanceof Observable)
            ((Observable)algorithm).addSolutionMonitor(this);
        if (algorithm instanceof Progressive) {
            ((Progressive)algorithm).addProgressMonitor(this);
        }
    }

    @Override
    public void start()
    {
        stats.totalTimer.tic();
        stats.best = algorithm.execute(this.problem);
        stats.totalTimer.toc();
        printSummary();
    }

    private void printSummary()
    {
        System.out.println("=====Summary=====");
        System.out.println("Iterations: " + stats.iteration);
        // System.out.println("Total solutions: " + stats.history.size());
        System.out.println();
        System.out.println("Execution time: " + stats.totalTimer);
        System.out.println("Monitor time  : " + stats.monitorTimer);
        System.out.println("Computation   : " +
                           stats.totalTimer.diff(stats.monitorTimer));
        System.out.println();
        System.out.println("Solution found on iteration: " + stats.lastUpdate);
        System.out.println("Best score: " + stats.best.getScore());
        System.out.println("Solution: " + stats.best);
    }
    
    public static void main(final String [] args)
    {
        ArgumentCollection argument = new ArgumentCollection()
                                      .add("--problem-args", "")
                                      .add("--algorithm-args", "");
        argument.capture(args);

        ClassLoader loader = new ClassLoader();
        Class<?> problemType = InternalClassScanner.loadClass(args[0]);
        Class<?> algorithmType = InternalClassScanner.loadClass(args[1]);

        String [] option = null;

        option = argument.get("--problem-args").split(",");
        ProblemGenerator problem =
                loader.load(ProblemGenerator.class, problemType, option);

        option = argument.get("--algorithm-args").split(",");
        Algorithm algorithm = 
                loader.load(Algorithm.class, algorithmType, option);
        Application app = new TextRunner(algorithm, problem.generate());
        app.start();
    }
} // class TextRunner

class Stats
{
    Timer totalTimer = new Timer();
    Timer monitorTimer = new Timer();
    Progress progress = new Progress();
    int iteration = 0;
    Solution best = null;
    int lastUpdate = 0;
    HashSet<Solution> history = new HashSet<Solution>();
}

class Progress
{
    long total = -1, progress = 0;
    @Override
    public String toString()
    {
        return progress + "/" + total;
    }
}

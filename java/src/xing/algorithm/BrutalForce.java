package xing.algorithm;
import xing.base.adapter.SolutionAdapter;
import xing.core.Observable;
import xing.core.Algorithm;
import xing.core.Problem;
import xing.core.ProgressMonitor;
import xing.core.Progressive;
import xing.core.Solution;
import xing.base.SolutionPool;
import xing.core.SolutionMonitor;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Exhustively search solutions for TSP
 * @author Xing Yu
 */
public class BrutalForce
        implements Observable, Algorithm, Progressive
{
    private final ArrayList<SolutionMonitor> solutionMonitor =
            new ArrayList<SolutionMonitor>();
    private final ArrayList<ProgressMonitor> progressMonitor =
            new ArrayList<ProgressMonitor>();
    private boolean active = true;

    public BrutalForce()
    {
    }

    public BrutalForce(final String [] args)
    {
        this();
    }

    @Override
    public void kill()
    {
        active = false;
    }
    
    @Override
    public Solution execute(final Problem problem)
    {
        final int N = problem.getProblemSize();
        BFSolution.init(problem);

        // Prepare working variables
        final boolean [] visited = new boolean[N];
        final int [] solution = new int[N];
        Arrays.fill(visited, false);
        Arrays.fill(solution, 0);
        visited[0] = true;

        // Start progress counter
        Progress progress = new Progress(N);
        
        return solve(1, solution, visited, progress);
    }

    private Solution solve(final int i,
                           final int [] solution,
                           final boolean [] visited,
                           final Progress progress)
    {
        final int N = visited.length;
        if (i == N)
        {
            int [] copy = Arrays.copyOf(solution, solution.length);
            BFSolution ret = new BFSolution(copy);
            progress.makeProgress();
            for (ProgressMonitor m : progressMonitor)
                m.monitor(progress.progress, progress.total);

            SolutionPool<BFSolution> pool = new SolutionPool<BFSolution>();
            pool.add(ret);
            for (SolutionMonitor m : solutionMonitor)
                m.monitor(pool);

            return ret;
        }
        else
        {
            Solution best = null;
            for (int j = 1; j < N && active; ++j)
            {
                if (visited[j]) continue;
                visited[j] = true;
                solution[i] = j;
                Solution candidate = solve(i + 1, solution, visited, progress);
                if (best == null || candidate.getScore() < best.getScore())
                    best = candidate;
                visited[j] = false;
            }
            return best;
        }
    }
    
    @Override
    public void addSolutionMonitor(final SolutionMonitor monitor)
    {
        solutionMonitor.add(monitor);
    }

    @Override
    public void addProgressMonitor(final ProgressMonitor monitor)
    {
        progressMonitor.add(monitor);
        monitor.monitor(0, 1);
    }
} // class BrutalForce

class Progress
{
    final long total;
    long progress = 0;

    void makeProgress()
    {
        ++progress;
    }

    Progress(final int N)
    {
        long product = 1;
        for (int i = 2; i < N; ++i)
            product *= i;
        total = product;
    }
}

class BFSolution extends SolutionAdapter
{
    BFSolution(final int [] solution)
    {
        super(solution);
    }
}

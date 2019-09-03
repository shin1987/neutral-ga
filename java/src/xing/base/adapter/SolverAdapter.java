package xing.base.adapter;
import xing.core.Solution;
import xing.core.SolutionPool;
import xing.core.SolutionSampler;
import xing.core.EvolvePolicy;
import xing.core.RandomEngine;
import xing.core.SolutionMonitor;
import xing.core.Solver;
import xing.core.Observable;
import xing.core.Progressive;
import xing.core.ProgressMonitor;
import java.util.ArrayList;

/**
 * Template implementation of solver class.
 * @author Xing Yu
 */
public class SolverAdapter<E extends Solution>
        implements Solver<E>, Observable, Progressive
{
    // Policies
    private SolutionSampler.Factory<E> surviverPolicy      = null;
    private SolutionPool.Factory<E>    solutionPoolFactory = null;    
    private RandomEngine               rng                 = null;
    private EvolvePolicy<E>            evolvePolicy        = null;
    private final ArrayList<SolutionMonitor> solutionMonitor =
            new ArrayList<SolutionMonitor>();
    private final ArrayList<ProgressMonitor> progressMonitor =
            new ArrayList<ProgressMonitor>();

    private boolean active = true;
    
    // Internal data
    private SolutionPool<E> solutionPool;
    private E bestSolution;    

    /**
     * Initialise the solver.
     * @param poolSize number of solutions maintained by the solver.
     * @param solutionPoolFactory factory for generate new solution pool.
     * @param solutionFactory factory for generate initial solutions.
     * @param rng a random number generator to be used by the solver.
     */
    protected SolverAdapter(final int poolSize,
                            final SolutionPool.Factory<E> solutionPoolFactory,
                            final Solution.Factory<E> solutionFactory,
                            final RandomEngine rng)
    {
        // Save solution pool factory
        this.solutionPoolFactory = solutionPoolFactory;
        this.rng = rng;
        
        // Initialise solution pool
        this.solutionPool = solutionPoolFactory.getInstance();   
        while (this.solutionPool.getSize() < poolSize) {
            E solution = solutionFactory.getInstance(this.rng);
            this.solutionPool.add(solution);
        }

        this.bestSolution = solutionPool.getBestSolution();
    }

    @Override
    public void kill()
    {
        active = false;
    }

    @Override
    public void addSolutionMonitor(final SolutionMonitor m)
    {
        solutionMonitor.add(m);
        m.monitor(this.solutionPool);
    }

    @Override
    public void addProgressMonitor(final ProgressMonitor m)
    {
        progressMonitor.add(m);
    }
    
    /**
     * Set strategy for how the solution pool should evolve.
     * @param policy an algorithm as policy object.
     */
    protected void setEvolvePolicy(final EvolvePolicy<E> policy)
    {
        this.evolvePolicy = policy;
    }

    /**
     * Set strategy for how new population should survive.
     * @param policy a survival algorithm as a policy object.
     */
    public void setSurviverPolicy(final SolutionSampler.Factory<E> policy)
    {
        this.surviverPolicy = policy;
    }

    @Override
    public E solve(final int maxIteration)
    {
        active = true;
        for (int iter = 0; iter < maxIteration && active; ++iter)
        {
            SolutionPool<E> nextPool  = solutionPoolFactory.getInstance();
            SolutionPool<E> workspace = solutionPoolFactory.getInstance();
            evolvePolicy.evolve(workspace, this.solutionPool, rng);

            updateBestSolution(workspace.getBestSolution());
            
            SolutionSampler<E> surviver = surviverPolicy.getInstance(workspace);
            while (nextPool.getSize() < solutionPool.getSize())
                nextPool.add(surviver.next());
            this.solutionPool = nextPool;

            for (ProgressMonitor m : this.progressMonitor)
                m.monitor(iter + 1, maxIteration);
            
            for (SolutionMonitor m : this.solutionMonitor)
                m.monitor(this.solutionPool);
        }
        
        return this.bestSolution;
    }

    private void updateBestSolution(final E solution)
    {
        if (solution.getScore() < bestSolution.getScore())
            bestSolution = solution;
    }    
} // class SolverAdapter

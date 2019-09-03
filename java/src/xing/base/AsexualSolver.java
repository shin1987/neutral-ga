package xing.base;
import xing.base.adapter.SolverAdapter;
import xing.core.AsexualSolution;
import xing.core.Solution;
import xing.core.SolutionPool;
import xing.core.RandomEngine;
import xing.core.SolutionSampler;
import xing.core.EvolvePolicy;
import xing.core.InputSolutionPool;

/**
 * <p>General framework to handle AsexualSolution.</p>
 *
 * <p>To use the solver framework, one should define as minimum:</p>
 * <ul>
 * <li> How solution should reproduce (see {@link AsexualSolution}
 * interface)</li>
 * <li> Factory classes to produce Solutions</li>
 * <li> Sampler classes and corresponding factories to generate the samplers
 * , which is used to control how parent generation is sampled</li>
 * <li>Default implementation for some preset samplers are available. 
 * (see {@link xing.sampler})</li>
 * </ul>
 *
 * <p>An example workflow:</p>
 *
 * <pre>
 * {@code
 * // Suppose the follow factories have been user implemented
 * UserSolutionFactory solutionFact = new UserSolutionFactory();
 * UserSolutionPoolFactory poolFact = new UserSolutionPoolFactory();
 * RandomEngine rng = new RandomEngine(0);
 *
 * // Initialise pool size to be 100
 * AsexualSolver solver = new AsexualSolver(100, poolFact, solutionFact, rng);
 *
 * // Setup how parent generations should be sampled
 * ParentSamplerFactory parentPolicy = new ParentSamplerFactory();
 * solver.setParentPolicy(parentPolicy);
 *
 * // Optionally, depending on different algorithm requirement, one may
 * // wish to setup surviverPolicy, e.g.:
 * // solver.surviverPolicy(someSamplerFactory);
 * 
 * UserSolution solution = solver.solve(200); // run for 200 iterations
 * }
 * </pre>
 *
 * @author Xing Yu
 */
public class AsexualSolver extends SolverAdapter<AsexualSolution>
{
    /**
     * Construct the solver.
     * @param poolSize initial size of the solution pool
     * @param poolFact factory class that allocate a solution pool instance
     * @param solutionFact factory class that generates initial solutions
     * @param rng a random engine instance that will be used during evolution
     * of the solutions
     */
    public AsexualSolver(
        final int poolSize,
        final SolutionPool.Factory<AsexualSolution> poolFact,
        final Solution.Factory<AsexualSolution> solutionFact,
        final RandomEngine rng)
    {
        super(poolSize, poolFact, solutionFact, rng);
    }

    /**
     * Defines how parent generation should be sampled from a solution pool.
     * The sampler is constructed exactly once every iteration.
     *
     * @param N number of candidate to select to reproduce
     * @param policy a factory policy that generates a sampler instance.
     */
    public void setParentPolicy(
        final int N,
        final SolutionSampler.Factory<AsexualSolution> policy)
    {
        this.setEvolvePolicy(new AsexualEvolve(N, policy));
    }
} // class AsexualSolver

class AsexualEvolve implements EvolvePolicy<AsexualSolution>
{
    private final SolutionSampler.Factory<AsexualSolution> parent;
    private final int N;
    public AsexualEvolve(final int N,
                         final SolutionSampler.Factory<AsexualSolution> policy)
    {
        this.parent = policy;
        this.N = N;
    }

    @Override
    public void evolve(final SolutionPool<AsexualSolution> destination,
                       final InputSolutionPool<AsexualSolution> source,
                       final RandomEngine rng)
    {
        SolutionSampler<AsexualSolution> sample = parent.getInstance(source);
        for (int i = 0; i < N; ++i)
        {
            AsexualSolution origin = sample.next();
            origin.reproduce(destination, rng);
        }
    }
}

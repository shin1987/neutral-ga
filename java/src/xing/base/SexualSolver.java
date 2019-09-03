package xing.base;
import xing.base.adapter.SolverAdapter;
import xing.core.SexualSolution;
import xing.core.Solution;
import xing.core.SolutionPool;
import xing.core.RandomEngine;
import xing.core.SolutionSampler;
import xing.core.EvolvePolicy;
import xing.core.InputSolutionPool;

/**
 * <p>General framework to handle SexualSolution.</p>
 *
 * <p>To use the solver framework, one should define as minimum:</p>
 * <ul>
 * <li> How solution should reproduce (see {@link SexualSolution}
 * interface)</li>
 * <li> Factory classes to produce Solutions</li>
 * <li> Sampler classes and corresponding factories to generate the samplers
 * , which is used to control how parent generation is sampled</li>
 * <li>Default implementation for some preset samplers are available. 
 * (see {@link xing.sampler})</li>
 * </ul>
 *
 * <p>An example workflow:</p>
 * <pre>
 * {@code
 * // Suppose the follow factories have been user implemented
 * UserSolutionFactory solutionFact = new UserSolutionFactory();
 * UserSolutionPoolFactory poolFact = new UserSolutionPoolFactory();
 * RandomEngine rng = new RandomEngine(0); 
 *
 * // Initialise pool size to be 100
 * SexualSolver solver = new SexualSolver(100, poolFact, solutionFact, rng);
 *
 * // Setup how parent generations should be sampled
 * SamplerFactory1 parent1 = new SamplerFactory1();
 * SamplerFactory2 parent2 = new SamplerFactory2();
 * solver.setParentPolicy(parent1, parent2);
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
public class SexualSolver extends SolverAdapter<SexualSolution>
{
    /**
     * Construct the solver.
     * @param poolSize initial size of the solution pool
     * @param poolFactory factory class that allocate a solution pool instance
     * @param solutionFactory factory class that generates initial solutions
     * @param rng a random engine instance that will be used during evolution
     * of the solutions
     */
    public SexualSolver(
        final int poolSize,
        final SolutionPool.Factory<SexualSolution> poolFactory,
        final Solution.Factory<SexualSolution> solutionFactory,
        final RandomEngine rng)
    {
        super(poolSize, poolFactory, solutionFactory, rng);
    }

    /**
     * Defines how parent generation should be sampled from a given 
     * solution pool. The samplers are constructed exactly once every iteration.
     *
     * The first sample the individuals that initiates the reproduction process.
     * The second sampler provides individuals to exchange information with
     * the individuals sampled from the first sampler. The individual sampled 
     * from the first group may sample its patern multiple times from the second
     * sample, so it is up-to the developer to ensure the second sampler can 
     * produce enough samples. 
     * 
     * @see SexualSolution#reproduce SexualSolution's reproduce interface 
     * for more definition on the second sampler.
     *
     * @param N number of candidate to select to reproduce
     * @param parent1 Usually referes to the individuals that get a chance to 
     * reproduce.
     * @param parent2 Secondary parent sampler, which is used to feed to
     * an instace sampled from parent1 to produce next generation of solutions.
     */
    public void setParentPolicy(
        final int N,
        final SolutionSampler.Factory<SexualSolution> parent1,
        final SolutionSampler.Factory<SexualSolution> parent2)
    {
        this.setEvolvePolicy(new SexualEvolve(N, parent1, parent2));
    }
} // class SexualSolver

class SexualEvolve implements EvolvePolicy<SexualSolution>
{
    private SolutionSampler.Factory<SexualSolution> parent1 = null;
    private SolutionSampler.Factory<SexualSolution> parent2 = null;
    private final int N;
    public SexualEvolve(
        final int N,
        final SolutionSampler.Factory<SexualSolution> parent1,
        final SolutionSampler.Factory<SexualSolution> parent2)
    {
        this.N = N;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }
    
    @Override
    public void evolve(final SolutionPool<SexualSolution> destination,
                       final InputSolutionPool<SexualSolution> source,
                       final RandomEngine rng)
    {
        SolutionSampler<SexualSolution> sample1 = parent1.getInstance(source);
        SolutionSampler<SexualSolution> sample2 = parent2.getInstance(source);
        for (int i = 0; i < N; ++i)
        {
            SexualSolution origin = sample1.next();
            origin.reproduce(destination, sample2, rng);
        }
    }
}

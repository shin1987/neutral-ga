package xing.algorithm;

import xing.algorithm.adapter.AlgorithmAdapter;
import xing.base.AsexualSolver;
import xing.base.RandomEngine;
import xing.base.SolutionPool;
import xing.core.AsexualSolution;
import xing.core.Problem;
import xing.sampler.LocalBestSampler;
import xing.sampler.ShuffleSampler;


import xing.solution.asexual.GASolution;

/**
 * Baseline algorithm using Genetic Algorithm solver
 * For string construction, the following options are available:
 *     --population N     population size of the gene pool
 *     --algorithm-seed N a long integer for seeding the algorithm
 *     --iteration N      maximum iterations to run the solver
 * @author Xing Yu
 */
public class Baseline extends AlgorithmAdapter
{
    public Baseline(final String [] args)
    {
        super(args);
    }

    @Override
    public AsexualSolver newSolver(final Problem problem)
    {
        final int N = problem.getProblemSize();
        // Initialise solutions
        GASolution.init(problem);

        // Create random generator
        RandomEngine rng = new RandomEngine(this.seed);

        // Create population pool factory and solution factory
        SolutionPool.Factory<AsexualSolution> poolFact =
                new SolutionPool.Factory<AsexualSolution>();
        GASolution.Factory solutionFact =
                new GASolution.Factory(N);

        // Construct solver
        int population = (this.population >> 2) << 2;
        AsexualSolver solver =
                new AsexualSolver(population, poolFact, solutionFact, rng);

        // Configure sampling strategies
        LocalBestSampler.Factory<AsexualSolution> parentSampler =
                new LocalBestSampler.Factory<AsexualSolution>(4);
        ShuffleSampler.Factory<AsexualSolution> shuffleSampler =
                new ShuffleSampler.Factory<AsexualSolution>(rng);
        solver.setParentPolicy(population >> 2, parentSampler);
        solver.setSurviverPolicy(shuffleSampler);

        return solver;
    }
} // class Baseline

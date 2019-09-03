package xing.algorithm;
import xing.algorithm.adapter.AlgorithmAdapter;
import xing.util.ArgumentCollection;
import xing.core.SexualSolution;
import xing.core.Problem;
import xing.core.Solution;
import xing.core.SexualSolution;
import xing.core.SolutionSampler;
import xing.core.SolutionMonitor;
import xing.core.InputSolutionPool;
import xing.util.core.Probability;
import xing.sampler.RandomSampler;
import xing.sampler.StraightSampler;
import xing.sampler.StochasticSampler;
import xing.base.SolutionPool;
import xing.base.SexualSolver;
import xing.base.RandomEngine;
import xing.solution.sexual.StochasticNicheSolution;
import static xing.solution.sexual.StochasticNicheSolution.*;
import java.util.ArrayList;

/**
 * @author Xing Yu
 */
public class StochasticNicheAlgorithm extends AlgorithmAdapter
{
    private static final String DEFAULT_SAMPLER = "RANDOM";
    private final ArgumentCollection argument =
            new ArgumentCollection()
            .add("--death-rate", "" + (1.0 - DEFAULT_SURVIVE))
            .add("--migration-rate", "" + DEFAULT_MIGRATION)
            .add("--replace-rate", "" + DEFAULT_REPLACE)
            .add("--mutation-rate", "" + DEFAULT_MUTATION)
            .add("--sampler", "" + DEFAULT_SAMPLER);

    private final double survival, replace, migration, mutation;
    private final boolean randomSampler;

    public StochasticNicheAlgorithm(final String [] args)
    {
        super(args);
        argument.capture(args);
        survival = 1.0 - Double.parseDouble(argument.get("--death-rate"));
        mutation = Double.parseDouble(argument.get("--mutation-rate"));
        replace  = Double.parseDouble(argument.get("--replace-rate"));
        migration = Double.parseDouble(argument.get("--migration-rate"));
        if (argument.get("--sampler").toUpperCase().equals(DEFAULT_SAMPLER))
            randomSampler = true;
        else
            randomSampler = false;
    }

    @Override
    public SexualSolver newSolver(final Problem problem)
    {
        StochasticNicheSolution.init(problem);
        // Setup model parameters
        StochasticNicheSolution.setSurvivalRate(survival);
        StochasticNicheSolution.setReplaceRate(replace);
        StochasticNicheSolution.setMigrationRate(migration);
        StochasticNicheSolution.setMutationRate(mutation);
        StochasticNicheSolution.normaliseRate();

        // Setup factories
        Solution.Factory<SexualSolution> solutionFact =
                new StochasticNicheSolution.Factory(problem.getProblemSize());
        SolutionPool.Factory<SexualSolution> poolFact =
                new SolutionPool.Factory<SexualSolution>();

        // Setup probability function
        DynamicNormal probability = new DynamicNormal();
        StochasticNicheSolution.setProbability(probability);
        
        // Create random sampler
        RandomEngine rng = new RandomEngine(seed);
        
        // Setup sampler
        SolutionSampler.Factory<SexualSolution> parent1 =
                new StraightSampler.Factory<SexualSolution>();
        SolutionSampler.Factory<SexualSolution> parent2 =
                new RandomSampler.Factory<SexualSolution>(rng);

        // Check the sampler to be used.
        if (!randomSampler)
        {
            parent2 = new StochasticSampler.Factory<SexualSolution>(
                rng, probability);
        }
        
        // Create solver
        SexualSolver solver = new SexualSolver(
            population, poolFact, solutionFact, rng);

        solver.addSolutionMonitor(probability);
        solver.setParentPolicy(population, parent1, parent2);
        solver.setSurviverPolicy(parent1);

        return solver;
    }

    private static double estimateSTD2(
        final Solution.Factory<SexualSolution> factory,
        final RandomEngine rng)
    {
        ArrayList<SexualSolution> sample = new ArrayList<SexualSolution>();
        for (int i = 0; i < 5000; ++i)
            sample.add(factory.getInstance(rng));
        
        double mean = 0.0, var = 0.0;
        for (SexualSolution s : sample)
            mean += s.getScore();

        mean /= sample.size();
        for (SexualSolution s : sample)
        {
            double x = s.getScore() - mean;
            var += x * x;
        }
        
        return var / (sample.size() - 1.0);
    }
} // class StochasticNicheAlgorithm

class DynamicNormal implements Probability, SolutionMonitor
{
    private double std2 = 1.0;

    @Override
    public double convert(final double x)
    {
        return Math.exp(-.5 * x * x / std2) / Math.sqrt(2 * Math.PI * std2);
    }

    @Override
    public void monitor(final InputSolutionPool<? extends Solution> pop)
    {
        double mu = 0.0;
        for (int i = 0; i < pop.getSize(); ++i)
            mu += pop.at(i).getScore();
        mu /= pop.getSize();

        double var = 0.0;
        for (int i = 0; i < pop.getSize(); ++i)
        {
            double x = pop.at(i).getScore() - mu;
            var += x * x;
        }

        std2 = var / (pop.getSize() - 1);
    }
} // class DynamicNormal
            

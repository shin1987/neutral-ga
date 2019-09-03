package xing.algorithm;
import xing.algorithm.adapter.AlgorithmAdapter;
import xing.core.*;
import xing.sampler.*;
import xing.base.SolutionPool;
import xing.base.SexualSolver;
import xing.util.ArgumentCollection;
import xing.solution.sexual.NeutralSolution;

/**
 * @author Xing Yu
 */
public class NeutralModel extends AlgorithmAdapter
{
    private final double death, replace, mutation, migration;

    public NeutralModel(final String [] args)
    {
        super(args);
        ArgumentCollection argument = new ArgumentCollection();
        argument.add("--death-rate",
                     "" + NeutralSolution.DEFAULT_DEATH_RATE)
                .add("--mutation-rate",
                     "" + NeutralSolution.DEFAULT_MUTATION_RATE)
                .add("--replace-rate",
                     "" + NeutralSolution.DEFAULT_REPLACE_RATE)
                .add("--migration-rate",
                     "" + NeutralSolution.DEFAULT_MIGRATION_RATE);
        argument.capture(args);
        death = Double.parseDouble(argument.get("--death-rate"));
        replace = Double.parseDouble(argument.get("--replace-rate"));
        mutation = Double.parseDouble(argument.get("--mutation-rate"));
        migration = Double.parseDouble(argument.get("--migration-rate"));
    }

    @Override
    protected SexualSolver newSolver(final Problem problem)
    {
        final int N = problem.getProblemSize();
        NeutralSolution.init(problem);
        NeutralSolution.setDeathRate(death);
        NeutralSolution.setMigrationRate(migration);
        NeutralSolution.setReplaceRate(replace);
        NeutralSolution.setMutationRate(mutation);
        NeutralSolution.normaliseRate();
        
        NeutralSolution.Factory solutionFact = new NeutralSolution.Factory(N);
        SolutionPool.Factory<SexualSolution> poolFact =
                new SolutionPool.Factory<SexualSolution>();

        RandomEngine rng = new xing.base.RandomEngine(seed);
        SexualSolver solver =
                new SexualSolver(population, poolFact, solutionFact, rng);

        SolutionSampler.Factory<SexualSolution> parent1 =
                new StraightSampler.Factory<SexualSolution>();
        SolutionSampler.Factory<SexualSolution> parent2 =
                new RandomSampler.Factory<SexualSolution>(rng);
        solver.setParentPolicy(population, parent1, parent2);
        solver.setSurviverPolicy(parent1);
        return solver;
    }
} // class NeutralModel

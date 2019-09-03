package xing.solution.sexual;
import xing.base.SexualSolutionBase;
import xing.core.SexualSolution;
import xing.core.SolutionPool;
import xing.core.SolutionSampler;
import xing.core.RandomEngine;
import xing.core.Solution;
import xing.util.core.Probability;

import static xing.solution.util.SequencePerturbation.*;

/**
 * Solution exhibits stochastic reproduction behaviour given fitness of a new
 * solution.
 * @author Xing Yu
 */
public class StochasticNicheSolution extends SexualSolutionBase
{
    public static final double DEFAULT_SURVIVE   = 0.5;
    public static final double DEFAULT_MUTATION  = 0.2;
    public static final double DEFAULT_REPLACE   = 0.6;
    public static final double DEFAULT_MIGRATION = 0.2;

    public static void setProbability(final Probability func)
    {
        probability = func;
    }

    public static void setSurvivalRate(final double r)
    {
        rate.survival = r;
    }

    public static void setMutationRate(final double r)
    {
        rate.mutation = r;
    }

    public static void setReplaceRate(final double r)
    {
        rate.replace = r;
    }

    public static void setMigrationRate(final double r)
    {
        rate.migration = r;
    }

    public static void normaliseRate()
    {
        rate.normalise();
    }
    
    public static class Factory implements Solution.Factory<SexualSolution>
    {
        private final int N;
        public Factory(final int N)
        {
            this.N = N;
        }

        @Override
        public StochasticNicheSolution getInstance(final RandomEngine rng)
        {
            return StochasticNicheSolution.generate(N, rng);
        }
    } // class Factory

    private static final Rate rate = new Rate();
    
    private static Probability probability =
            x -> Math.exp(-0.5 * x * x) / Math.sqrt(2 * Math.PI);

    public StochasticNicheSolution(final int [] solution)
    {
        super(solution);
    }

    @Override
    public void reproduce(
        final SolutionPool<? super SexualSolution> result,
        final SolutionSampler<? extends SexualSolution> parent,
        final RandomEngine rng)
    {
        // Test if survive
        if (rng.nextDouble(0, 1) < rate.survival)
        {
            result.add(this);
        }
        else // Current solution dies
        {
            // The replacement can be:
            // 1. another solution in the pool
            // 2. a mutant from another solution in the pool
            // 3. a completely new solution
            SexualSolution replace = parent.next();
            SexualSolution mutant  = mutate(replace, rng);
            SexualSolution invader = generate(this.getLength(), rng);

            // Calculate likelihood of each result
            double replaceL =
                    probability.convert(replace.getScore()) * rate.replace;
            double mutantL =
                    probability.convert(mutant.getScore()) * rate.mutation;
            double invaderL =
                    probability.convert(invader.getScore()) * rate.migration;
            double total = replaceL + mutantL + invaderL;
            double chance = rng.nextDouble(0, total);
            if (chance < replaceL) {
                result.add(replace);
            }
            else if (chance < replaceL + mutantL) {
                result.add(mutant);
            }
            else {
                result.add(invader);
            }
        }
    }

    private static StochasticNicheSolution mutate(
        final Solution parent, final RandomEngine rng)
    {
        final int N = parent.getLength();
        // Create a copy of the solution
        int [] solution = ((StochasticNicheSolution)parent).toArray();
        // Decide mutation operation
        int i = rng.nextInt(0, N - 1), j = rng.nextInt(0, N - 1);
        switch(rng.nextInt(0, 2))
        {
            case 0:
                swap(solution, i, j);
                break;
            case 1:
                shift(solution, i, j);
                break;
            case 2:
            default:
                reverse(solution, i, j);
                break;
        }
        return new StochasticNicheSolution(solution);
    }

    private static StochasticNicheSolution generate(
        final int N, final RandomEngine rng)
    {
        int [] solution = new int[N];
        rng.randperm(solution);
        return new StochasticNicheSolution(solution);
    }
} // class StochasticNicheSolution

class Rate
{
    double survival = 0.5;
    double replace  = 1.0;
    double mutation = 1.0;
    double migration = 1.0;

    void normalise()
    {
        survival = Math.max(0.0, Math.min(1.0, survival));
        double total = replace + mutation + migration;
        replace /= total;
        mutation /= total;
        migration /= total;
    }
}


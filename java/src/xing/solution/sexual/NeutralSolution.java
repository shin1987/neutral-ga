package xing.solution.sexual;
import xing.base.SexualSolutionBase;
import xing.core.*;
import static xing.solution.util.SequencePerturbation.*;

/**
 * @author Xing Yu
 */
public class NeutralSolution extends SexualSolutionBase
{
    public static final class Factory
            implements Solution.Factory<SexualSolution>
    {
        private final int N;
        public Factory(final int N)
        {
            this.N = N;
        }

        @Override
        public NeutralSolution getInstance(final RandomEngine rng)
        {
            int [] solution = new int[N];
            rng.randperm(solution);
            return new NeutralSolution(solution);
        }
    }

    public static final double DEFAULT_DEATH_RATE = 0.05;
    public static final double DEFAULT_MUTATION_RATE = 0.5;
    public static final double DEFAULT_MIGRATION_RATE  = 0.2;
    public static final double DEFAULT_REPLACE_RATE   = 0.3;

    private static class Rate
    {
        double death = DEFAULT_DEATH_RATE;
        double mutation = DEFAULT_MUTATION_RATE;
        double migration = DEFAULT_MIGRATION_RATE;
        double replace = DEFAULT_REPLACE_RATE;
    }

    private static final Rate rate = new Rate();

    public static void normaliseRate()
    {
        double total = rate.mutation + rate.migration + rate.replace;
        rate.mutation /= total;
        rate.migration /= total;
        rate.replace /= total;
    }
    
    public static void setDeathRate(final double r)
    {
        rate.death = r;
    }

    public static void setMigrationRate(final double r)
    {
        rate.migration = r;
    }

    public static void setMutationRate(final double r)
    {
        rate.mutation = r;
    }

    public static void setReplaceRate(final double r)
    {
        rate.replace = r;
    }

    public NeutralSolution(final int [] solution)
    {
        super(solution);
    }

    @Override
    public void reproduce(
        final SolutionPool<? super SexualSolution> result,
        final SolutionSampler<? extends SexualSolution> parent,
        final RandomEngine rng)
    {
        if (rng.nextDouble(0, 1) < rate.death)
        {
            double p = rng.nextDouble(0, 1);
            if (p < rate.replace) { // raplace
                result.add(parent.next());
            } else if (p < rate.replace + rate.migration) { // migration
                int [] solution = new int[this.getLength()];
                rng.randperm(solution);
                result.add(new NeutralSolution(solution));
            }
            else { // Mutation
                int i = rng.nextInt(0, getLength() - 1);
                int j = rng.nextInt(0, getLength() - 1);
                int [] solution = toArray();
                switch (rng.nextInt(0, 2))
                {
                    case 0: // swap
                        swap(solution, i, j);
                        break;
                    case 1: //
                        reverse(solution, i, j);
                        break;
                    case 2:
                    default:
                        shift(solution, i, j);
                        break;
                }
                result.add(new NeutralSolution(solution));
            }
        }
        else

        {
            result.add(this);
        }
    }
} // class NeutralSolution


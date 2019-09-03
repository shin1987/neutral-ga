package xing.solution.asexual;
import xing.base.AsexualSolutionBase;
import xing.core.SolutionPool;
import xing.core.RandomEngine;
import xing.core.AsexualSolution;
import xing.core.Solution;
import xing.solution.util.SequencePerturbation;

/**
 * GA mutation strategy for solving TSP. Each instance produce 4 offsprings. 
 * 1 identical copy of the parent itself, 1 shifted version, 1 swapped version,
 * and 1 copy of reversed version.
 * @author Xing Yu
 */
public class GASolution extends AsexualSolutionBase
{
    /**
     * Factory class for creating random GASolution instances.
     */
    public static class Factory implements Solution.Factory<AsexualSolution>
    {
        private final int N;
        /**
         * Constructor.
         * @param N length of solution.
         */
        public Factory(final int N)
        {
            this.N = N;
        }

        @Override
        public AsexualSolution getInstance(final RandomEngine rng)
        {
            int [] solution = new int[N];
            rng.randperm(solution);
            return new GASolution(solution);
        }
    }
    
    public GASolution(final int [] solution)
    {
        super(solution);
    }

    @Override
    public void reproduce(final SolutionPool<? super AsexualSolution> result,
                          final RandomEngine rng)
    {
        // Add a copy of itself
        result.add(this);
        // Add mutations
        result.add(this.swap(rng));
        result.add(this.shift(rng));
        result.add(this.reverse(rng));
    }

    /**
     * Swap two digit of the solution.
     * @param rng random number generator.
     * @return a new instance with swapped digits.
     */
    public GASolution swap(final RandomEngine rng)
    {
        final int N = this.getLength();
        final int x = rng.nextInt(0, N - 1), y = rng.nextInt(0, N - 1);
        final int [] solution = toArray();
        return new GASolution(SequencePerturbation.swap(solution, x, y));
    }

    /**
     * Reverse the order of a segment.
     * @param rng random number generator.
     * @return a new instance with some segment reversed.
     */
    public GASolution reverse(final RandomEngine rng)
    {
        final int N = this.getLength();
        final int x = rng.nextInt(0, N - 1), y = rng.nextInt(0, N - 1);
        final int [] solution = toArray();
        return new GASolution(SequencePerturbation.reverse(solution, x, y));
    }

    /**
     * Shift (to the left) the sequence of a random segment.
     * @param rng random number generator.
     * @return new instance with the shifted effect.
     */
    public GASolution shift(final RandomEngine rng)
    {
        final int N = this.getLength();
        final int x = rng.nextInt(0, N - 1), y = rng.nextInt(0, N - 1);
        final int [] solution = toArray();
        return new GASolution(SequencePerturbation.shift(solution, x, y));
    }
} // class GASolution

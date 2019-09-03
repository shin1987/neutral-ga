package xing.core;

/**
 * Solution that provides asexual reproduction policy.
 * @author Xing Yu
 */
public interface AsexualSolution extends Solution
{
    /**
     * Generate offsprings.
     * @param offspring output solution pool
     * @param rng random number generator
     */
    public void reproduce(
        final SolutionPool<? super AsexualSolution> offspring,
        final RandomEngine                          rng);
} // interface AsexualSolution

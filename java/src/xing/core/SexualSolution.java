package xing.core;

/**
 * Solutions that provides sexual reproduction behaviour.
 * @author Xing Yu
 */
public interface SexualSolution extends Solution
{
    /**
     * Reproduction policy.
     * @param offspring output pool
     * @param parent input pool
     * @param rng random number generator
     */
    public void reproduce(
        final SolutionPool<? super SexualSolution> offspring,
        final SolutionSampler<? extends SexualSolution> parent,
        final RandomEngine rng);
} // interface SexualSolution

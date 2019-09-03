package xing.core;

/**
 * Policy defines how a solution pool should be iterated.
 * @author Xing Yu
 */
public interface SolutionSampler<E extends Solution>
{
    /**
     * Factory interface for SolutionSampler generation
     */
    public interface Factory<T extends Solution>
    {
        /**
         * Generate an instance of solution sampler.
         * @param solutionPool source solution pool to sample from
         * @return SolutionSampler instance
         */
        public SolutionSampler<T> getInstance(
            final InputSolutionPool<T> solutionPool);
    }
    
    /**
     * Returns next sample.
     * @return A solution instance
     */
    public E next();
} // interface SolutionSampler

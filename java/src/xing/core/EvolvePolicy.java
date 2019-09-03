package xing.core;

/**
 * Strategy for how a population pool should evolve.
 * @author Xing Yu
 */
public interface EvolvePolicy<E extends Solution>
{
    /**
     * Evolve population
     * @param destination output pool for storing new generation
     * @param source input pool providing source generation 
     * @param rng random value generator
     */
    public void evolve(final SolutionPool<E>      destination,
                       final InputSolutionPool<E> source,
                       final RandomEngine         rng);
} // interface EvolvePolicy

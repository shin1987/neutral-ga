package xing.core;

/**
 * Random number generator.
 *
 * @author Xing Yu
 */
public interface RandomEngine
{
    /**
     * Produces a random interger between [min, max] inclusive.
     * @param min lower bound
     * @param max upper bound
     * @return a random integer in range
     */
    public int nextInt(final int min, final int max);

    /**
     * Produces a random double floating point number in [min, max) exclusive.
     * @param min lower bound
     * @param max upper bound
     * @return a random double number in range
     */
    public double nextDouble(final double min, final double max);

    /**
     * Produces a random permutation of N numbers.
     * @param output N is derived from the length of the array on input; on 
     * exit the permutation is populated into this array.
     */
    public void randperm(final int [] output);
} // interface RandomEngine

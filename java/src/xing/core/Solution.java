package xing.core;

/**
 * Represent a solution
 * @author Xing Yu
 */
public interface Solution extends Isomophic<Solution>
{
    /**
     * Interface for Solution factories
     */
    public interface Factory<E extends Solution>
    {
        /**
         * Create an instance of solution
         * @param rng random number gnerator
         * @return a Solution instance 
         */
        public E getInstance(final RandomEngine rng);
    }

    /**
     * Length of the solution, should equal to the number of cities.
     * @return number of cities
     */
    public int getLength();

    /**
     * Get the i-th city label represented by the solution.
     * @param index i-th vertex
     * @return label of the vertex
     */
    public int at(final int index);

    /**
     * Score of the current solution, lower the better.
     * @return total distance for traversing the graph.
     */
    public double getScore();

    /**
     * Get index of where city 0 starts.
     * @return index to city 0
     */
    public int getStart();
} // interface Solution

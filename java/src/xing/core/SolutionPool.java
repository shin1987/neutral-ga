package xing.core;

/**
 * Interface for mutatble solution pool.
 * @author Xing Yu
 */
public interface SolutionPool<E extends Solution>
        extends InputSolutionPool<E>
{
    /**
     * Interface for solution pool factories
     */
    public interface Factory<T extends Solution>
    {
        /**
         * Returns solution pool instance
         * @return instance of solution pool containing solution type T
         */
        public SolutionPool<T> getInstance();
    }    
    
    public void add(final E candidate);
} // interface SolutionPool

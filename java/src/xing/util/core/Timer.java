package xing.util.core;

/**
 * Keep track of time (unit: nano-seconds)
 * @author Xing Yu
 */
public interface Timer
{
    /**
     * Start the clock.
     */
    public void tic();
    
    /**
     * Stop the clock.
     * @return elapsed time
     */
    public long toc();

    /**
     * Get total elapsed time accumulated from all tic/toc operations.
     * @return elapsed time in nano seconds
     */
    public long getTotalElapsedTime();

    /**
     * Get a new timer represent the difference between two timer instance.
     * @param timer compute this - timer
     * @return new timer holds the time difference 
     */
    public Timer diff(final Timer timer);
    /**
     * Get the sum (accumulation) of two timers
     * @param timer compute this + timer
     * @return new timer instance holding the sum
     */
    public Timer accu(final Timer timer);

    /**
     * In-place accumulation 
     * @param timer this + timer
     */
    public void add(final Timer timer);
    /**
     * In-place subtraction
     * @param timer this - timer
     */
    public void sub(final Timer timer);
} // interface Timer

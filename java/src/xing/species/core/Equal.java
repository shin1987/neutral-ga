package xing.species.core;
import xing.core.Solution;
/**
 * @author Xing Yu
 */
public interface Equal
{
    /**
     * Test if solution x and y are equal
     * @param x solution
     * @param y solution
     * @return if x and y are equal
     */
    public boolean test(final Solution x, final Solution y);

    /**
     * Create hash code for solution a
     * @param a solution to exame its hash value
     * @return hash value of a
     */
    public int hash(final Solution a);
} // interface Equal

package xing.species;
import xing.core.Solution;
import xing.species.core.Equal;
/**
 * Two species are equal if there references are equal.
 * @author Xing Yu
 */
public final class SolutionReferenceEqual implements Equal
{
    @Override
    public boolean test(final Solution x, final Solution y)
    {
        return x == y; 
    }

    @Override
    public int hash(final Solution x)
    {
        return x.hashCode();
    }
} // class SolutionReferenceEqual

package xing.species;
import xing.species.core.SpeciesSummary;
import xing.species.core.Equal;
import xing.core.Solution;
import xing.core.InputSolutionPool;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;

/**
 * Species abundance distribution descriptor.
 * @author Xing Yu
 */
public final class SADSummary implements SpeciesSummary
{
    private final Equal equal;

    private static final class Species
    {
        private final Solution solution;
        private final Equal equal;
        
        Species(final Solution s, final Equal e)
        {
            solution = s;
            equal = e;
        }

        @Override
        public boolean equals(final Object other)
        {
            if (other instanceof Species)
                return this.equal.test(solution, ((Species)other).solution);
            return super.equals(other);
        }

        @Override
        public int hashCode()
        {
            return equal.hash(this.solution);
        }
    }

    /**
     * Default constructor, use SolutionReferenceEqual comparitor. 
     */
    public SADSummary()
    {
        this(new SolutionReferenceEqual());
    }

    /**
     * Constructor with supplied equaility comparitor
     * @param equal solution equality comparison policy.
     */
    public SADSummary(final Equal equal)
    {
        this.equal = equal;
    }
    
    @Override
    public int [] summarise(
        final InputSolutionPool<? extends Solution> solution)
    {
        HashMap<Species, Integer> count = new HashMap<Species, Integer>();
        for (int i = 0; i < solution.getSize(); ++i) {
            Species s = new Species(solution.at(i), equal);
            count.compute(s, (k, v) -> (v == null) ? 1 : v + 1);
        }
        Collection<Integer> values = count.values();
        int [] ret = new int[values.size()];
        Iterator<Integer> iter = values.iterator();
        for (int i = 0; i < ret.length; ++i)
            ret[i] = iter.next();
        return ret;
    }
} // class SADSummary



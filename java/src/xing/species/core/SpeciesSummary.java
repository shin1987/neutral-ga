package xing.species.core;
import xing.core.Solution;
import xing.core.InputSolutionPool;

/**
 * Summaries the abundance of species
 * @author Xing Yu
 */
public interface SpeciesSummary
{
    public int [] summarise(final InputSolutionPool<? extends Solution> pool);
} // interface SpeciesSummary

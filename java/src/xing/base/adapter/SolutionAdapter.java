package xing.base.adapter;
import xing.core.Solution;
import xing.core.HasDistance;
import xing.core.Isomophic;
import xing.core.Problem;

/**
 * Template for Solution implementation
 * @author Xing Yu
 */
public abstract class SolutionAdapter implements Solution
{
    private final int [] solution;
    private final double score;
    private int start = 0;
    
    private static Problem problem;

    /**
     * Initialise global configuration of the solution.
     * @param configuration problem instance used to evaluate the solution.
     */
    public static void init(final Problem configuration)
    {
        SolutionAdapter.problem = configuration;
    }

    /**
     * Construct the solution with score automatically calculated.
     * @param solution traversal order of vertices (cities) in the graph
     */
    protected SolutionAdapter(final int [] solution)
    {
        this(solution, computeScore(solution));
    }

    private static double computeScore(final int [] solution)
    {
        
        final int N = solution.length;

        double sum = problem.distance(solution[0], solution[N - 1]);
        for (int i = 1; i < N; ++i)
            sum += problem.distance(solution[i - 1], solution[i]);
        return sum;
    }

    protected SolutionAdapter(final int [] solution, final double score)
    {
        this.solution = solution;
        this.score    = score;
    }
    
    @Override
    public int getLength()
    {
        return solution.length;
    }

    @Override
    public double getScore()
    {
        return this.score;
    }

    @Override
    public int at(final int i)
    {
        return this.solution[i];
    }

    /**
     * Get the index to the vertex with label 0.
     * @return traversal order of vertex 0.
     */
    @Override
    public int getStart()
    {
        while (this.at(this.start) != 0)
            ++this.start;
        return this.start;
    }

    @Override
    public boolean isomophicTo(final Solution o)
    {
        return this == o || forwardIsomophic(o) || backwardIsomophic(o);
    }

    @Override
    public String toString()
    {
        String ans = "";
        String prefix = "";
        for (int i : solution) {
            ans += prefix + i;
            prefix = "-";
        }
        return ans;
    }

    private boolean forwardIsomophic(final Solution o)
    {
        int i = this.getStart() + 1;
        int j = o.getStart() + 1;
        final int N = this.getLength();
        for (int k = 1; k < N; ++k, ++i, ++j)
            if (this.at(i % N) != o.at(j % N))
                return false;
        return true;
    }

    private boolean backwardIsomophic(final Solution o)
    {
        int i = this.getStart() + 1;
        int j = o.getStart() - 1;
        final int N = this.getLength();
        for (int k = 1; k < N; ++k, ++i, --j)
            if (this.at(i % N) != o.at((j + N) % N))
                return false;
        return true;
    }

    public int [] toArray()
    {
        int [] ans = new int[getLength()];
        for (int i = 0; i < ans.length; ++i)
            ans[i] = at(i);
        return ans;
    }
} // class SolutionAdapter

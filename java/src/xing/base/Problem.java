package xing.base;
import xing.core.HasDistance;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Problem implementation.
 * @author Xing Yu
 */
public class Problem implements xing.core.Problem
{
    private final double [] distance;
    private final int N;

    /**
     * Constructor, given vertex set as input.
     * @param collection vertex set
     * @param <E> inmplement HasDistance interface so that the distance
     * between any pair of vertices are defined.
     */
    public <E extends HasDistance<E>> Problem(final Collection<E> collection)
    {
        this(collection.size());
        final ArrayList<E> sample = new ArrayList<E>(collection);
        for (int i = 0; i < N; ++i)
        {
            E x = sample.get(i);
            for (int j = i; j < N; ++j)
            {
                E y = sample.get(j);
                this.setDistance(i, j, x.distanceTo(y));
            }
        }
    }

    /**
     * Allocate problem storage, but no distance is set.
     * @param N number of vertices of the problem.
     */
    public Problem(final int N)
    {
        this.N = N;
        this.distance = new double[N*N];
    }

    /**
     * Manually set the distance between two vertice. Assumes i-j, and j-i 
     * are equal in distance.
     * @param i vertex index
     * @param j vertex index
     * @param distance distance between i-j
     */
    public void setDistance(final int i, final int j, final double distance)
    {
        int x = i * N + j;
        int y = j * N + i;
        this.distance[x] = this.distance[y] = distance;
    }

    @Override
    public double distance(final int i, final int j)
    {
        return distance[i * N + j];
    }

    @Override
    public int getProblemSize()
    {
        return this.N;
    }
} // class Problem

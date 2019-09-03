package xing.problem;
import xing.core.Point;
import xing.core.Problem;
import xing.problem.adapter.ProblemAdapter2D;
import xing.util.ArgumentCollection;

import java.util.Collection;
import java.util.ArrayList;

/**
 * Generate cities laid on a circle.
 * @author Xing Yu
 */
public class CircularProblem extends ProblemAdapter2D
{
    public static final int DEFAULT_CIRCLES = 1;
    private final int circles;
    /**
     * Constructor.
     * @param args parses parameters from string options
     */
    public CircularProblem(final String [] args)
    {
        super(args);
        ArgumentCollection argument = new ArgumentCollection();
        argument.add("--circles", "" + DEFAULT_CIRCLES);
        argument.capture(args);
        this.circles = Integer.parseInt(argument.get("--circles"));
    }

    @Override
    public Problem generate(final Collection<? super Point> result)
    {
        ArrayList<Point> workspace = new ArrayList<Point>();
        double r = radius, step = radius / (double)circles;
        final int [] N = computeSize(problemSize, circles, radius, step); 
        for (int i = 0; i < circles; ++i, r -= step)
            makeCircle(workspace, N[i], r);
        result.addAll(workspace);
        
        return new xing.base.Problem(workspace);
    }

    private void makeCircle(final Collection<Point> workspace,
                            final int N,
                            final double R)
    {
        for (int i = 0; i < N; ++i)
        {
            double theta = Math.PI * 2 * i / N;
            Point point = new Point(Math.cos(theta) * R, Math.sin(theta) * R);
            workspace.add(point);
        }
    }

    private static int [] computeSize(final int N,
                                      final int C,
                                      final double R,
                                      final double step)
    {
        int [] ret = new int[C];
        double [] size = new double [C];
        size[0] = R;
        double totalSize = R;
        for (int i = 1; i < C; ++i) {
            size[i] = size[i - 1] - step;
            totalSize += size[i];
        }
        int totalPoint = 0;
        for (int i = 0; i < C - 1; ++i)
        {
            ret[i] = (int)Math.floor(size[i] * N / totalSize + 0.5);
            totalPoint += ret[i];
        }
        ret[C - 1] = N - totalPoint;
        return ret;
    }

    public static void main(final String [] args)
    {
        CircularProblem problem = new CircularProblem(args);
        ArrayList<Point> point = new ArrayList<Point>();
        problem.generate(point);
        for (Point pt : point)
            System.out.println(pt.x + "," + pt.y);
    }
} // class CircularProblem

package xing.problem;
import xing.core.Point;
import xing.base.Problem;
import xing.base.RandomEngine;
import xing.problem.adapter.RandomisedProblemAdapter2D;
import java.util.Collection;
import java.util.ArrayList;
import xing.util.ArgumentCollection;

/**
 * @author Xing Yu
 */
public class FuzzyRing extends RandomisedProblemAdapter2D
                                           
{
    public static final int DEFAULT_CIRCLES = 1;
    private final int circles;
    
    public FuzzyRing(final String [] args)
    {
        super(args);
        ArgumentCollection argument = new ArgumentCollection();
        argument.add("--circles", "" + DEFAULT_CIRCLES);
        argument.capture(args);
        circles = Integer.parseInt(argument.get("--circles"));
    }

    @Override
    public Problem generate(final Collection<? super Point> output)
    {
        ArrayList<Point> city = new ArrayList<Point>();
        RandomEngine rng = new RandomEngine(seed);

        final int [] N = computeSize(problemSize, circles, radius);
        final double step = step(circles, radius);
        double r = radius;
        for (int i = 0; i < circles; ++i, r -= step)
            makeRing(city, N[i], r, rng);
        output.addAll(city);
        return new Problem(city);
    }

    private void makeRing(
        final Collection<Point> city,
        final int N,
        final double R,
        final RandomEngine rng)
    {
        for (int i = 0; i < N; ++i) {
            double theta = rng.nextDouble(0, Math.PI * 2.0);
            double x = Math.cos(theta) * R +
                       rng.nextDouble(-noise, noise);
            double y = Math.sin(theta) * R +
                       rng.nextDouble(-noise, noise);
            city.add(new Point(x, y));
        }
    }
    
    private static int [] computeSize(final int N, final int C, final double R)
    {
        final double step = step(C, R);
        double totalSize = R;
        final int [] ret = new int[C];
        final double [] size = new double[C];
        size[0] = R;
        for (int i = 1; i < size.length; ++i) {
            size[i] = size[i - 1] - step;
            totalSize += size[i];
        }
        int left = N;
        for (int i = 0; i < ret.length - 1; ++i) {
            ret[i] = (int)Math.floor(N * size[i] / totalSize + 0.5);
            left -= ret[i];
        }
        ret[C - 1] = left;
        return ret;
    }

    private static double step(final int C, final double R)
    {
        return R / C;
    }

    public static void main(final String [] args)
    {
        FuzzyRing problem = new FuzzyRing(args);
        ArrayList<Point> point = new ArrayList<Point>();
        problem.generate(point);
        for (Point pnt : point)
            System.out.println(pnt.x + "," + pnt.y);
    }
} // class FuzzyRing

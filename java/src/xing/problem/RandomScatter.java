package xing.problem;
import xing.problem.adapter.RandomisedProblemAdapter2D;
import xing.base.RandomEngine;
import xing.base.Problem;
import xing.core.Point;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Points randomly scattered in a circular area.
 * @author Xing Yu
 */
public class RandomScatter extends RandomisedProblemAdapter2D
{
    @Override
    public Problem generate(final Collection<? super Point> point)
    {
        ArrayList<Point> result = new ArrayList<Point>();
        RandomEngine rng = new RandomEngine(seed);
        while (result.size() < problemSize)
        {
            double theta = rng.nextDouble(0, Math.PI * 2);
            double r = rng.nextDouble(0, radius);
            double x = Math.cos(theta) * r;
            double y = Math.sin(theta) * r;
            result.add(new Point(x, y));
        }

        point.addAll(result);
        return new Problem(result);
    }
    
    public RandomScatter(final String [] args)
    {
        super(args);
    }

    public static void main(final String [] args)
    {
        RandomScatter problem = new RandomScatter(args);
        ArrayList<Point> point = new ArrayList<Point>();
        problem.generate(point);
        for (Point pnt : point)
            System.out.println(pnt.x + "," + pnt.y);
    }
} // class RandomScatter

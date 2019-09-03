package xing.problem;
import xing.base.Problem;
import xing.core.Point;
import xing.problem.adapter.ProblemAdapter2D;
import xing.util.ArgumentCollection;
import java.util.Collection;
import java.util.ArrayList;

/**
 * @author Xing Yu
 */
public class Spiral extends ProblemAdapter2D
{
    public static final double DEFAULT_PERIOD = 1.0;
    private final double period;
    private static final ArgumentCollection argument =
            new ArgumentCollection().add("--circles", "" + DEFAULT_PERIOD);
    public Spiral(final String [] args)
    {
        super(args);
        argument.capture(args);
        period = Double.parseDouble(argument.get("--circles"));
    }
    
    @Override
    public Problem generate(final Collection<? super Point> result)
    {
        ArrayList<Point> city = new ArrayList<Point>();

        for (int i = 0; i < problemSize; ++i)
        {
            double r = i * radius / problemSize;
            double theta = i * Math.PI * 2 / problemSize * period;
            double x = Math.cos(theta) * r;
            double y = Math.sin(theta) * r;
            city.add(new Point(x, y));
        }
        
        result.addAll(city);
        return new Problem(city);
    }

    public static void main(final String [] args)
    {
        Spiral problem = new Spiral(args);
        ArrayList<Point> point = new ArrayList<Point>();
        problem.generate(point);
        for (Point pnt : point)
            System.out.println(pnt.x + "," + pnt.y);
    }
} // class Spiral

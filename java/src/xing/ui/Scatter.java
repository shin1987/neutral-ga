package xing.ui;
import xing.core.Point;
import xing.ui.adapter.CompondShapeAdapter;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Scatter plot shape, composed of Circle shapes.
 * @author Xing Yu
 */
public class Scatter extends CompondShapeAdapter
{
    /**
     * Constructor.
     * @param point points in the scatter plot.
     * @param radius size of each scatter dot.
     */
    public Scatter(final Collection<? extends Point> point, final double radius)
    {
        for (Point p : point)
            super.shape.add(new Circle(p, radius));
    }
} // class Scatter

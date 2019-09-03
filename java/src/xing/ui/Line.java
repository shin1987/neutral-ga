package xing.ui;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import xing.core.Point;
import xing.ui.adapter.CompondShapeAdapter;

/**
 * Line shape, composed of multiple LineSegment shapes.
 * @author Xing Yu
 */
public class Line extends CompondShapeAdapter
{
    /**
     * Constructor.
     * @param point specify points on the line.
     */
    public Line(final Collection<? extends Point> point)
    {
        Iterator<? extends Point> iter = point.iterator();
        Point last = iter.next();
        while (iter.hasNext())
        {
            Point current = iter.next();
            super.shape.add(new LineSegment(last, current));
            last = current;
        }
    }
} // class Line

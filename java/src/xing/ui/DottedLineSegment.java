package xing.ui;
import xing.ui.adapter.CompondShapeAdapter;
import xing.core.Point;
import java.util.Collection;
import java.util.ArrayList;

/**
 * A line segment in dotted style
 * @author Xing Yu
 */
public class DottedLineSegment extends CompondShapeAdapter
{
    private final Point start, end;
    private final int N = 50;
    
    public DottedLineSegment(final Point start, final Point end)
    {
        this.start = start;
        this.end = end;

        // Calculate magnitude
        double M = start.distanceTo(end);
        double step = M / N;
        // Compute a unit vector
        Point unit = new Point((end.x - start.x) / M, (end.y - start.y) / M);

        Point last = start;
        boolean visible = true;

        for (int i = 0; i < N; ++i)
        {
            Point next = new Point(
                last.x + unit.x * step, last.y + unit.y * step);
            if (visible)
                super.shape.add(new LineSegment(last, next));
            last = next;
            visible = !visible;
        }
    }

    @Override
    public void getAllPoints(final Collection<? super Point> c)
    {
        c.add(start);
        c.add(end);
    }
} // class DottedLineSegment

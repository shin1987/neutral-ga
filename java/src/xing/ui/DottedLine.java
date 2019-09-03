package xing.ui;
import xing.core.Point;
import xing.ui.adapter.CompondShapeAdapter;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Xing Yu
 */
public class DottedLine extends CompondShapeAdapter
{
    public DottedLine(final Collection<? extends Point> point)
    {
        Iterator<? extends Point> iterator = point.iterator();
        Point last = iterator.next();
        while (iterator.hasNext())
        {
            Point next = iterator.next();
            super.shape.add(new DottedLineSegment(last, next));
            last = next;
        }
    }
} // class DottedLine

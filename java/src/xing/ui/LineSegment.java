package xing.ui;
import xing.ui.adapter.ShapeAdapter;
import xing.ui.core.Canvas;
import xing.core.Point;
import java.util.Collection;

/**
 * A straight line as Shape.
 * @author Xing Yu
 */
public class LineSegment extends ShapeAdapter
{
    private final Point start, stop;

    /**
     * Constructor.
     * @param start start point of the segment.
     * @param stop terminal of the line segment.
     */
    public LineSegment(final Point start, final Point stop)
    {
        this.start = start;
        this.stop  = stop;
    }
    
    @Override
    public void draw(final Canvas canvas)
    {
        canvas.setColour(this.getColour());
        canvas.drawLine(start, stop);
    }

    @Override
    public void getAllPoints(final Collection<? super Point> c)
    {
        c.add(start);
        c.add(stop);
    }
} // class Line

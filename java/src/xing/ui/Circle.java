package xing.ui;
import xing.ui.core.Canvas;
import xing.ui.adapter.ShapeAdapter;
import xing.core.Point;
import java.util.Collection;

/**
 * Circle shape.
 * @author Xing Yu
 */
public class Circle extends ShapeAdapter
{
    private final double radius;
    private final Point centre;

    /**
     * Constructor
     * @param centre centre of the circle - relative to axis.
     * @param radius radius of the circle - absolute pixel size.
     */
    public Circle(final Point centre, final double radius)
    {
        this.centre = centre;
        this.radius = radius;
    }
    
    @Override
    public void draw(final Canvas canvas)
    {
        canvas.setColour(this.getColour());
        canvas.drawCircle(centre, radius);
    }

    @Override
    public void getAllPoints(final Collection<? super Point> c)
    {
        c.add(centre);
    }
} // class Circle

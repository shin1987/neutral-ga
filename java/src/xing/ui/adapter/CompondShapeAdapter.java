package xing.ui.adapter;
import xing.ui.core.Shape;
import xing.ui.core.RGBColour;
import xing.ui.core.Canvas;
import xing.core.Point;
import java.util.ArrayList;
import java.util.Collection;
/**
 * A generic implementation of compond shapes.
 * @author Xing Yu
 */
public class CompondShapeAdapter implements Shape
{
    protected ArrayList<Shape> shape = new ArrayList<Shape>();

    @Override
    public void setOutlineColour(final RGBColour colour)
    {
        for (Shape s : shape)
            s.setOutlineColour(colour);
    }

    @Override
    public void setColour(final RGBColour colour)
    {
        for (Shape s : shape)
            s.setColour(colour);
    }

    @Override
    public void draw(final Canvas canvas)
    {
        for (Shape s : shape)
            s.draw(canvas);
    }

    @Override
    public void getAllPoints(final Collection<? super Point> c)
    {
        for (Shape s : shape)
            s.getAllPoints(c);
    }
} // class CompondShapeAdapter

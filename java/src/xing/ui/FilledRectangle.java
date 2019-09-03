package xing.ui;
import xing.ui.adapter.ShapeAdapter;
import xing.ui.core.Canvas;
import java.util.Collection;
import xing.core.Point;

/**
 * @author Xing Yu
 */
public class FilledRectangle extends ShapeAdapter
{
    private final Point topLeft, bottomRight;

    public FilledRectangle(final Point topLeft, final Point bottomRight)
    {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }
    
    @Override
    public void draw(final Canvas canvas)
    {
        canvas.setColour(getColour());
        canvas.fillRectangle(topLeft, bottomRight);
        canvas.setColour(getOutlineColour());
        canvas.drawRectangle(topLeft, bottomRight);
    }

    @Override
    public void getAllPoints(final Collection<? super Point> result)
    {
        result.add(topLeft);
        result.add(bottomRight);
    }
} // class FilledRectangle

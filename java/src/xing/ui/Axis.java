package xing.ui;
import xing.core.Point;
import xing.ui.core.Drawable;
import java.util.Collection;
/**
 * Constant axis - once set the values remain unchanged during auto-update.
 * @author Xing Yu
 */
public class Axis implements xing.ui.core.Axis
{
    private final double x0, x1, y0, y1;

    /**
     * Construct the axis by specifying the bounds.
     * @param x0 left most coordinate.
     * @param y0 bottom coordinate.
     * @param x1 right coordinate.
     * @param y1 top coordinate.
     */
    public Axis(final double x0, final double y0,
                final double x1, final double y1)
    {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }
    
    @Override
    public double x0()
    {
        return this.x0;
    }

    @Override
    public double y0()
    {
        return y0;
    }

    @Override
    public double x1()
    {
        return this.x1;
    }

    @Override
    public double y1()
    {
        return this.y1;
    }

    @Override
    public Axis toFixedAxis()
    {
        return new Axis(x0, y0, x1, y1);
    }

    @Override
    public void update(final Drawable drawable)
    {
        // DO NOTHING
    }
} // class Axis

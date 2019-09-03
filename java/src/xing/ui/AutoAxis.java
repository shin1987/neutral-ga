package xing.ui;
import java.util.Collection;
import java.util.ArrayList;
import xing.core.Point;
import xing.ui.core.Drawable;
/**
 * @author Xing Yu
 */
public class AutoAxis implements xing.ui.core.Axis
{
    private double x0 = -1, x1 = 1, y0 = -1, y1 = 1;

    @Override
    public double x0()
    {
        return x0;
    }

    @Override
    public double x1()
    {
        return x1;
    }

    @Override
    public double y0()
    {
        return y0;
    }

    @Override
    public double y1()
    {
        return y1;
    }

    private void update(final Collection<? extends Point> point)
    {
        if (point.size() == 0) return;
        
        double minX = Double.POSITIVE_INFINITY, maxX = Double.NEGATIVE_INFINITY;
        double minY = minX, maxY = maxX;

        for (Point pnt : point) {
            minX = Math.min(minX, pnt.x);
            maxX = Math.max(maxX, pnt.x);
            minY = Math.min(minY, pnt.y);
            maxY = Math.max(maxY, pnt.y);
        }

        double dx = (maxX - minX) * .05;
        double dy = (maxY - minY) * .05;
        x0 = minX - dx;
        x1 = maxX + dx;
        y0 = minY - dy;
        y1 = maxY + dy;
    }

    @Override
    public void update(final Drawable drawable)
    {
        ArrayList<Point> point = new ArrayList<Point>();
        drawable.getAllPoints(point);
        this.update(point);
    }

    public Axis toFixedAxis()
    {
        return new Axis(x0, y0, x1, y1);
    }
} // class AutoAxis

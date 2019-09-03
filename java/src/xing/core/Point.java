package xing.core;

/**
 * 2D Point representation.
 * @author Xing Yu
 */
public final class Point implements xing.core.HasDistance<Point>
{
    public final double x;
    public final double y;
    
    public Point(final double x, final double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public double distanceTo(final Point other)
    {
        double a = x - other.x;
        double b = y - other.y;
        return Math.sqrt(a * a + b * b);
    }
} // class Point

package xing.ui.core;
import xing.core.Point;
import java.util.Collection;

/**
 * Policy for configuring axis of a plot.
 * @author Xing Yu
 */
public interface Axis
{
    public double x0();
    public double y0();
    public double x1();
    public double y1();
    
    /**
     * Update axis given current drawable
     * @param drawable objects contains all points in the graph
     */
    public void update(final Drawable drawable);

    /**
     * Generate constant version of the axis.
     * @return axis that does not implement update.
     */
    public Axis toFixedAxis();
} // interface Axis

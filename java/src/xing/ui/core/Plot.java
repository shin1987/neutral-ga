package xing.ui.core;
import java.util.Collection;

/**
 * Basic plot functions.
 * @author Xing Yu
 */
public interface Plot
{
    /**
     * Preparing the plot for adding new shapes.
     */
    public void begin();

    /**
     * Called when all shapes are added.
     */
    public void end();

    /**
     * Add new shapes to the plot. 
     * @param shape object to be drawn within the plot.
     */
    public void addShape(final Shape shape);
} // interface Plot

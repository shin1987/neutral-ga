package xing.ui.core;
import xing.core.Point;

/**
 * Provides basic functions for drawing shapes.
 * @author Xing Yu
 */
public interface Canvas
{
    /** 
     * Draw circle on the canvas.
     * @param centre centre of the circle, coordinates relative to axis.
     * @param radius radius of the circle, absolute pixel size.
     */
    public void drawCircle(final Point centre, final double radius);

    /**
     * Draw line on the canvas.
     * @param start start point of the line.
     * @param stop the other end of the line.
     */
    public void drawLine(final Point start, final Point stop);

    public void drawRectangle(final Point topLeft, final Point bottomRight);

    public void fillRectangle(final Point topLeft, final Point bottomRight);
    
    /**
     * Change foreground colour.
     * @param colour foreground colour to switch to.
     */
    public void setColour(final RGBColour colour);
} // interface Canvas

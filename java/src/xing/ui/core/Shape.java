package xing.ui.core;

/**
 * Defines how a shape should be drawn.
 * @author Xing Yu
 */
public interface Shape extends Drawable
{
    /**
     * Set colour of the shape.
     * @param colour desired colour assigned to the shape object.
     */
    public void setColour(final RGBColour colour);

    /**
     * Set colour of outline of the shape.
     * @param colour required colour
     */
    public void setOutlineColour(final RGBColour colour);
} // interface Shape

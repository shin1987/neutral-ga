package xing.ui.adapter;

import xing.ui.core.Shape;
import xing.ui.core.RGBColour;

/**
 * Implementing some part of Shape interface.
 * @author Xing Yu
 */
public abstract class ShapeAdapter implements Shape
{
    private RGBColour colour = new RGBColour(0, 0, 0);
    private RGBColour outlineColour = new RGBColour(0, 0, 0);
    
    @Override
    public void setColour(final RGBColour colour)
    {
        this.colour = colour;
    }

    @Override
    public void setOutlineColour(final RGBColour colour)
    {
        this.outlineColour = colour;
    }

    /**
     * Used by sub-classes to access current colour of the shape object.
     * @return RGB colour values stored internally with ShapeAdapter.
     */
    protected RGBColour getColour()
    {
        return this.colour;
    }

    /**
     * Used by sub-class to access outline colour.
     * @return RGB colour values
     */
    protected RGBColour getOutlineColour()
    {
        return this.outlineColour;
    }
} // class ShapeAdapter

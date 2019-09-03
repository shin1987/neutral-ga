package xing.ui.core;
import xing.core.Point;
import java.util.Collection;

/**
 * Subclasses must provide comply rules to be drawn onto a canvas.
 * @author Xing Yu
 */
public interface Drawable
{
    /**
     * Query points in the shape.
     * @param c output collection to store the result into.
     */
    public void getAllPoints(final Collection<? super Point> c);

    /**
     * Defines how the shape should be drawn on the canvas.
     * @param canvas target to draw the shape.
     */
    public void draw(final Canvas canvas);
} // interface Drawable

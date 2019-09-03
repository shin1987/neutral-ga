package xing.ui.swing;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import xing.ui.core.Axis;
import xing.ui.core.RGBColour;
import xing.core.Point;

/**
 * Implementing xing.ui.core.Canvas with SWING.
 * @author Xing Yu
 */
public class Canvas implements xing.ui.core.Canvas
{
    private final Graphics graphics;
    private final Axis axis;

    /**
     * Constructor.
     * @param graphics onto which the shapes will be drawn to.
     * @param axis defines the viewport of the canvas.
     */
    public Canvas(final Graphics graphics, final Axis axis)
    {
        this.graphics = graphics;
        this.axis     = axis;
    }

    @Override
    public void setColour(final RGBColour colour)
    {
        Color c = new Color(colour.r, colour.g, colour.b);
        graphics.setColor(c);
    }

    @Override
    public void drawLine(final Point start, final Point stop)
    {
        int x0 = toX(start.x), y0 = toY(start.y);
        int x1 = toX(stop.x), y1 = toY(stop.y);
        graphics.drawLine(x0, y0, x1, y1);
    }

    @Override
    public void drawCircle(final Point centre, final double r)
    {
        int x = toX(centre.x), y = toY(centre.y);
        int R = (int)(r + 0.5);
        graphics.drawOval(x - R, y - R, R << 1, R << 1);
    }

    @Override
    public void drawRectangle(final Point topLeft, final Point bottomRight)
    {
        int x0 = toX(topLeft.x), y0 = toY(bottomRight.y);
        int x1 = toX(bottomRight.x), y1 = toY(topLeft.y);
        graphics.drawRect(x0, y1, x1 - x0, y0 - y1);
    }

    @Override
    public void fillRectangle(final Point topLeft, final Point bottomRight)
    {
        int x0 = toX(topLeft.x), y0 = toY(bottomRight.y);
        int x1 = toX(bottomRight.x), y1 = toY(topLeft.y);
        graphics.fillRect(x0, y1, x1 - x0, y0 - y1);
    }
    
    private final int toX(final double x)
    {
        Rectangle rect = new Rectangle();
        graphics.getClipBounds(rect);
        double range = axis.x1() - axis.x0();
        double relative = x - axis.x0();
        int absolute = (int)(rect.x + rect.width * relative / range);
        return absolute;
    }

    private final int toY(final double y)
    {
        Rectangle rect = new Rectangle();
        graphics.getClipBounds(rect);
        double range = axis.y1() - axis.y0();
        double relative = y - axis.y0();
        int absolute = (int)(rect.y + rect.width * relative / range);
        return rect.height - absolute; // Swing's canvas is flipped
    }
} // class Canvas

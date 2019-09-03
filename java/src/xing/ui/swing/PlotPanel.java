package xing.ui.swing;
import xing.ui.core.Drawable;
import xing.ui.core.Axis;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * A general plot container
 * @author Xing Yu
 */
public class PlotPanel<T extends Drawable> extends JPanel
{
    private static final long serialVersionUID = 0L;

    private T drawable = null;
    private Axis axis = null;

    public PlotPanel(final T drawable, final Axis axis)
    {
        this.drawable = drawable;
        this.axis = axis;
        repaint();
    }

    public void setPlot(final T drawable)
    {
        this.drawable = drawable;
        repaint();
    }

    public void setAxis(final Axis axis)
    {
        this.axis = axis;
        repaint();
    }

    public T getPlot()
    {
        return drawable;
    }

    public void finaliseAxis()
    {
        this.axis.update(drawable);
        this.axis = axis.toFixedAxis();
    }

    @Override
    public void paint(final Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        Rectangle rect = new Rectangle();
        g2.getClipBounds(rect);
        g2.setBackground(Color.WHITE);
        g2.clearRect(rect.x, rect.y, rect.width, rect.height);
        axis.update(drawable);
        Canvas canvas = new Canvas(g2, axis);
        drawable.draw(canvas);
    }
} // class PlotPanel

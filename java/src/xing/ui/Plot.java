package xing.ui;
import xing.core.Point;
import xing.ui.core.Shape;
import xing.ui.core.Canvas;
import xing.ui.core.RGBColour;
import xing.ui.core.ColourManager;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Plot implementation, which itself is also a shape so that it can be drawn
 * onto some Canvas instance.
 * @author Xing Yu
 */
public class Plot implements xing.ui.core.Plot, xing.ui.core.Drawable
{
    private ArrayList<Shape> component = new ArrayList<Shape>();
    private ArrayList<Shape> buffer = new ArrayList<Shape>();
    
    private ColourManager colourManager;

    /**
     * Constructor.
     * @param colourManager palette generator
     */
    public Plot(final ColourManager colourManager)
    {
        this.colourManager = colourManager;
    }

    /**
     * Constructor - default tp HSVPalet
     */
    public Plot()
    {
        this(new HSVPalette());
    }

    @Override
    public void getAllPoints(final Collection<? super Point> c)
    {
        for (int i = 0; i < component.size(); ++i)
            component.get(i).getAllPoints(c);
    }
    
    @Override
    public void addShape(final Shape shape)
    {
        // component.add(shape);
        buffer.add(shape);
    }

    /**
     * Clear the plot and prepare for adding new shapes.
     */
    @Override
    public void begin()
    {
        // component = new ArrayList<Shape>();
        buffer = new ArrayList<Shape>();
    }

    /**
     * Called after all shapes are added. Potentially calculate a distinct 
     * colour for each shape in the plot.
     */
    @Override
    public void end()
    {
        // Generate colours
        ArrayList<RGBColour> colour = new ArrayList<RGBColour>();
        colourManager.generate(colour, buffer.size());
        for (int i = 0; i < buffer.size() && i < buffer.size(); ++i)
            buffer.get(i).setColour(colour.get(i));
        synchronized (this) {
            component = buffer;
        }
    }

    @Override
    public void draw(final Canvas canvas)
    {
        canvas.setColour(new RGBColour(255, 255, 255));
        ArrayList<Shape> copy = null;
        synchronized (this) {
            copy = component;
        }
        for (int i = 0; i < copy.size(); ++i)
            copy.get(i).draw(canvas);
    }
} // class Plot


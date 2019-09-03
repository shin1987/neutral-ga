package xing.ui;
import xing.ui.core.Shape;
import xing.ui.core.Canvas;
import xing.ui.core.RGBColour;
import xing.ui.adapter.CompondShapeAdapter;
import xing.core.Point;
import java.util.ArrayList;
import java.util.Collection;
import xing.core.InputHistogram;

/**
 * @author Xing Yu
 */
public class HistogramGraph extends CompondShapeAdapter
{
    private final double top, left, right;

    public HistogramGraph(final InputHistogram hist)
    {
        left = hist.getLowerBound();
        right = hist.getUpperBound();
        double step = hist.getStepSize();
        double x = left;
        double max = hist.get(0);

        for (int i = 0; i < hist.getNumberOfBins(); ++i, x += step)
        {
            double y = hist.get(i);
            max = Math.max(y, max);

            Point topLeft = new Point(x, y);
            Point bottomRight = new Point(x + step, 0);
            super.shape.add(new FilledRectangle(topLeft, bottomRight));
        }
        top = max;
    }

    @Override
    public void getAllPoints(final Collection<? super Point> point)
    {
        point.add(new Point(left, 0));
        point.add(new Point(right, top));
    }
} // class HistogramPlot

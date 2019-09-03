package xing.ui;
import xing.core.Solution;
import xing.core.Point;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An extension to Plot class with plotSolution method. SolutionPlot maintains
 * the list of vertices in the graph, and always plot them. In addition, it also
 * plots each solutions added to the plot.
 * @author Xing Yu
 */
public class SolutionPlot extends Plot
{
    private ArrayList<Point> city;

    public SolutionPlot()
    {
        this(new ArrayList<Point>());
    }
    
    /**
     * Constructor.
     * @param city vertices of the graph, which will be displayed as scatter
     * plot.
     */
    public SolutionPlot(final Collection<? extends Point> city)
    {
        setCity(city);
        begin(); end(); // Force to update shapes        
    }
    
    public void setCity(final Collection<? extends Point> city)
    {
        this.city = new ArrayList<Point>(city);
    }
    
    @Override
    public void end()
    {
        this.addShape(new Scatter(city, 3));
        super.end();
    }

    public void plotSolution(final Solution solution)
    {
        this.plotSolution(solution, '-');
    }

    public void plotSolution(final Solution solution, final char style)
    {
        final int N = city.size();
        ArrayList<Point> line = new ArrayList<Point>();
        for (int i = 0; i < N; ++i)
            line.add(city.get(solution.at(i)));
        line.add(city.get(solution.at(0)));

        switch (style) {
            case '-':
                this.addShape(new Line(line));
                break;
            case '.':
                this.addShape(new DottedLine(line));
                break;
        }
    }
} // class SolutionPlot

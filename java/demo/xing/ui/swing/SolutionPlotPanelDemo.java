package xing.ui.swing;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import xing.core.Point;
import java.util.ArrayList;
import xing.ui.Axis;

/**
 * @author Xing Yu
 */
public class SolutionPlotPanelDemo extends JFrame
{
    private SolutionPlotPanel panel;
    static final int N = 20;
    public SolutionPlotPanelDemo()
    {
        ArrayList<Point> city = new ArrayList<Point>();
        for (int i = 0; i < N; ++i)
        {
            double theta = i * 2 * Math.PI / N;
            city.add(new Point(Math.cos(theta), Math.sin(theta)));
        }
        panel = new SolutionPlotPanel(city, new Axis(-2, -2, 2, 2));
        panel.setPreferredSize(new java.awt.Dimension(400, 400));
        this.getContentPane().add(panel);
        panel.begin();
        panel.plotSolution(new Solution());
        panel.end();
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public static void main(String [] args)
    {
        JFrame app = new SolutionPlotPanelDemo();
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run()
                {
                    app.setVisible(true);
                }
            });
    }
} //  SolutionPlotPanelDemo

class Solution implements xing.core.Solution
{
    @Override
    public double getScore()
    {
        return 0.0;
    }

    @Override
    public int getLength()
    {
        return SolutionPlotPanelDemo.N;
    }

    @Override
    public int at(final int i)
    {
        return i;
    }
}

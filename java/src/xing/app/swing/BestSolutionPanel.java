package xing.app.swing;
import xing.ui.SolutionPlot;
import xing.ui.AutoAxis;
import xing.core.Point;
import xing.core.Solution;
import xing.core.InputSolutionPool;
import xing.core.Observable;
import xing.core.SolutionMonitor;
import java.util.Collection;

/**
 * @author Xing Yu
 */
class BestSolutionPanel extends SubPlotPanel<SolutionPlot>
{
    private static final long serialVersionUID = 0L;
    BestSolutionPanel()
    {
        super("Current best solution", new SolutionPlot(), new AutoAxis());
    }

    void reset(final Collection<? extends Point> city)
    {
        super.reset();
        getPlotPanel().setAxis(new AutoAxis());
        getPlotPanel().getPlot().setCity(city);
        getPlotPanel().finaliseAxis();
    }

    void registerAlgorithm(final Observable obs)
    {
        if (!isActive())
            return;
        obs.addSolutionMonitor(new SolutionMonitor() {
                private Solution best = null;
                public void monitor(InputSolutionPool<? extends Solution> pool)
                {
                    getTimer().tic();
                    Solution cand = pool.getBestSolution();
                    if (best == null || cand.getScore() < best.getScore())
                        best = cand;
                    getPlotPanel().getPlot().begin();
                    getPlotPanel().getPlot().plotSolution(cand, '.');
                    getPlotPanel().getPlot().plotSolution(best);
                    getPlotPanel().getPlot().end();
                    getPlotPanel().repaint();
                    getTimer().toc();
                }
            });
    }
} // class BestSolutionPanel

package xing.app.swing;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.util.Collection;
import xing.core.Point;
import xing.core.Observable;
/**
 * @author Xing Yu
 */
class ResultPanel extends JPanel
{
    private static final long serialVersionUID = 0L;

    private final BestSolutionPanel bestSolution = new BestSolutionPanel();
    private final SADPanel sadSummary = new SADPanel();

    ResultPanel()
    {
        this.setLayout(new FlowLayout());
        this.add(bestSolution);
        this.add(sadSummary);
    }

    void reset(final Collection<? extends Point> city)
    {
        bestSolution.reset(city);
        sadSummary.reset();
    }

    void registerAlgorithm(final Observable algorithm)
    {
        bestSolution.registerAlgorithm(algorithm);
        sadSummary.registerAlgorithm(algorithm);
    }

    void registerMonitorTimer(final SummaryPanel panel)
    {
        bestSolution.registerMonitorTimer(panel);
        sadSummary.registerMonitorTimer(panel);
    }

    void flush()
    {
        sadSummary.flush();
    }

    SADPanel getSADPanel()
    {
        return sadSummary;
    }

    BestSolutionPanel getBestSolutionPanel()
    {
        return bestSolution;
    }
} // class ResultPanel

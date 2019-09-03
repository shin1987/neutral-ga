package xing.app.swing;
import xing.ui.Plot;
import xing.ui.AutoAxis;
import xing.species.SADSummary;
import xing.species.SolutionIsomophicEqual;
import xing.species.core.Equal;
import xing.species.core.SADReport;
import xing.species.RankedReport;
import xing.species.BinnedReport;
import xing.core.InputSolutionPool;
import xing.core.Solution;
import xing.core.Observable;
import xing.core.SolutionMonitor;
import xing.ui.HistogramGraph;
import java.util.Arrays;

/**
 * @author Xing Yu
 */
class SADPanel extends SubPlotPanel<Plot>
{
    private static final long serialVersionUID = 0L;

    public static final int RANKED_REPORT = 0;
    public static final int LOGBIN_REPORT = 1;
    
    static final Equal DEFAULT_EQUAL = new SolutionIsomophicEqual();
    static final int DEFAULT_FREQUENCY = 50;
    
    private SADSummary sadSummary = new SADSummary(DEFAULT_EQUAL);
    private SADReport sadReport = null;
            
    private InputSolutionPool<? extends Solution> solution = null;
    
    private int frequency = DEFAULT_FREQUENCY;

    SADPanel()
    {
        super("Species abundance distribution", new Plot(), new AutoAxis());
        this.setReport(LOGBIN_REPORT);
    }

    void setReport(final int type)
    {
        switch (type) {
            case RANKED_REPORT:
                sadReport = new RankedReport();
                break;
            case LOGBIN_REPORT:
                sadReport = new BinnedReport(x -> Math.log(x) / Math.log(2));
                break;
            default:
                break;
        }
        flush();
    }

    void setSpeciesEqualTest(final Equal equal)
    {
        this.sadSummary = new SADSummary(equal);
    }

    void registerAlgorithm(final Observable algorithm)
    {
        if (!isActive())
            return;
        algorithm.addSolutionMonitor(new SolutionMonitor() {
                private int counter = 0;
                @Override
                public void monitor(InputSolutionPool<? extends Solution> p)
                {
                    getTimer().tic();
                    solution = p;
                    if (counter % frequency == 0)
                        flush();
                    ++counter;
                    getTimer().toc();
                }
            });
    }

    void setSampleFrequency(final int freq)
    {
        this.frequency = Math.max(1, freq);
    }

    void flush()
    {
        getPlotPanel().getPlot().begin();
        if (solution != null) {
            int [] summary = sadSummary.summarise(solution);
            HistogramGraph hist = new HistogramGraph(sadReport.report(summary));
            getPlotPanel().getPlot().addShape(hist);
        }
        getPlotPanel().getPlot().end();
        getPlotPanel().repaint();
    }

    @Override
    void reset()
    {
        super.reset();
        solution = null;
        flush();
        repaint();
    }
} // class SADPanel

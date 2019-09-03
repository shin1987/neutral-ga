package xing.app.swing;

import xing.core.Solution;
import xing.core.InputSolutionPool;
import xing.core.SolutionMonitor;
import xing.core.Observable;
import xing.util.core.Timer;

import java.util.HashSet;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Xing Yu
 */
class SummaryPanel extends JPanel implements SolutionMonitor 
{
    private static final Dimension TEXTAREA_SIZE = new Dimension(200, 200);
    private static final long serialVersionUID = 0L;
    private Timer timer, globalTimer;
    private ArrayList<Timer> monitorTimer;
    private Solution best;
    private int iteration, solutionCount, updateCount, foundAtIteration;
    private HashSet<Solution> uniqueSolution;

    private final UniqueSolutionMonitor uniqueMonitor =
            new UniqueSolutionMonitor();
    
    private final JTextArea summary = new JTextArea();
    private final JCheckBox uniqueSolutionOption =
            new JCheckBox("Monitor unique solution count");
    
    SummaryPanel()
    {
        super();
        reset();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel("Execution summary"));

        uniqueSolutionOption.setAlignmentX(JCheckBox.LEFT_ALIGNMENT);
        uniqueSolutionOption.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    boolean checked = uniqueSolutionOption.isSelected();
                    uniqueMonitor.setEnabled(checked);
                }
            });

        this.add(uniqueSolutionOption);
        
        summary.setEditable(false);
        summary.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(summary);
        scroll.setPreferredSize(TEXTAREA_SIZE);
        scroll.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        scroll.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        super.add(scroll);        
    }

    void registerGlobalTimer(final Timer timer)
    {
        globalTimer = timer;
    }

    void registerMonitorTimer(final Timer timer)
    {
        monitorTimer.add(timer);
    }

    void registerAlgorithm(final Observable algorithm)
    {
        algorithm.addSolutionMonitor(this);
    }

    void reset()
    {
        timer = new xing.util.Timer();
        globalTimer = null;
        monitorTimer = new ArrayList<Timer>();
        best = null;
        iteration = 0;
        solutionCount = 0;
        updateCount = 0;
        foundAtIteration = 0;
        uniqueSolution = new HashSet<Solution>();
        summary.setText(null);
    }

    private void appendln(final String text)
    {
        summary.append(text + "\n");
    }

    private void appendln()
    {
        appendln("");
    }
    
    void displaySummary()
    {
        appendln("Total running time: " + globalTimer);
        Timer temp = globalTimer.diff(timer);
        for (Timer t : monitorTimer)
            temp = temp.diff(t);
        appendln("Algorithm time: " + temp);
        appendln();
        appendln("Total iteration: " + iteration);
        appendln("Total update: " + updateCount);
        appendln("Total candidate: " + solutionCount);
        if (uniqueMonitor.isEnabled())
            appendln("Unique candidate: " + uniqueSolution.size());
        appendln();
        appendln("Solution found at: " + foundAtIteration);
        appendln("Solution cost: " + best.getScore());
        appendln("Solution:");
        appendln("" + best);
    }

    @Override
    public void setEnabled(boolean v)
    {
        super.setEnabled(v);
        uniqueSolutionOption.setEnabled(v);
    }
    
    @Override
    public void monitor(final InputSolutionPool<? extends Solution> solution)
    {
        timer.tic();

        iteration++;
        Solution candidate = solution.getBestSolution();
        if (best == null || candidate.getScore() < best.getScore())
        {
            best = candidate;
            updateCount++;
            foundAtIteration = iteration;
        }

        solutionCount += solution.getSize();
        uniqueMonitor.monitor(uniqueSolution, solution);
        timer.toc();
    }
} // class SummaryPanel

class UniqueSolutionMonitor
{
    private static class DefaultMonitor
    {
        void monitor(final HashSet<Solution> uniq,
                     final InputSolutionPool<? extends Solution> pop)
        {
        }
    }

    private static class Monitor extends DefaultMonitor
    {
        @Override
        void monitor(final HashSet<Solution> uniq,
                     final InputSolutionPool<? extends Solution> pop)
        {
            pop.getAllSolutions(uniq);
        }
    }

    private static final DefaultMonitor DISABLED = new DefaultMonitor();
    private static final DefaultMonitor ENABLED  = new Monitor();

    private DefaultMonitor action = DISABLED;
    
    void setEnabled(boolean enabled) {
        action = enabled ? ENABLED : DISABLED;
    }

    boolean isEnabled()
    {
        return action == ENABLED;
    }

    void monitor(final HashSet<Solution> uniq,
                 final InputSolutionPool<? extends Solution> pop)
    {
        action.monitor(uniq, pop);
    }
}

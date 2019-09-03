package xing.app.swing;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

import xing.core.*;
import xing.ui.core.Application;
import xing.util.core.ClassScanner;
import xing.util.InternalClassScanner;
import xing.util.ClassLoader;
import xing.util.Timer;

/**
 * @author Xing Yu
 */
public class GUIRunner implements Application, ProgressMonitor
{
    private final String SOLVE_ACTION = "Solve";
    private final String KILL_ACTION = "Kill";
    private final JFrame window = new JFrame("Travel Salesman Problem");
    private final JButton startButton = new JButton(SOLVE_ACTION);
    private final JProgressBar progressBar = new JProgressBar();
    private final ProblemPanel problemPanel = new ProblemPanel();
    private final SummaryPanel summaryPanel = new SummaryPanel();
    private final ResultPanel resultPanel = new ResultPanel();
    private final OptionPanel optionPanel = new OptionPanel(resultPanel);
    private Algorithm algorithm = null;
    
    public GUIRunner()
    {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void registerSolver(final Algorithm algorithm)
    {
        this.algorithm = algorithm;
        if (algorithm instanceof Observable) {
            Observable obs = (Observable)algorithm;
            obs.addSolutionMonitor(summaryPanel);
            resultPanel.registerAlgorithm(obs);
        }

        if (algorithm instanceof Progressive) {
            Progressive prg = (Progressive)algorithm;
            prg.addProgressMonitor(this);
        }
    }

    @Override
    public void monitor(final long progress, final long total)
    {
        if (total == 0)
            return;
        long value = progress * progressBar.getMaximum() / total;
        progressBar.setValue((int)value);
    }
    
    @Override
    public void start()
    {
        setupGUI();
        setupListener();
        
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run()
                {
                    window.setVisible(true);
                }
            });
    } // start

    private void setupGUI()
    {
        BorderLayout layout = new BorderLayout();
        window.getContentPane().setLayout(layout);

        // North panel
        window.add(problemPanel, BorderLayout.NORTH);

        // East panel
        window.add(summaryPanel, BorderLayout.EAST);

        // Central panel
        window.add(resultPanel, BorderLayout.CENTER);

        // West panel
        window.add(optionPanel, BorderLayout.WEST);
        
        // South panel
        setupSouthPanel();
        // Pack GUI
        window.pack();
    } // setupGUI
    
    private void setupSouthPanel()
    {
        JPanel workspace = new JPanel(new GridLayout(0, 1));
        workspace.add(progressBar);
        workspace.add(startButton);
        window.add(workspace, BorderLayout.SOUTH);
    }
    
    private void setupListener()
    {
        startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (startButton.getText() == SOLVE_ACTION) {
                        try {
                            runSolver();
                        }
                        catch (Exception e) {
                            System.err.println(e);
                        }
                    }
                    else {
                        algorithm.kill();
                    }
                }
            });
    } // setupListener    

    private void runSolver() throws Exception
    {
        // Load programs
        ArrayList<Point> city = new ArrayList<Point>();
        Problem problem = loadProblem(city);
        Algorithm algorithm = loadAlgorithm();

        // Clear all current result
        summaryPanel.reset();
        resultPanel.reset(city);

        // Register monitors
        registerSolver(algorithm);

        // Register timers.
        Timer timer = new Timer();
        summaryPanel.registerGlobalTimer(timer);
        resultPanel.registerMonitorTimer(summaryPanel);
        
        // Disable action buttons
        setGUIBusy(true);
        // Execute algorithm
        Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    timer.tic();
                    algorithm.execute(problem);
                    timer.toc();

                    summaryPanel.displaySummary();

                    progressBar.setValue(0);
                    setGUIBusy(false);
                }
            });
        t.start();
    }

    private void setGUIBusy(final boolean busy)
    {
        boolean enabled = !busy;
        // startButton.setEnabled(enabled);
        if (enabled) 
            startButton.setText(SOLVE_ACTION);
        else
            startButton.setText(KILL_ACTION);
        summaryPanel.setEnabled(enabled);
        resultPanel.flush();
    }

    private Algorithm loadAlgorithm()
            throws NullSelectionException
    {
        ClassLoader clazzLoader = new ClassLoader();
        try {
            Class<?> clazz = problemPanel.getSelectedAlgorithm();
            String [] args = problemPanel.getAlgorithmArgs();
            return clazzLoader.load(Algorithm.class, clazz, args);
        }
        catch (NullSelectionException e) {
            System.err.println("No algorithm has been selected");
            throw e;
        }
    }

    private Problem loadProblem(final ArrayList<Point> city)
            throws NullSelectionException
    {
        ClassLoader clazzLoader = new ClassLoader();
        try // Load problem
        {
            Class<?> clazz = problemPanel.getSelectedProblem();
            String [] args = problemPanel.getProblemArgs();
            ProblemGenerator2D gen = clazzLoader.load(
                ProblemGenerator2D.class, clazz, args);
            return gen.generate(city);
        }
        catch (NullSelectionException e) {
            System.err.println("Problem generator not selected");
            throw e;
        }
    }
    
    public static void main(final String [] args)
    {
        ClassScanner [] problemScanner = {
            new InternalClassScanner("problem")};
        ClassScanner [] algorithmScanner = {
            new InternalClassScanner("algorithm")};
        
        GUIRunner app = new GUIRunner();
        app.problemPanel.registerProblem(problemScanner);
        app.problemPanel.registerAlgorithm(algorithmScanner);
        
        app.start();
    }
} // class GUIRunner

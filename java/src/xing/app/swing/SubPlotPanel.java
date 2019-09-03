package xing.app.swing;
import xing.ui.swing.PlotPanel;
import xing.ui.core.Drawable;
import xing.ui.core.Axis;
import xing.util.Timer;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
/**
 * @author Xing Yu
 */
class SubPlotPanel<T extends Drawable> extends JPanel
{
    private static final long serialVersionUID = 0L;
    private JLabel title;
    private PlotPanel<T> plot;
    private boolean active = true;
    private Timer timer = new Timer();
    
    SubPlotPanel(final String title, final T drawable, final Axis axis)
    {
        this.title = new JLabel(title);
        this.plot = new PlotPanel<T>(drawable, axis);
        this.setLayout(new BorderLayout());

        this.plot.setPreferredSize(new Dimension(500, 500));
        
        this.add(this.title, BorderLayout.NORTH);
        this.add(this.plot, BorderLayout.CENTER);
    }

    protected Timer getTimer()
    {
        return timer;
    }

    void reset()
    {
        timer = new Timer();
    }

    void registerMonitorTimer(final SummaryPanel target)
    {
        target.registerMonitorTimer(timer);
    }
    
    boolean isActive()
    {
        return active;
    }
    
    void setActive(final boolean active)
    {
        this.active = active;
        this.setVisible(active);
    }
    
    PlotPanel<T> getPlotPanel()
    {
        return plot;
    }

    void setTitle(final String title)
    {
        this.title.setText(title);
    }
} // class SubPlotPanel

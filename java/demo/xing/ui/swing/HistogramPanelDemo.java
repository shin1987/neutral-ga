package xing.ui.swing;
import javax.swing.*;
import java.awt.*;

/**
 * @author Xing Yu
 */
public class HistogramPanelDemo
{
    public static void main(String [] args)
    {
        HistogramPanel panel = new HistogramPanel();
        panel.setPreferredSize(new Dimension(500, 500));
        panel.setHistogram(new int[]{2, 0, 1});
        JFrame wnd = new JFrame("Histogram");
        wnd.add(panel);
        wnd.pack();
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run()
                {
                    wnd.setVisible(true);
                }
            });
    }
} //  HistogramPanelDemo

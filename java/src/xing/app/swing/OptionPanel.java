package xing.app.swing;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Xing Yu
 */
public class OptionPanel extends JPanel
{
    private static final long serialVersionUID = 0L;
    private final SADOptionPanel sadOptionPanel;     
    public OptionPanel(final ResultPanel resultPanel)
    {
        sadOptionPanel = new SADOptionPanel(resultPanel);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(sadOptionPanel);
    }
} // class ResultOptionPanel

class SADOptionPanel extends JPanel implements ActionListener, ChangeListener
{
    private static final long serialVersionUID = 0L;
    private final ResultPanel resultPanel;

    private static final String LOGBIN = "Log-bin report";
    private static final String RANKED = "Ranked report";

    private static Component left(final Component comp)
    {
        Box box = Box.createHorizontalBox();
        box.add(comp);
        box.add(Box.createHorizontalGlue());
        return box;
    }
    
    SADOptionPanel(final ResultPanel resultPanel)
    {
        this.resultPanel = resultPanel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(left(new JLabel("Species abudance distribution:")));

        ButtonGroup group = new ButtonGroup();
        JRadioButton button = null;

        button = new JRadioButton(LOGBIN, true);
        resultPanel.getSADPanel().setReport(SADPanel.LOGBIN_REPORT);
        button.addActionListener(this);
        group.add(button);
        this.add(left(button));
        button = new JRadioButton(RANKED, false);
        button.addActionListener(this);
        group.add(button);
        this.add(left(button));

        this.add(left(new JLabel("Species abundance sample frequency:")));
        JSpinner spinner = null;
        spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(50, 0, Integer.MAX_VALUE, 10));
        JPanel temp = new JPanel(new FlowLayout()); // Workaround spinner size
        temp.add(spinner);
        spinner.addChangeListener(this);
        this.add(left(temp));
        resultPanel.getSADPanel().setSampleFrequency(50);
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        Object source = evt.getSource();
        if (source instanceof JRadioButton)
        {
            JRadioButton button = (JRadioButton)source;
            if (button.getText().equals(LOGBIN)) {
                resultPanel.getSADPanel().setReport(SADPanel.LOGBIN_REPORT);
            }
            else if (button.getText().equals(RANKED)) {
                resultPanel.getSADPanel().setReport(SADPanel.RANKED_REPORT);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent evt)
    {
        Object source = evt.getSource();
        if (source instanceof JSpinner)
        {
            SpinnerModel model = ((JSpinner)source).getModel();
            int frequency = (Integer)model.getValue();
            resultPanel.getSADPanel().setSampleFrequency(frequency);
        }
    }
}

package xing.app.swing;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.ArrayList;

import xing.core.Algorithm;
import xing.core.ProblemGenerator2D;
import xing.util.core.ClassScanner;
import xing.util.FilteredClassScanner;

/**
 * @author Xing Yu
 */
class ProblemPanel extends JPanel
{
    public static final long serialVersionUID = 0L;

    private final JComboBox<ClassItem> problem = new JComboBox<ClassItem>();
    private final JComboBox<ClassItem> algorithm = new JComboBox<ClassItem>();
    private final JTextField problemArgs = new JTextField(16);
    private final JTextField algorithmArgs = new JTextField(16);
    private final GridBagLayout layout = new GridBagLayout();

    ProblemPanel()
    {
        this.setLayout(layout);
        addControl("Problem", problem, problemArgs);
        addControl("Algorithm", algorithm, algorithmArgs);
    }

    private void addControl(final String name,
                            final JComboBox<ClassItem> option,
                            final JTextField args)
    {
        GridBagConstraints c = new GridBagConstraints();
        JLabel label = null;

        c.fill = GridBagConstraints.BOTH;

        // add label
        label = new JLabel(name);
        c.weightx = 1.0;
        layout.setConstraints(label, c);
        this.add(label);
        // add combo-box
        c.weightx = 2.0;
        layout.setConstraints(option, c);
        this.add(option);
        // add another label
        label = new JLabel("Arguments");
        c.weightx = 1.0;
        layout.setConstraints(label, c);
        this.add(label);
        // add argument text field - and end the line
        c.weightx = 2.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(args, c);
        this.add(args);
    }

    String [] getProblemArgs()
    {
        return this.problemArgs.getText().split(" ");
    }

    String [] getAlgorithmArgs()
    {
        return this.algorithmArgs.getText().split(" ");
    }
    
    Class<?> getSelectedAlgorithm() throws NullSelectionException
    {
        return getSelectedClass(algorithm);
    }
    
    Class<?> getSelectedProblem() throws NullSelectionException
    {
        return getSelectedClass(problem);
    }

    private static Class<?> getSelectedClass(final JComboBox<ClassItem> box)
            throws NullSelectionException
    {
        int index = box.getSelectedIndex();
        ClassItem item = box.getItemAt(index);
        if (item == null)
            throw new NullSelectionException();
        return item.clazz;
    }

    void registerProblem(final ClassScanner [] scanner)
    {
        registerComboBox(problem, scanner, ProblemGenerator2D.class);
    }

    void registerAlgorithm(final ClassScanner [] scanner)
    {
        registerComboBox(algorithm, scanner, Algorithm.class);
    }

    private static void registerComboBox(
        final JComboBox<ClassItem> box,
        final ClassScanner [] scanner,
        final Class<?> target)
    {
        ArrayList<Class<?>> clazz = new ArrayList<Class<?>>();
        for (ClassScanner s : scanner) {
            ClassScanner filtered = new FilteredClassScanner(s, target);
            filtered.scan(clazz);
        }

        for (Class<?> c : clazz)
            box.addItem(new ClassItem(c));
    }
} // class ProblemPanel

class ClassItem
{
    final Class<?> clazz;

    ClassItem(final Class<?> clazz)
    {
        this.clazz = clazz;
    }
    
    @Override
    public String toString()
    {
        return clazz.getCanonicalName();
    }
}

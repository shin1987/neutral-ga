package xing.ui.core;
import java.util.Collection;

/**
 * Policy for generating colours.
 * @author Xing Yu
 */
public interface ColourManager
{
    /**
     * Generate colours.
     * @param pallate output collection of RGBColours.
     * @param N number of colours to generate.
     */
    public void generate(final Collection<? super RGBColour> pallate,
                         final int N);
} // interface ColourManager

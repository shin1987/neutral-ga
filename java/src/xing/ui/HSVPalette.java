package xing.ui;
import xing.ui.core.ColourManager;
import xing.ui.core.RGBColour;
import java.util.Collection;

/**
 * Generate distinct colours by varying Hues of the palatte.
 * @author Xing Yu
 */
public class HSVPalette implements ColourManager
{
    @Override
    public void generate(final Collection<? super RGBColour> palette,
                         final int N)
    {
        double index = 0.0;
        while (index < N)
        {
            palette.add(generate(index / N));
            index += 1;
        }
    }

    private static RGBColour generate(final double h)
    {
        final int H = (int)(h * 360) / 60;
        final double S = 1.0, V = 0.8, C = S * V, M = V - C;
        final double X = C * (1 - Math.abs(H % 2 - 1));
        double r = 0, g = 0, b = 0;
        switch (H)
        {
            case 0:
                r = C;
                g = X;
                break;
            case 1:
                r = X;
                g = C;
                break;
            case 2:
                g = C;
                b = X;
                break;
            case 3:
                g = X;
                b = C;
                break;
            case 4:
                r = X;
                b = C;
                break;
            default:
                r = C;
                b = X;
                break;
        }
        return new RGBColour((int)((r + M) * 255),
                             (int)((g + M) * 255),
                             (int)((b + M) * 255));
    }
} // class HSVPalette

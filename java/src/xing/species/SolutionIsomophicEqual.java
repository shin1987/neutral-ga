package xing.species;
import xing.core.Solution;
import xing.species.core.Equal;
/**
 * Two solutions are equal if they are isomophic.
 * @author Xing Yu
 */
public class SolutionIsomophicEqual implements Equal
{
    public boolean test(final Solution x, final Solution y)
    {
        return x.isomophicTo(y);
    }

    @Override
    public int hash(final Solution x)
    {
        int N = x.getLength();
        String code = "0";
        int start = x.getStart();
        int next = (start + 1) % N;
        int last = (start + N - 1) % N;
        if (x.at(next) > x.at(last)) {
            for (int i = 1; i < N; ++i) {
                int a = x.at((start + i) % N);
                code += "," + a;
            }
        }
        else {
            for (int i = 1; i < N; ++i) {
                int a = x.at((start + N - i) % N);
                code += "," + a;
            }
        }
        return code.hashCode();
    }
} // class SolutionIsomophicEqual

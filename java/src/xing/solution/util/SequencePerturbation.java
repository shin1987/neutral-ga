package xing.solution.util;

/**
 * Collection of methods to perturb a sequence
 * @author Xing Yu
 */
public final class SequencePerturbation
{
    public static int [] swap(final int [] ans, final int i, final int j)
    {
        int temp = ans[i];
        ans[i] = ans[j];
        ans[j] = temp;
        return ans;
    }

    public static int [] reverse(final int [] ans, final int a, final int b)
    {
        int i = a, j = b;
        final int N = ans.length;
        final int M = (i < j) ? (j - i + 1) : (N - i + j + 1);
                      
        for (int k = 0; k < (M >> 1); ++k) {
            int temp = ans[i];
            ans[i] = ans[j];
            ans[j] = temp;
            i = (i + 1) % N;
            j = (j + N - 1) % N;
        }
        return ans;
    }

    public static int [] shift(final int [] ans, final int i, final int j)
    {
        final int N = ans.length;
        final int temp = ans[i];
        int ii = i;
        while (ii != j) {
            int next = (ii + 1) % N;
            ans[ii] = ans[next];
            ii = next;
        }
        ans[j] = temp;
        return ans;
    }
} // class SequencePerturbation

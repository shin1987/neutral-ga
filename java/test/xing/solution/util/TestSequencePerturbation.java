package xing.solution.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import static xing.solution.util.SequencePerturbation.*;
import java.util.ArrayList;
/**
 * @author Xing Yu
 */
public class TestSequencePerturbation
{
    private static final int N = 10;
    private int [] sequence;
    
    @BeforeEach
    void setup()
    {
        assumeTrue(N == 10);
        sequence = new int[N];
        for (int i = 0; i < N; ++i)
            sequence[i] = i;
    }

    @Test
    void testSwap()
    {
        swap(sequence, 3, 7);
        assertThat(toList(sequence), contains(0, 1, 2, 7, 4, 5, 6, 3, 8, 9));
    }

    @Test
    void shift37()
    {
        shift(sequence, 3, 7);
        assertThat(toList(sequence), contains(0, 1, 2, 4, 5, 6, 7, 3, 8, 9));
    }

    @Test
    void shift73()
    {
        shift(sequence, 7, 3);
        assertThat(toList(sequence), contains(1, 2, 3, 7, 4, 5, 6, 8, 9, 0));
    }

    @Test
    void shift36()
    {
        shift(sequence, 3, 6);
        assertThat(toList(sequence), contains(0, 1, 2, 4, 5, 6, 3, 7, 8, 9));
    }

    @Test
    void shift63()
    {
        shift(sequence, 6, 3);
        assertThat(toList(sequence), contains(1, 2, 3, 6, 4, 5, 7, 8, 9, 0));
    }

    @Test
    void reverse37()
    {
        reverse(sequence, 3, 7);
        assertThat(toList(sequence), contains(0, 1, 2, 7, 6, 5, 4, 3, 8, 9));
    }

    @Test
    void reverse73()
    {
        reverse(sequence, 7, 3);
        assertThat(toList(sequence), contains(0, 9, 8, 7, 4, 5, 6, 3, 2, 1));
    }

    @Test
    void reverse36()
    {
        reverse(sequence, 3, 6);
        assertThat(toList(sequence), contains(0, 1, 2, 6, 5, 4, 3, 7, 8, 9));
    }

    @Test
    void reverse63()
    {
        reverse(sequence, 6, 3);
        assertThat(toList(sequence), contains(9, 8, 7, 6, 4, 5, 3, 2, 1, 0));
    }

    private static ArrayList<Integer> toList(final int [] a)
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (int x : a)
            ret.add(x);
        return ret;
    }
} // class TestSequencePerturbation


package xing.species;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.CoreMatchers.*;
import xing.base.SolutionPool;

/**
 * @author Xing Yu
 */
public class TestSADSummary
{
    private SolutionPool<TestSolution> solution;
    @BeforeEach
    void setup()
    {
        solution = new SolutionPool<TestSolution>();
        solution.add(new TestSolution(new int[] {0, 1, 2, 3, 4, 5}));
        solution.add(new TestSolution(new int[] {0, 1, 2, 3, 4, 5}));
        solution.add(new TestSolution(new int[] {1, 2, 3, 4, 5, 0}));

        solution.add(new TestSolution(new int[] {0, 2, 1, 3, 4, 5}));
        solution.add(new TestSolution(new int[] {1, 3, 4, 5, 0, 2}));
        solution.add(new TestSolution(new int[] {5, 4, 3, 1, 2, 0}));

        solution.add(new TestSolution(new int[] {0, 3, 4, 2, 1, 5}));
        solution.add(new TestSolution(new int[] {0, 3, 4, 2, 1, 5}));

        solution.add(new TestSolution(new int[] {0, 3, 4, 2, 5, 1}));

        TestSolution x = new TestSolution(new int[] {5, 2, 1, 3, 4, 0});
        solution.add(x);
        solution.add(x);
    }

    @Test
    void testReferenceEqual()
    {
        SADSummary sad = new SADSummary();
        int [] sample = sad.summarise(solution);
        int [] hist = new int[3];
        java.util.Arrays.fill(hist, 0);
        for (int x : sample)
            hist[x]++;
        assertThat(hist[0], equalTo(0));
        assertThat(hist[1], equalTo(9));
        assertThat(hist[2], equalTo(1));
    }

    @Test
    void testIsomophicEqual()
    {
        SADSummary sad = new SADSummary(new SolutionIsomophicEqual());
        int [] sample = sad.summarise(solution);
        int [] hist   = new int[solution.getSize() + 1];
        java.util.Arrays.fill(hist, 0);
        for (int x : sample)
            hist[x]++;
        assertThat(hist[3], equalTo(2));
        assertThat(hist[2], equalTo(2));
        assertThat(hist[1], equalTo(1));
    }
    
} // class TestSADSummary


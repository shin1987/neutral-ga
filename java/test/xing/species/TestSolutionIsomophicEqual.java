package xing.species;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Xing Yu
 */
public class TestSolutionIsomophicEqual
{
    private final SolutionIsomophicEqual equal = new SolutionIsomophicEqual();
    private TestSolution a, b, c, d, e;

    @BeforeEach
    void setup()
    {
        a = new TestSolution(new int[]{1, 2, 3, 4, 0});
        b = new TestSolution(new int[]{0, 1, 2, 3, 4});
        c = new TestSolution(new int[]{4, 3, 2, 1, 0});
        d = new TestSolution(new int[]{1, 0, 4, 3, 2});
        e = new TestSolution(new int[]{1, 2, 4, 3, 0});
    }

    @Test
    void testEquality()
    {
        assertTrue(equal.test(a, b));
        assertTrue(equal.test(a, c));
        assertTrue(equal.test(a, d));
        assertTrue(equal.test(a, a));
        assertTrue(equal.test(b, c));
        assertTrue(equal.test(b, d));
        assertTrue(equal.test(c, d));

        assertFalse(equal.test(a, e));
        assertFalse(equal.test(b, e));
        assertFalse(equal.test(c, e));
        assertFalse(equal.test(d, e));
    }

    @Test
    void testHash()
    {
        assertThat(equal.hash(a), is(equal.hash(b)));
        assertThat(equal.hash(a), is(equal.hash(c)));
        assertThat(equal.hash(a), is(equal.hash(d)));
        assertThat(equal.hash(a), is(not(equal.hash(e))));
    }
} // class TestSolutionIsomophicEqual


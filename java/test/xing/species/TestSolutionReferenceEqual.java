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
public class TestSolutionReferenceEqual
{
    private SolutionReferenceEqual equal = new SolutionReferenceEqual();

    TestSolution a, b, c, d;

    @BeforeEach
    void setup()
    {
        a = new TestSolution(new int[]{1, 2, 3, 0});
        b = new TestSolution(new int[]{2, 3, 0, 1});
        c = new TestSolution(new int[]{0, 3, 2, 1});
        d = new TestSolution(new int[]{1, 2, 3, 0});
    } 

    @Test
    void testEquality()
    {

        assertTrue(equal.test(a, a));
        assertTrue(equal.test(b, b));
        assertTrue(equal.test(c, c));

        assertFalse(equal.test(a, b));
        assertFalse(equal.test(b, c));
        assertFalse(equal.test(c, a));
        assertFalse(equal.test(a, d));
    }

    @Test
    void testHash()
    {
        assertThat(equal.hash(a), is(equal.hash(a)));
        assertThat(equal.hash(a), is(a.hashCode()));
        assertThat(equal.hash(a), is(not(equal.hash(d))));
        assertThat(equal.hash(a), is(not(equal.hash(b))));
    }
} // class TestSolutionReferenceEqual


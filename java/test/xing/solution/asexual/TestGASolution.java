package xing.solution.asexual;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import xing.base.Problem;
import xing.core.RandomEngine;
import xing.core.Point;

import java.util.ArrayList;

/**
 * @author Xing Yu
 */
public class TestGASolution
{
    private static GASolution parent;
    
    @BeforeAll
    public static void init()
    {
        int [] x = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayList<Point> vertex = new ArrayList<Point>();
        for (int a : x)
            vertex.add(new Point(a, 0));
        GASolution.init(new Problem(vertex));
        parent = new GASolution(x);
    }

    @Test
    public void testSwap()
    {
        Pair rng = new Pair(3, 7);
        int [] exp = {0, 1, 2, 7, 4, 5, 6, 3, 8, 9};
        GASolution child = parent.swap(rng);
        for (int i = 0; i < exp.length; ++i)
            assertThat(child.at(i), is(exp[i]));
    }

    @Test
    public void testShift37()
    {
        Pair rng = new Pair(3, 7);
        int [] exp = {0, 1, 2, 4, 5, 6, 7, 3, 8, 9};
        GASolution child = parent.shift(rng);
        for (int i = 0; i < exp.length; ++i)
            assertThat(child.at(i), is(exp[i]));
    }

    @Test
    public void testShift73()
    {
        Pair rng = new Pair(7, 3);
        int [] exp = {1, 2, 3, 7, 4, 5, 6, 8, 9, 0};
        GASolution child = parent.shift(rng);
        for (int i = 0; i < exp.length; ++i)
            assertThat(child.at(i), is(exp[i]));
    }

    @Test
    public void testReverse37()
    {
        Pair rng = new Pair(3, 7);
        int [] exp = {0, 1, 2, 7, 6, 5, 4, 3, 8, 9};
        GASolution child = parent.reverse(rng);
        for (int i = 0; i < exp.length; ++i)
            assertThat(child.at(i), is(exp[i]));
    }

    @Test
    public void testReverse73()
    {
        Pair rng = new Pair(7, 3);
        int [] exp = {0, 9, 8, 7, 4, 5, 6, 3, 2, 1};
        GASolution child = parent.reverse(rng);
        for (int i = 0; i < exp.length; ++i)
            assertThat(child.at(i), is(exp[i]));
    }
} // class TestGASolution

class Pair implements RandomEngine
{
    private int x, y;

    public Pair(int a, int b)
    {
        x = a;
        y = b;
    }
    
    public void randperm(int [] a)
    {
    }

    public double nextDouble(double a, double b)
    {
        return 0;
    }

    public int nextInt(int a, int b)
    {
        int z = x;
        x = y;
        return z;
    }
}


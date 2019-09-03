package xing.base;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Xing Yu
 */
public class TestRandomEngine
{
    private RandomEngine rng;

    @BeforeEach
    private void setup()
    {
        rng = new RandomEngine(102);
    }

    @Test
    public void testRandomPerm()
    {
        int [] output = new int[10];
        rng.randperm(output);

        int sum = 0;
        for (int x : output)
            sum += x;
        assertThat(sum, is(45));

        boolean randomised = false;
        for (int i = 0; i < output.length; ++i)
            randomised |= output[i] != i;

        assertTrue(randomised);
    }
    
} // class TestRandomEngine


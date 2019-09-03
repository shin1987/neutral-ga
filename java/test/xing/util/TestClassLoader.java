package xing.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Xing Yu
 */
public class TestClassLoader
{
    @Test
    public void testLoadClass()
    {
        ClassLoader cLoader = new ClassLoader();
        String [] args = {"1", "2", "3", "4", "5"};
        HasValue product = cLoader.load(HasValue.class, Product.class, args);
        assumeTrue(product != null);
        assertThat(product.value(), is(1 * 2 * 3 * 4 * 5));
    }
} // class TestClassLoader

interface HasValue
{
    public int value();
}

class Product implements HasValue
{
    private final int v;

    public Product(final String [] args)
    {
        int prod = 1;
        for (String s : args)
            prod *= Integer.parseInt(s);
        this.v = prod;
    }
    
    @Override
    public int value()
    {
        return v;
    }
}


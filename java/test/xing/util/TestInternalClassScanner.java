package xing.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasItem;
import java.util.ArrayList;
import xing.util.core.ClassScanner;

/**
 * @author Xing Yu
 */
public class TestInternalClassScanner
{
    private ArrayList<Class<?>> result;
    @BeforeEach
    public void init()
    {
        result = new ArrayList<Class<?>>();
    }

    @Test
    public void testDefaultScanner()
    {
        InternalClassScanner scanner = new InternalClassScanner("util");
        scanner.scan(result);
        assertThat(result, hasItem(InternalClassScanner.class));
        assertThat(result, not(hasItem(ClassScanner.class)));
    }

    @Test
    public void testRecursiveScanner()
    {
        InternalClassScanner scanner = new InternalClassScanner("util", true);
        scanner.scan(result);
        assertThat(result, hasItem(InternalClassScanner.class));
        assertThat(result, hasItem(ClassScanner.class));
    }
} // class TestInternalClassScanner


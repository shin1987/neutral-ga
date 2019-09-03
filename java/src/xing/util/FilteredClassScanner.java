package xing.util;
import xing.util.core.ClassScanner;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Decorator around other class scanner, performs type check after
 * the source scanner has returned all classes.
 * @author Xing Yu
 */
public class FilteredClassScanner implements ClassScanner
{
    private final ClassScanner scanner;
    private final Class<?> target;

    /**
     * Constructor, pass the desired class type as filter.
     * @param src source scanner which returns unfiltered result.
     * @param filter target class type, only classes that can be cast into  
     * filter type will be returned after {@code scan()} method.
     */
    public FilteredClassScanner(final ClassScanner src, final Class<?> filter)
    {
        this.scanner = src;
        this.target = filter;
    }

    @Override
    public void scan(final Collection<Class<?>> result)
    {
        ArrayList<Class<?>> clazz = new ArrayList<Class<?>>();
        this.scanner.scan(clazz);

        for (Class<?> clz : clazz) {
            try {
                result.add(clz.asSubclass(target));
            }
            catch (ClassCastException e) {
                continue;
            }
        }
    }
} // class FilteredClassScanner

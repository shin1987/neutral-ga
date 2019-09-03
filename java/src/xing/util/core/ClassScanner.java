package xing.util.core;
import java.util.Collection;

/**
 * Scans available classes in some user provided location.
 * @author Xing Yu
 */
public interface ClassScanner
{
    public void scan(final Collection<Class<?>> clazz);
} // interface ClassScanner

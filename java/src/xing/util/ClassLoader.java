package xing.util;
import java.lang.reflect.Constructor;

/**
 * An implementation of xing.util.core.ClassLoader
 * @author Xing Yu
 */
public class ClassLoader implements xing.util.core.ClassLoader
{
    @Override
    public <T> T load(
        final Class<T> target, final Class<?> source, final String [] args)
    {
        try {
            Constructor<?> constructor = source.getConstructor(String[].class);
            Object obj = constructor.newInstance((Object)args);
            return target.cast(obj);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
} // class ClassLoader

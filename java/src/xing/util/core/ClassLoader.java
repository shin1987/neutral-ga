package xing.util.core;

/**
 * Instantiate classes that support constructors with string arrary 
 * as arguments. 
 * @author Xing Yu
 */
public interface ClassLoader
{
    /**
     * Construct class from given class instance.
     * Example:
     * <pre>
     * {@code
     * Class<?> source = Class.forName("xing.problem.CircularProblem");
     * ProblemGenerator cls = classLoader.load(
     *      ProblemGenerator.class, source, args);
     * }.
     * </pre>
     * @param clazz target class - usually the interface to cast into
     * @param source raw class possibly loaded by ClassScanner
     * @param args string array arguments passed to clazz's constructor
     * @param <T> target class type derived from clazz
     * @return an instance of object of T
     */
    public <T> T load(
        final Class<T> clazz, final Class<?> source, final String [] args);
} // interface ClassLoader

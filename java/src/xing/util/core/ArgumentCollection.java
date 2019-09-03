package xing.util.core;

/**
 * Utility to collect command-line style argument
 * @author Xing Yu
 */
public interface ArgumentCollection
{
    public ArgumentCollection add(final String arg, final String value);
    public String get(final String arg);
    public void capture(final String [] args);
} // interface ArgumentCollection

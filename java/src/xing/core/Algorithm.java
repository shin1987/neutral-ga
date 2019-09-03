package xing.core;

/**
 * @author Xing Yu
 */
public interface Algorithm
{
    public Solution execute(final Problem problem);
    public void kill();
} // interface Algorithm

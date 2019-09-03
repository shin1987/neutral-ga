package xing.core;

/**
 * Monitor progress.
 *
 * @author Xing Yu
 */
public interface ProgressMonitor
{
    public void monitor(final long progress, final long total);
} // interface ProgressMonitor

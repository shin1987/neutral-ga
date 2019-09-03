package xing.core;

/**
 * Sub-class must support method to maintain ProgressMonitors. 
 * @author Xing Yu
 */
public interface Progressive
{
    public void addProgressMonitor(final ProgressMonitor monitor);
} // interface Progressive

package xing.util;

/**
 * A timer implementation using System.nanoTime() for time keeping
 * Invalid tic/toc operations will result exception being thrown.
 * @author Xing Yu
 */
public class Timer implements xing.util.core.Timer
{
    private long timestamp = 0;
    private long total = 0;

    private final Tic tic = new Tic();
    private final Toc toc = new Toc();
    
    @Override
    public void tic()
    {
        timestamp = tic.tic();
        tic.disable();
        toc.enable();
    }

    @Override
    public long toc()
    {
        long ans = toc.toc(timestamp);
        total += ans;
        tic.enable();
        toc.disable();
        return ans;
    }

    @Override
    public long getTotalElapsedTime()
    {
        return total;
    }

    public long getTotalElapsedTimeMillis()
    {
        return (int)(total * 1.0 / 1E6 + 0.5);
    }

    @Override
    public Timer diff(final xing.util.core.Timer other)
    {
        Timer result = new Timer();
        result.total = this.total - other.getTotalElapsedTime();
        return result;
    }

    @Override
    public Timer accu(final xing.util.core.Timer other)
    {
        Timer result = new Timer();
        result.total = this.total + other.getTotalElapsedTime();
        return result;
    }

    @Override
    public void add(final xing.util.core.Timer other)
    {
        this.total += other.getTotalElapsedTime();
    }

    @Override
    public void sub(final xing.util.core.Timer other)
    {
        this.total -= other.getTotalElapsedTime();
    }
    
    @Override
    public String toString()
    {
        return getTotalElapsedTimeMillis() + "ms";
    }
} // class Timer

// Utility to control whether a toc is valid
final class Toc
{
    private static class Tocker
    {
        long toc(final long timestamp)
        {
            return System.nanoTime() - timestamp;
        }
    }

    private static final class InvalidTocker extends Tocker
    {
        @Override
        long toc(final long timestamp)
        {
            throw new RuntimeException("Clock has not started yet");
        }
    }

    private static final Tocker NORMAL = new Tocker();
    private static final Tocker INVALID = new InvalidTocker();
    private Tocker toc = INVALID;

    long toc(final long timestamp)
    {
        return toc.toc(timestamp);
    }

    void disable()
    {
        toc = INVALID;
    }

    void enable()
    {
        toc = NORMAL;
    }
}

// Utility to control whether a tic is valid
final class Tic
{
    private static class Ticker
    {
        long tic()
        {
            return System.nanoTime();
        }
    }

    private static final class InvalidTicker extends Ticker
    {
        @Override
        long tic()
        {
            throw new RuntimeException("Clock already started");
        }
    }

    private static final Ticker NORMAL = new Ticker();
    private static final Ticker INVALID = new InvalidTicker();

    private Ticker ticker = NORMAL;

    long tic()
    {
        return ticker.tic();
    }

    void disable()
    {
        ticker = INVALID;
    }

    void enable()
    {
        ticker = NORMAL;
    }
}

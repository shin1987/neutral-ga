package xing.base;

/**
 * Random number generator.
 * @author Xing Yu
 */
public class RandomEngine implements xing.core.RandomEngine
{
    private final java.util.Random rng;

    /**
     * Construct random engine with a seed number.
     * @param seed number to seed the engine
     */
    public RandomEngine(final long seed)
    {
        rng = new java.util.Random(seed);
    }
    
    @Override
    public double nextDouble(final double min, final double max)
    {
        double range = max - min;
        return rng.nextDouble() * range + min;
    }

    @Override
    public int nextInt(final int min, final int max)
    {
        int range = max - min + 1;
        return min + (int)Math.floor(nextDouble(0, 1) * range);
    }

    @Override
    public void randperm(final int [] array)
    {
        final int N = array.length;
        for (int i = 0; i < N; ++i)
            array[i] = i;
        for (int i = 0; i < N - 1; ++i)
        {
            int j = nextInt(i, N - 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
} // class RandomEngine

package xing.util;
import java.util.HashMap;
/**
 * @author Xing Yu
 */
public class ArgumentCollection implements xing.util.core.ArgumentCollection
{
    private final HashMap<String, String> list = new HashMap<String, String>();

    @Override
    public ArgumentCollection add(final String arg, final String value)
    {
        list.put(arg, value);
        return this;
    }

    @Override
    public String get(final String arg)
    {
        return list.get(arg);
    }

    @Override
    public void capture(final String [] args)
    {
        for (int i = 0; i < args.length - 1; i += 2)
            list.replace(args[i], args[i + 1]);
    }
} // class ArgumentCollection

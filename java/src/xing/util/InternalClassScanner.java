package xing.util;
import xing.util.core.ClassScanner;
import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Scans classes in a package under xing.* package domain.
 * @author Xing Yu
 */
public class InternalClassScanner implements ClassScanner
{
    private static final String root = getRoot().getAbsolutePath();
    private final File myPackage;
    private final boolean recursive;
    
    /**
     * Constructor, non-recursive scanner.
     * @param myPackage package name to be scaned. e.g. to scan xing.core 
     * call <code>new InternalClassScanner("core");</code>.
     */
    public InternalClassScanner(final String myPackage)
    {
        this(myPackage, false);
    }

    /**
     * Contstructor, optionally specify if recursive search is required.
     * @param myPackage package name to be scanned.
     * @param recursive if true, sub-packages will also be scanned.
     */
    public InternalClassScanner(final String myPackage, final boolean recursive)
    {
        this.myPackage = new File(root, myPackage);
        this.recursive = recursive;
    }
    
    @Override
    public void scan(final Collection<Class<?>> clazz)
    {
        scan(clazz, myPackage, recursive);
    }

    private static void scan(final Collection<Class<?>> clazz,
                             final File dir,
                             final boolean recursive)
    {
        ArrayList<File> subDir = new ArrayList<File>();
        File [] file = dir.listFiles();
        if (file == null) return;
        for (File f : file)
        {
            String filename = f.getAbsolutePath();
            if (filename.endsWith(".class")) {
                clazz.add(loadClass(toClassName(filename)));
            }
            else if (f.isDirectory())
            {
                subDir.add(f);
            }
        }
        
        if (recursive) {
            for (File f : subDir) {
                scan(clazz, f, recursive);
            }
        }
    }

    private static String toClassName(final String filename)
    {
        int begin = root.length() - 4;   // ignore path up-to before (*/)xing
        int end = filename.length() - 6; // ignore *(.class) suffix
        String truncated = filename.substring(begin, end);
        return truncated.replace('/', '.'); // convert to class format
    }

    /**
     * General method for loading class in class path using class name.
     * Wrapper of Class.forName so that any exception is thrown as 
     * RuntimeException.
     * @param clsName class name as a string.
     * @return corresponding class object
     */
    public static Class<?> loadClass(final String clsName)
    {
        try {
            Class<?> cls = Class.forName(clsName);
            return cls;
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Class cannot be found: " + e);
        }
    }
    
    private static File getRoot()
    {
        File ret = null;
        try {
            URI rootURI = InternalClassScanner.class
                          .getResource("InternalClassScanner.class").toURI();
            ret = new File(rootURI).getParentFile().getParentFile();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to get package root from URI");
        }
        return ret;
    }
} // class InternalClassScanner

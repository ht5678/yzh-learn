package classloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ServiceConfigurationError;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年7月13日 下午4:07:22  
 *
 */
public class TestClassloaderGetResource {

	
    private static LinkedHashMap<String,?> providers = new LinkedHashMap<>();
	
	
	public static void main(String[] args) throws Exception{
		Enumeration<URL> configs = ClassLoader.getSystemResources("META-INF/spring.factories");
		
		

        Iterator<String> pending = null;
        
        while ((pending == null) || !pending.hasNext()) {
            if (!configs.hasMoreElements()) {
//                return false;
            	System.out.println("false");
            }
            pending = parse(TestClassloaderGetResource.class, configs.nextElement());
        }
        String  nextName = pending.next();
        System.out.println(nextName);
        
        
        
        
        //method2
        
        System.out.println("method2");
		InputStream input = TestClassloaderGetResource.class.getClassLoader().getResourceAsStream("META-INF/spring.factories");
		byte[] bytes = new byte[input.available()];
		input.read(bytes);
		System.out.println(new String(bytes));
		input.close();
        
	}
	
	
	
	
    private static Iterator<String> parse(Class<?> service, URL u)
            throws ServiceConfigurationError
        {
            InputStream in = null;
            BufferedReader r = null;
            ArrayList<String> names = new ArrayList<>();
            try {
                in = u.openStream();
                r = new BufferedReader(new InputStreamReader(in, "utf-8"));
                int lc = 1;
                while ((lc = parseLine(service, u, r, lc, names)) >= 0);
            } catch (IOException x) {
            	System.out.println("Error reading configuration file");
//                fail(service, "Error reading configuration file", x);
            } finally {
                try {
                    if (r != null) r.close();
                    if (in != null) in.close();
                } catch (IOException y) {
                	System.out.println("Error closing configuration file ");
//                    fail(service, "Error closing configuration file", y);
                }
            }
            return names.iterator();
        }
	
	
    
    // Parse a single line from the given configuration file, adding the name
    // on the line to the names list.
    //
    private static int parseLine(Class<?> service, URL u, BufferedReader r, int lc,
                          List<String> names)
        throws IOException, ServiceConfigurationError
    {
        String ln = r.readLine();
        if (ln == null) {
            return -1;
        }
        int ci = ln.indexOf('#');
        if (ci >= 0) ln = ln.substring(0, ci);
        ln = ln.trim();
        int n = ln.length();
        if (n != 0) {
            if ((ln.indexOf(' ') >= 0) || (ln.indexOf('\t') >= 0))
            	System.out.println("Illegal configuration-file syntax");
//                fail(service, u, lc, "Illegal configuration-file syntax");
            int cp = ln.codePointAt(0);
            if (!Character.isJavaIdentifierStart(cp))
            	System.out.println("Illegal provider-class name: " + ln);
//                fail(service, u, lc, "Illegal provider-class name: " + ln);
            for (int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
                cp = ln.codePointAt(i);
                if (!Character.isJavaIdentifierPart(cp) && (cp != '.'))
                	System.out.println("Illegal provider-class name: " + ln);
//                    fail(service, u, lc, "Illegal provider-class name: " + ln);
            }
            if (!providers.containsKey(ln) && !names.contains(ln))
                names.add(ln);
        }
        return lc + 1;
    }
	
}

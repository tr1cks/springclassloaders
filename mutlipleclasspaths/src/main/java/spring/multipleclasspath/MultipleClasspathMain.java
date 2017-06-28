package spring.multipleclasspath;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.core.Context;
import spring.core.impl.SomeServicesManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import static java.util.Arrays.asList;

public class MultipleClasspathMain {

    public static void main(String[] args) throws MalformedURLException {
        List<Class> contexts = new ArrayList<>();
        contexts.addAll(findContexts(MultipleClasspathMain.class.getClassLoader()));
        contexts.addAll(findContexts(getPluginClassLoader("plugina\\target\\plugina-1.0-SNAPSHOT.jar")));
        contexts.addAll(findContexts(getPluginClassLoader("pluginb\\target\\pluginb-1.0-SNAPSHOT.jar")));

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(contexts.toArray(new Class[contexts.size()]));
        SomeServicesManager servicesManager = context.getBean(SomeServicesManager.class);

        Set<String> expected = new HashSet<>(asList("CoreSomeService", "PluginASomeService", "PluginBSomeService"));
        if(!expected.equals(servicesManager.getServicesNames())) {
            throw new IllegalStateException(servicesManager.getServicesNames().toString());
        }
    }

    private static List<Class> findContexts(ClassLoader classLoader) {
        ServiceLoader<Context> serviceLoader = ServiceLoader.load(Context.class, classLoader);

        List<Class> result = new ArrayList<>();
        for(Context context : serviceLoader) {
            result.add(context.getClass());
        }

        return result;
    }

    private static ClassLoader getPluginClassLoader(String path) throws MalformedURLException {
        return new URLClassLoader(new URL[]{ new File(path).toURI().normalize().toURL() });
    }
}

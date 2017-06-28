package spring.oneclasspath;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.core.Context;
import spring.core.impl.SomeServicesManager;

import java.util.*;

import static java.util.Arrays.asList;

public class OneClasspathMain {

    public static void main(String[] args) throws BeansException {
        ServiceLoader<Context> loader = ServiceLoader.load(Context.class);

        List<Class> contexts = new ArrayList<>();
        loader.forEach(context -> contexts.add(context.getClass()));

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(contexts.toArray(new Class[contexts.size()]));
        SomeServicesManager servicesManager = context.getBean(SomeServicesManager.class);

        Set<String> expected = new HashSet<>(asList("CoreSomeService", "PluginASomeService", "PluginBSomeService"));
        if(!expected.equals(servicesManager.getServicesNames())) {
            throw new IllegalStateException();
        }
    }
}

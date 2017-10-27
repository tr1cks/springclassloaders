package spring.workaround;

import org.springframework.aop.framework.autoproxy.MulticlassloadersAnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.MulticlassloadersConfigurationClassPostProcessor;
import spring.core.Context;
import spring.core.impl.SomeServicesManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import static java.util.Arrays.asList;
import static org.springframework.aop.config.AopConfigUtils.AUTO_PROXY_CREATOR_BEAN_NAME;
import static org.springframework.context.annotation.AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME;

public class WorkaroundMain {

    public static void main(String[] args) throws MalformedURLException {
        List<Class> contexts = new ArrayList<>();
        contexts.addAll(findContexts(WorkaroundMain.class.getClassLoader()));
        contexts.addAll(findContexts(getPluginClassLoader("plugina\\target\\plugina-1.0-SNAPSHOT.jar")));
        contexts.addAll(findContexts(getPluginClassLoader("pluginb\\target\\pluginb-1.0-SNAPSHOT.jar")));
        contexts.addAll(findContexts(getPluginClassLoader("pluginc\\target\\pluginc-1.0-SNAPSHOT.jar")));
        contexts.addAll(findContexts(getPluginClassLoader("plugind\\target\\plugind-1.0-SNAPSHOT.jar")));

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        registerClassloadingWorkarounds(beanFactory);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(beanFactory);
        context.register(contexts.toArray(new Class[contexts.size()]));
        context.refresh();

        SomeServicesManager servicesManager = context.getBean(SomeServicesManager.class);

        Set<String> expected = new HashSet<>(asList("MainService#CoreSomeService", "MainService#PluginASomeService",
                                                    "MainService#PluginBSomeService", "MainService#PluginCSomeService",
                                                    "MainService#PluginDSomeService", "MainService#PluginDSubSomeService",
                                                    "MainService#PluginDSubSubSomeService"));
        if(!expected.equals(servicesManager.getServicesNames())) {
            throw new IllegalStateException(servicesManager.getServicesNames().toString());
        }
    }

    private static void registerClassloadingWorkarounds(DefaultListableBeanFactory beanFactory) {
        // Workaround for loading spring contexts in own classloaders
        beanFactory.registerBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME,
                                           new RootBeanDefinition(MulticlassloadersConfigurationClassPostProcessor.class));
        // Workaround for creating cglib proxy with origin bean classLoader
        beanFactory.registerBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME,
                                           new RootBeanDefinition(MulticlassloadersAnnotationAwareAspectJAutoProxyCreator.class));
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

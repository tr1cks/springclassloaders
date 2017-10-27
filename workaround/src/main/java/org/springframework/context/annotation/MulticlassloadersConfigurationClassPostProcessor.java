package org.springframework.context.annotation;

import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassEnhancer;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.ConfigurationClassUtils;
import org.springframework.util.ClassUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.util.ClassUtils.getDefaultClassLoader;

// Must be loaded with classloader of ConfigurationClassUtils.class. In other case you can't get access, because of package-private visibility
public class MulticlassloadersConfigurationClassPostProcessor extends ConfigurationClassPostProcessor {

    public void enhanceConfigurationClasses(ConfigurableListableBeanFactory beanFactory) {
        Map<String, AbstractBeanDefinition> configBeanDefs = new LinkedHashMap<>();
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDef = beanFactory.getBeanDefinition(beanName);
            if (ConfigurationClassUtils.isFullConfigurationClass(beanDef)) {
                if (!(beanDef instanceof AbstractBeanDefinition)) {
                    throw new BeanDefinitionStoreException("Cannot enhance @Configuration bean definition '" +
                            beanName + "' since it is not stored in an AbstractBeanDefinition subclass");
                }
/*
                else if (logger.isWarnEnabled() && beanFactory.containsSingleton(beanName)) {
                    logger.warn("Cannot enhance @Configuration bean definition '" + beanName +
                            "' since its singleton instance has been created too early. The typical cause " +
                            "is a non-static @Bean method with a BeanDefinitionRegistryPostProcessor " +
                            "return type: Consider declaring such methods as 'static'.");
                }
*/
                configBeanDefs.put(beanName, (AbstractBeanDefinition) beanDef);
            }
        }
        if (configBeanDefs.isEmpty()) {
            // nothing to enhance -> return immediately
            return;
        }
        ConfigurationClassEnhancer enhancer = new ConfigurationClassEnhancer();
        for (Map.Entry<String, AbstractBeanDefinition> entry : configBeanDefs.entrySet()) {
            AbstractBeanDefinition beanDef = entry.getValue();
            // If a @Configuration class gets proxied, always proxy the target class
            beanDef.setAttribute(AutoProxyUtils.PRESERVE_TARGET_CLASS_ATTRIBUTE, Boolean.TRUE);
            try {
                ClassLoader classLoader = getBeanClassloader(beanDef);

                // Set enhanced subclass of the user-specified bean class
                Class<?> configClass = beanDef.resolveBeanClass(classLoader);
                Class<?> enhancedClass = enhancer.enhance(configClass, classLoader);
                if (configClass != enhancedClass) {
/*
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("Replacing bean definition '%s' existing class '%s' with " +
                                "enhanced class '%s'", entry.getKey(), configClass.getName(), enhancedClass.getName()));
                    }
*/
                    beanDef.setBeanClass(enhancedClass);
                }
            }
            catch (Throwable ex) {
                throw new IllegalStateException("Cannot load configuration class: " + beanDef.getBeanClassName(), ex);
            }
        }
    }

    private ClassLoader getBeanClassloader(AbstractBeanDefinition beanDef) {
        Class beanClass = getBeanClass(beanDef);
        return beanClass != null ? beanClass.getClassLoader() : getDefaultClassLoader();
    }

    private Class getBeanClass(AbstractBeanDefinition beanDef) {
        try {
            return beanDef.getBeanClass();
        } catch(IllegalStateException exc) {
            if(exc.getMessage().endsWith("has not been resolved into an actual Class")) {
                return null;
            }

            throw exc;
        }
    }
}
package org.springframework.aop.framework.autoproxy;

import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Create proxy of class, thar respect classloader of source class.
 */
public class MulticlassloadersAnnotationAwareAspectJAutoProxyCreator extends AnnotationAwareAspectJAutoProxyCreator {

    static {
        // Without it AopConfigUtils.findPriorityForClass will throw exception for this workaround
        try {
            Field apcPriorityListField = AopConfigUtils.class.getDeclaredField("APC_PRIORITY_LIST");
            apcPriorityListField.setAccessible(true);
            List<Class<?>> apcPriorityList = (List<Class<?>>) apcPriorityListField.get(null);
            apcPriorityList.add(MulticlassloadersAnnotationAwareAspectJAutoProxyCreator.class);
        } catch(Exception exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override protected Object createProxy(Class<?> beanClass, String beanName, Object[] specificInterceptors, TargetSource targetSource) {
        if (getBeanFactory() instanceof ConfigurableListableBeanFactory) {
            AutoProxyUtils.exposeTargetClass((ConfigurableListableBeanFactory) getBeanFactory(), beanName, beanClass);
        }

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.copyFrom(this);

        if (!proxyFactory.isProxyTargetClass()) {
            if (shouldProxyTargetClass(beanClass, beanName)) {
                proxyFactory.setProxyTargetClass(true);
            }
            else {
                evaluateProxyInterfaces(beanClass, proxyFactory);
            }
        }

        Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
        for (Advisor advisor : advisors) {
            proxyFactory.addAdvisor(advisor);
        }

        proxyFactory.setTargetSource(targetSource);
        customizeProxyFactory(proxyFactory);

        proxyFactory.setFrozen(isFrozen());
        if (advisorsPreFiltered()) {
            proxyFactory.setPreFiltered(true);
        }

        // Difference from origin
        return proxyFactory.getProxy(beanClass.getClassLoader());
    }
}

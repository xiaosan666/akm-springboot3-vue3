package com.akm.springboot3.core.utils;

import jakarta.annotation.Nullable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring加载的时候加载的ApplicationContextAware，方便在pojo类里面获得context或者bean。
 *
 * @author bernix
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext appContext;

    public static synchronized ApplicationContext getApplicationContext() {
        return appContext;
    }

    @Override
    public synchronized void setApplicationContext(@Nullable ApplicationContext applicationContext) {
        SpringContextHolder.appContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) appContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return appContext.getBean(requiredType);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return appContext.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return appContext.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return appContext.isSingleton(name);
    }

    public static Class<?> getType(String name) {
        return appContext.getType(name);
    }

    public static String[] getAliases(String name) {
        return appContext.getAliases(name);
    }
}

package com.sp.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author chenqi
 * @date 2022/3/22
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext =applicationContext;
    }

    /**
     * 通过类型获取Bean
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T  getBean(Class<T> requiredType){
        T bean = (T)applicationContext.getBean(requiredType);
        return bean;
    }

    /**
     * 通过名字获取bean
     * @param name
     * @return
     */
    public static <T> T getBean(String name){
        return (T)applicationContext.getBean(name);
    }
}

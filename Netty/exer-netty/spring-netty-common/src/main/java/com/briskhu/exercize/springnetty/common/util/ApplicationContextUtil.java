package com.briskhu.exercize.springnetty.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2019-12-09
 **/
public class ApplicationContextUtil implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextUtil.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    private static ApplicationContext applicationContext;

    /* ---------------------------------------- methods ---------------------------------------- */

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName, Class<T> beanClass){
        return ApplicationContextUtil.applicationContext.getBean(beanName, beanClass);
    }



}



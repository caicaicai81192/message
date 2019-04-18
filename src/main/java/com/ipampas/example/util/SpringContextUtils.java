package com.ipampas.example.util;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author caizj
 * @version V1.0
 * @date 2019/3/21 5:56 PM
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext arg) throws BeansException {
        applicationContext = arg;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return Optional.ofNullable(applicationContext)
                .map(applicationContext -> applicationContext.getBean(clazz)).orElseGet(null);
    }

}

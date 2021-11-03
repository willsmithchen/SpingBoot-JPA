package com.clj.demo.configuration;

import org.mapstruct.TargetType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/11/3
 * @description mapstruct 使用该factory  用于获取Spring容器管理的Bean
 * @date 2021/11/3
 **/
@Component
public class EntityObjFactory implements ApplicationContextAware {
    private ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        if (null == this.applicationContext) {
            this.applicationContext = context;
        }

    }

    /**
     * 获取Spring容器管理的Bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T createEntity(@TargetType Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
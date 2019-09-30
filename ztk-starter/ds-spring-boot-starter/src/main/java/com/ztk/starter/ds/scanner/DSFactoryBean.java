package com.ztk.starter.ds.scanner;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午2:05
 */
public class DSFactoryBean<T> implements FactoryBean<T> {

    private Class<T> target;

    public DSFactoryBean(Class<T> target) {
        this.target = target;
    }

    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(target.getClassLoader(),
                new Class[]{target},
                new DSAnnotationHandler(target));
    }

    @Override
    public Class<?> getObjectType() {
        return target;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
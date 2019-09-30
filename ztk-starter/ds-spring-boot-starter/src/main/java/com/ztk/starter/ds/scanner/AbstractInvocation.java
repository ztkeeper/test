package com.ztk.starter.ds.scanner;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午2:06
 */
public abstract class AbstractInvocation<T> implements InvocationHandler {

    protected Class<T> target;
    protected T proxy;

    protected AbstractInvocation(Class target) {
        this.createProxy(target);
    }

    private T createProxy(Class<T> target) {
        this.target = target;
        this.proxy = (T) Proxy.newProxyInstance(target.getClassLoader(), new Class[]{target}, this);
        return proxy;
    }

}
package com.ztk.starter.ds.scanner;


import com.ztk.starter.ds.annotation.service.DSH;
import com.ztk.starter.ds.storage.handler.DSHandler;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午2:18
 */
public class DSMapping {

    private Class type;

    private Map<Method, DSHandler> dsHandlerMap;

    public DSMapping(Class type, Map<Method, DSHandler> dsHandlerMap) {
        if (!type.isInterface()) {
            throw new RuntimeException("类不是interface   替换掉这个异常");
        }
        this.type = type;
        this.dsHandlerMap = dsHandlerMap == null ? new ConcurrentHashMap<>() : dsHandlerMap;
    }

    public Class getType() {
        return type;
    }

    public DSHandler getHandler(Method method) {
        return dsHandlerMap.get(method);
    }


    public Map<Method, DSHandler> getDsHandlerMap() {
        return dsHandlerMap;
    }

    public boolean checkMethod(Method method) {
        return method.getDeclaredAnnotation(DSH.class) != null;
    }

}
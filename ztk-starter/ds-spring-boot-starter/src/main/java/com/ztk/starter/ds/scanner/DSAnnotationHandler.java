package com.ztk.starter.ds.scanner;

import com.ztk.starter.ds.DSContext;
import com.ztk.starter.ds.model.DSResponse;
import com.ztk.starter.ds.storage.handler.DSHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Slf4j
public class DSAnnotationHandler<T> extends AbstractInvocation<T> {

    public DSAnnotationHandler(Class<T> target) {
        super(target);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        DSMappingHandler dsMappingHandler = DSContext.getContext().getBean(DSMappingHandler.class);
        DSMapping dsMapping = dsMappingHandler.dsMapping(target);
        if (!dsMapping.checkMethod(method)) {
            log.info("method {} is not a abstract method, execute the method with default content!", method.getName());
            Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                    .getDeclaredConstructor(target.getClass());
            constructor.setAccessible(true);
            return constructor.newInstance(target)
                    .unreflectSpecial(method, target)
                    .bindTo(proxy)
                    .invokeWithArguments(args);
        } else {

            // todo
            // 1、请求数据

            // 2、解析数据

            // 3、转换数据

            // 4、用户 handler
            DSHandler handler = dsMapping.getHandler(method);
            // todo handler 使用 动态代理处理
            handler.handle();
            return new DSResponse().setMessage("测试执行完成");
        }
    }
}
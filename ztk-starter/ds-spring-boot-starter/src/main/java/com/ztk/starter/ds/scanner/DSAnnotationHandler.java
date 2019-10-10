package com.ztk.starter.ds.scanner;

import com.ztk.starter.ds.DSContext;
import com.ztk.starter.ds.annotation.service.DSH;
import com.ztk.starter.ds.http.HttpRequestActuator;
import com.ztk.starter.ds.model.DSRequest;
import com.ztk.starter.ds.model.DSResponse;
import com.ztk.starter.ds.resolver.Resolver;
import com.ztk.starter.ds.resolver.ResolverFactory;
import com.ztk.starter.ds.storage.handler.DSHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

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
            return defaultMethod(method, args);
        } else {
            // 请求方法参数 DSRequest
            DSRequest request = checkDSHMethodParam(args);
            // DSH 方法注解，标注数据回调处理 handler 和 返回结果实体
            DSH dsh = method.getAnnotation(DSH.class);
            // 请求类型 POST、GET、PUT ...
            HttpMethod httpMethod = dsh.method();
            // 1、发起 http 请求，获取返回结果
            String result = HttpRequestActuator.request(httpMethod, request);

            // 2、解析数据
            final Class<?> entityClazz = dsh.entity();
            Resolver resolver = DSContext.getContext().getBean(ResolverFactory.class).getResolver(dsh.dataType());
            Object entity = resolver.resolve(result, dsh.entityType(), entityClazz);

            // 3、用户 handler
            DSHandler handler = dsMapping.getHandler(method);
            boolean async = false;
            if (async) {
                handler.handle(entity);
                return new DSResponse().setCode(0).setMessage("异步测试执行完成");
            } else {
                return handler.handle(entity);
            }


        }
    }

    /**
     * if the method in interface is default
     *
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    private Object defaultMethod(Method method, Object[] args) throws Throwable {
        Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                .getDeclaredConstructor(target.getClass());
        constructor.setAccessible(true);
        return constructor.newInstance(target)
                .unreflectSpecial(method, target)
                .bindTo(proxy)
                .invokeWithArguments(args);
    }

    /**
     * check param of method
     *
     * @param args
     * @return
     */
    private DSRequest checkDSHMethodParam(Object[] args) {
        if (args == null || args.length == 0) {
            throw new RuntimeException("参数错误，为了相对容易控制，请求参数未使用注解，使用参数方式传递");
        }
        Object param = args[0];
        if (!param.getClass().isAssignableFrom(DSRequest.class)) {
            throw new RuntimeException("参数错误，参数为 DSRequest");
        }
        return (DSRequest) param;
    }
}
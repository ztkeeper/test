package com.ztk.starter.ds;

import com.ztk.starter.ds.scanner.DSAnnotationHandler;
import com.ztk.starter.ds.scanner.DSMappingHandler;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内部上下文
 *
 * @author sunyue
 * @date 2019/9/30 下午2:10
 */
public class DSContext {

    private transient static ApplicationContext applicationContext;

    private transient static DSContext dsContext;

    private transient final Map<Class, Object> context = new ConcurrentHashMap<>();

    private DSContext() {
        dsContext = this;
    }

    protected DSContext registerDSMappingHandler(DSMappingHandler bean) {
        context.put(bean.getClass(), bean);
        return this;
    }

    public <T> T getBean(Class<T> type) {
        return (T) context.get(type);
    }

    /**
     * 获取 rocket 上下文
     *
     * @return
     */
    public static DSContext getContext() {
        return dsContext;
    }

    protected void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    /**
     * 获取系统上下文
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
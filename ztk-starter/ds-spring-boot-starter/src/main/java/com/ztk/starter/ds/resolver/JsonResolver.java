package com.ztk.starter.ds.resolver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztk.starter.ds.enums.DataType.EntityType;

/**
 * json string 转换 为 json Object / json Array
 *
 * @author sunyue
 * @date 2019/10/10 上午9:57
 */
public class JsonResolver<T> extends ResolverHandler implements Resolver<String, EntityType, T> {

    private final ThreadLocal<EntityType> entityTypeLocal = new ThreadLocal<>();
    private final ThreadLocal<Class> typeLocal = new ThreadLocal<>();

    @Override
    protected <E, T1> T1 handler(E source) {
        String sourceData = (String) source;
        EntityType type = entityTypeLocal.get();
        if (EntityType.OBJECT.equals(type)) {
            return (T1) JSONObject.parseObject(sourceData, typeLocal.get());
        }
        if (EntityType.ARRAY.equals(type)) {
            return (T1) JSONArray.parseArray(sourceData, typeLocal.get());
        }
        return null;
    }

    @Override
    public T resolve(String source, EntityType entityType, Class type) {
        entityTypeLocal.set(entityType);
        typeLocal.set(type);
        try {
            return handler(source);
        } finally {
            typeLocal.remove();
            entityTypeLocal.remove();
        }
    }
}

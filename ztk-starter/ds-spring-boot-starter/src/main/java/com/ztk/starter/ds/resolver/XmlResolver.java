package com.ztk.starter.ds.resolver;

import com.ztk.starter.ds.enums.DataType;

/**
 * xml string 转换为 json Object / json Array
 *
 * @author sunyue
 * @date 2019/10/10 上午9:54
 */
public class XmlResolver<T> extends ResolverHandler implements Resolver<String, DataType.EntityType, T> {

    @Override
    protected <E, T> T handler(E source) {
        return null;
    }

    @Override
    public T resolve(String source, DataType.EntityType entityType, Class type) {
        return handler(source);
    }
}

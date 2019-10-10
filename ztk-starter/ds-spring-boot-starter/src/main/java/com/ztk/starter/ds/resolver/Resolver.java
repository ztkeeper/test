package com.ztk.starter.ds.resolver;

import com.ztk.starter.ds.enums.DataType.EntityType;

/**
 * 解析器
 *
 * @author sunyue
 * @date 2019/10/10 上午9:52
 */
public interface Resolver<T1, T2 extends EntityType, T3> {

    /**
     * @param source      http 请求返回的数据
     * @param entityType  实体解析类型 object/array
     * @param targetClazz 实体类型
     * @return
     */
    T3 resolve(T1 source, T2 entityType, Class targetClazz);
}

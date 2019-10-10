package com.ztk.starter.ds.resolver;

import com.alibaba.fastjson.JSONObject;
import com.ztk.starter.ds.enums.DataType;

import java.util.HashMap;
import java.util.Map;

/**
 * 解析器工厂类
 *
 * @author sunyue
 * @date 2019/10/10 上午10:05
 */
public class ResolverFactory {

    private static final Map<DataType, Resolver> resolverMap = new HashMap<>();
    private static final Builder builder = new ResolverFactory.Builder();

    private ResolverFactory() {
    }

    public static ResolverFactory.Builder builder() {
        return builder;
    }

    public Resolver getResolver(DataType dataType) {
        return ResolverFactory.resolverMap.get(dataType);
    }

    public final static class Builder {

        public Builder bindResolver(DataType dataType, Resolver resolver) {
            resolverMap.put(dataType, resolver);
            return this;
        }

        public ResolverFactory build() {
            return new ResolverFactory();
        }
    }

}

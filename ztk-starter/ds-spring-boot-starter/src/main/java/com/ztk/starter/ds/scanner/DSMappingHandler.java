package com.ztk.starter.ds.scanner;

import java.util.Map;

public final class DSMappingHandler {

    private final transient Map<Class, DSMapping> dsMappingCache;

    public DSMappingHandler(Map<Class, DSMapping> dsMappingCache) {
        this.dsMappingCache = dsMappingCache;
    }

    public DSMapping dsMapping(Class clazz) {
        return dsMappingCache.get(clazz);
    }

}
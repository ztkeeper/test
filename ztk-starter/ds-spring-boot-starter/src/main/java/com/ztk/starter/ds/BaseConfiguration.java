package com.ztk.starter.ds;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午2:43
 */
public interface BaseConfiguration {

    String DS_PACKAGE = "packages";
    Map<String, Object> attributes = new ConcurrentHashMap<>();

    default BaseConfiguration setAttributes(String key, Object value) {
        attributes.put(key, value);
        return this;
    }

    default Object getAttributes(String key) {
        return attributes.get(key);
    }

}
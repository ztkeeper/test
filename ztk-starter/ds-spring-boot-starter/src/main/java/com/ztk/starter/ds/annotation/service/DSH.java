package com.ztk.starter.ds.annotation.service;

import com.ztk.starter.ds.storage.Entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据处理
 *
 * @author sunyue
 * @date 2019/9/30 下午12:59
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DSH {

    /**
     * 返回信息实体
     *
     * @return
     */
    Class<? extends Entity> entity();

    /**
     * 处理器
     *
     * @return
     */
    Class<? extends com.ztk.starter.ds.storage.handler.DSHandler> handler();
}

package com.ztk.starter.ds.storage.handler;

/**
 * 存储处理器
 *
 * @author sunyue
 * @date 2019/9/30 上午11:52
 */
public interface DSHandler<T, R> {

    R handle(T response);
}

package com.ztk.starter.ds.resolver;

/**
 * 转换处理器
 *
 * @author sunyue
 * @date 2019/10/10 上午9:58
 */
public abstract class ResolverHandler {

    protected abstract <E, T> T handler(E source);

}

package com.ztk.starter.ds.annotation.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 引用类型属性
 *
 * @author sunyue
 * @date 2019/9/30 下午12:31
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DFInner {
}

package com.ztk.starter.ds.annotation.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对应的三方属性信息
 *
 * @author sunyue
 * @date 2019/9/30 上午10:48
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DF {

    /**
     * 第三方属性名
     *
     * @return
     */
    String value();

}

/*
 * value: 属性名
 * type: 数据格式
 */
package com.ztk.starter.ds.annotation.data;

import com.ztk.starter.ds.enums.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据转换
 *
 * @author sunyue
 * @date 2019/9/30 上午10:43
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DC {

    /**
     * 对应的三方数据属性名， 默认 ""，（处理时，"" 被当前类名代替）
     *
     * @return
     */
    String value() default "";

    /**
     * 三方数据类型，默认 json
     *
     * @return
     */
    DataType type() default DataType.JSON;

}

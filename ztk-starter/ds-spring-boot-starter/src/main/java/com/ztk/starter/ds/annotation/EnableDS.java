package com.ztk.starter.ds.annotation;

import com.ztk.starter.ds.DSScanConfiguration;
import com.ztk.starter.ds.SystemInit;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入口
 *
 * @author sunyue
 * @date 2019/9/30 下午1:20
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({SystemInit.class, DSScanConfiguration.class})
public @interface EnableDS {

    String[] packages() default {""};
}

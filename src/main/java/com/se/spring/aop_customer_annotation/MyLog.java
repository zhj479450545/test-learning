package com.se.spring.aop_customer_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 拦截Controller
 * Created by Administrator on 2017/2/23.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLog {
    /*要执行的操作类型*/
    String operationType() default "";
    /*要执行的具体操作*/
    String operationName() default "";
}

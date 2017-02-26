package com.se.spring.aop_customer_annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 自定义切面类
 * Created by Administrator on 2017/2/23.
 */
@Aspect
@Component
public class MyLogAspect {

    //      controller层切入点
//    @Pointcut("@annotation(com.se.spring.aop_customer_annotation.MyLog)")
    @Pointcut("execution(* com.se.spring.aop_customer_annotation.MyController.*(..))")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     * @param joinPoint
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("controller层前置通知...");
    }

    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        System.out.println("controller层异常通知...");
    }
}

package com.se.spring.aop_customer_annotation;

/**
 * controller层
 * Created by Administrator on 2017/2/26.
 */
public class MyController {

    @MyLog(operationType = "执行操作类型：新增", operationName = "添加方法")
    public void add() {
        System.out.println("执行MyController中的add方法");
    }
}

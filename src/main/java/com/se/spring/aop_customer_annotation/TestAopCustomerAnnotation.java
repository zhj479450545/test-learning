package com.se.spring.aop_customer_annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/2/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:aop/aop_customer_annotation.xml"})
public class TestAopCustomerAnnotation {

    @Resource
    MyController myController;
    @Test
    public void textController() {
//        MyController myController = new MyController();
        myController.add();
    }
}

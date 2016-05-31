package com.se.spring.aop_helloworld.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.se.spring.aop_helloworld.service.IHelloWorldService;

public class AopTest {
	@Test
	public void testHelloworld(){
		ApplicationContext context = new ClassPathXmlApplicationContext("aop/aop.xml");
		IHelloWorldService helloWorldService = context.getBean("helloWorldService", IHelloWorldService.class);
		helloWorldService.sayHello();
	}
}


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
	
	@Test
	public void testSchemaBeforeAdvice(){
		ApplicationContext context = new ClassPathXmlApplicationContext("aop/aop_schema.xml");
		IHelloWorldService helloWorldService = context.getBean("helloWorldService", IHelloWorldService.class);
		helloWorldService.sayBefore("hello");
	}
	
	@Test
	public void testSchemaAfterReturningAdvice(){
		ApplicationContext context = new ClassPathXmlApplicationContext("aop/aop_schema.xml");
		IHelloWorldService helloWorldService = context.getBean("helloWorldService", IHelloWorldService.class);
		helloWorldService.sayAfterReturning();
	}
	
	@Test
	public void testSchemaAfterThrowingAdvice(){
		ApplicationContext context = new ClassPathXmlApplicationContext("aop/aop_schema.xml");
		IHelloWorldService helloWorldService = context.getBean("helloWorldService", IHelloWorldService.class);
		helloWorldService.sayAfterThrowing();
		
	}
}


package com.se.spring.aop_helloworld.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.se.spring.aop_helloworld.service.IHelloWorldService;
import com.se.spring.aop_helloworld.service.IIntroductionService;

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
	
	@Test
	public void testSchemaAroundAdvice(){
		ApplicationContext context = new ClassPathXmlApplicationContext("aop/aop_schema.xml");
		IHelloWorldService helloWorldService = context.getBean("helloWorldService", IHelloWorldService.class);
		helloWorldService.sayAround("hehe");
	}
	
	@Test
	public void testSchemaIntroduction(){
		ApplicationContext context = new ClassPathXmlApplicationContext("aop/aop_schema.xml");
		IIntroductionService introductionService = context.getBean("helloWorldService", IIntroductionService.class);
		introductionService.induct();
	}
	
	@Test
	public void testSchemaAdvisor(){
		ApplicationContext context = new ClassPathXmlApplicationContext("aop/aop_schema.xml");
		IHelloWorldService helloWorldService = context.getBean("helloWorldService", IHelloWorldService.class);
		helloWorldService.sayAdvisorBefore("hehe");
	}
	
	@Test
	public void testAnnotationBeforeAdvice(){
		ApplicationContext context = new ClassPathXmlApplicationContext("aop/aop_basedAnnotation.xml");
		IHelloWorldService helloWorldService = context.getBean("helloWorldService", IHelloWorldService.class);
		helloWorldService.sayAdvisorBefore("hehe");
	}
}


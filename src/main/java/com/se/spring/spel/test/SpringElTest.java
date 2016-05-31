package com.se.spring.spel.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.se.spring.spel.bean.SpELBean;

public class SpringElTest {
	
	@Test
	public void testAnnotationExpression(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spel/spel_1.xml");
		SpELBean helloBean = context.getBean("helloBean1", SpELBean.class);
		System.out.println(helloBean);
		
		SpELBean helloBean2 = context.getBean("helloBean2", SpELBean.class);
		System.out.println(helloBean2);
		
		String str = context.getBean("world", String.class);
		System.out.println(str);
	}
}


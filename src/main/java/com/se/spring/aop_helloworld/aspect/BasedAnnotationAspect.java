package com.se.spring.aop_helloworld.aspect;

import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

public class BasedAnnotationAspect {
	
	@Pointcut(value="execution(* com.se.spring.aop_helloworld.service.*.sayBefore(..)) && args(param)", argNames="param")
	public void beforePointcut(){
		System.out.println();
	}
	
	@Before(value="beforePointcut(param)", argNames="param")
	public void beforeAdvice(String param){
		System.out.println("***** beforeAdvice" + param + " *****");
	}
}


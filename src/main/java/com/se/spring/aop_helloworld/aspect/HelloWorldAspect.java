package com.se.spring.aop_helloworld.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 
 * @项目名称：test-learning
 * @包名：com.se.spring.aop.aspect
 * @类名称：HelloWorldAspect.java
 * @类描述：切面支持类
 * @创建人：zhoujian
 * @创建时间：2016年5月31日
 * @修改人：zhoujian
 * @修改时间：2016年5月31日
 * @修改备注：
 * @version
 */
public class HelloWorldAspect {
	
	public void beforeAdvice(){
		System.out.println("***** beforeAdvice *****");
	}
	public void afterFinallyAdvice(){		
		System.out.println("***** afterFinallyAdvice *****");
	}
	
	public void beforeAdvice(String param){
		System.out.println("***** beforeAdvice:" + param + " *****");
	}
	
	public void afterReturningAdvice(Object returnValue){
		System.out.println("***** afterReturningAdvice:" + returnValue + " *****");
	}
	
	public void afterThrowingAdvice(Exception exception){
		System.out.println("***** afterThrowingAdvice:+"+ exception +" *****");
	}

	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
		Object proceed = joinPoint.proceed(new Object[]{"replace"});
		return proceed;
	}
}


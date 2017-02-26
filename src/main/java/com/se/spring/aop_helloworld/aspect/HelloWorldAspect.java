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

	/**
	 *如果不注释掉此方法，aop_schema.xml中“<aop:before method="beforeAdvice" arg-names="param" pointcut="execution(* com.se.spring.aop_helloworld.service.*.sayBefore(..)) and args(param)"/>”会报错，
	 * 因为xml中默认找的是第一个beforeAdvice方法，而此方法没有参数，故不匹配。
	 */
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


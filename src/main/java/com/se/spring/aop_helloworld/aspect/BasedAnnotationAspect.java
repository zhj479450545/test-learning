package com.se.spring.aop_helloworld.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class BasedAnnotationAspect {
	
	/**
	 * 切面、切入点、通知全部使用注解完成：
	1）使用@Aspect将POJO声明为切面；
	2）使用@Pointcut进行命名切入点声明，同时指定目标方法第一个参数类型必须是java.lang.String，对于其他匹配的方法但参数类型不一致的将也是不匹配的，通过argNames = “param”指定了将把该匹配的目标方法参数传递给通知同名的参数上；
	3）使用@Before进行前置通知声明，其中value用于定义切入点表达式或引用命名切入点；
	4）配置文件(aop_basedAnnotation.xml)需要使用<aop:aspectj-autoproxy/>来开启注解风格的@AspectJ支持；
	5）需要将切面注册为Bean，如“aspect”Bean；
	 */
	 
			
	/*
	 * value：指定切入点表达式；
		argNames：指定命名切入点方法参数列表参数名字，可以有多个用“，”分隔，这些参数将传递给通知方法同名的参数，同时比如切入点表达式“args(param)”将匹配参数类型为命名切入点方法同名参数指定的参数类型。
		pointcutName：切入点名字，可以使用该名字进行引用该切入点表达式。(定义了一个切入点，名字为“beforePointcut”，该切入点将匹配目标方法的第一个参数类型为通知方法实现中参数名为“param”的参数类型。)
	 */
	@Pointcut(value="execution(* com.se.spring.aop_helloworld.service.*.sayAdvisorBefore(..)) && args(param)", argNames="param")
	public void beforePointcut(String param){}
	
	@Before(value="beforePointcut(param)", argNames="param")
	public void beforeAdvice(String param){
		System.out.println("***** beforeAdvice:" + param + " *****");
	}
}


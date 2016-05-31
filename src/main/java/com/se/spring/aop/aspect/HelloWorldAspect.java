package com.se.spring.aop.aspect;

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
}


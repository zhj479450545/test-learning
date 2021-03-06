package com.se.spring.aop_helloworld.service.impl;

import com.se.spring.aop_helloworld.service.IHelloWorldService;
/**
 * 
 * @项目名称：test-learning
 * @包名：com.se.spring.aop.service.impl
 * @类名称：HelloWorldService.java
 * @类描述：目标接口实现
 * @创建人：zhoujian
 * @创建时间：2016年5月31日
 * @修改人：zhoujian
 * @修改时间：2016年5月31日
 * @修改备注：
 * @version
 */
public class HelloWorldService implements IHelloWorldService {

	public void sayHello() {
		System.out.println("***** sayHello *****");
	}

	public void sayBefore(String param) {
		System.out.println("***** sayBefore:" + param + " *****");
	}

	public boolean sayAfterReturning() {
		System.out.println("***** sayAfterReturning *****");
		return true;
	}

	public void sayAfterThrowing() {
		System.out.println("***** sayAfterThrowing *****");
		throw new RuntimeException();
	}

	public void sayAfterFinally() {
		System.out.println("***** sayAfterFinally *****");
		throw new RuntimeException();
	}

	public void sayAround(String param) {
		System.out.println("***** sayAround:"+ param +" *****");
	}

	public void sayAdvisorBefore(String param) {
		System.out.println("***** sayAdvisorBefore:"+ param +" *****");
	}

}


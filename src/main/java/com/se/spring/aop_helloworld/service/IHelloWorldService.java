package com.se.spring.aop_helloworld.service;

/**
 * 
 * @项目名称：test-learning
 * @包名：com.se.spring.aop.service
 * @类名称：IHelloWorldService.java
 * @类描述：目标接口
 * @创建人：zhoujian
 * @创建时间：2016年5月31日
 * @修改人：zhoujian
 * @修改时间：2016年5月31日
 * @修改备注：
 * @version
 */
public interface IHelloWorldService {

	public void sayHello();
	
	public void sayBefore(String param);
	
	public boolean sayAfterReturning();
	
	public void sayAfterThrowing();
}


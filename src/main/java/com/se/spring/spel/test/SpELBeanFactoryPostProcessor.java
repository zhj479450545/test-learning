package com.se.spring.spel.test;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.se.spring.spel.bean.SpELBean;

/**
 * 
 * @项目名称：test-learning
 * @包名：com.se.spring.spel.test
 * @类名称：SpELBeanFactoryPostProcessor.java
 * @类描述：
 * @创建人：zhoujian
 * @创建时间：2016年5月31日
 * @修改人：zhoujian
 * @修改时间：2016年5月31日
 * @修改备注：
 * @version
 */
public class SpELBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	/**
	 * 替换换个前缀和后缀该如何实现方法：
	 * 我们使用BeanFactoryPostProcessor接口提供postProcessBeanFactory回调方法，它是在IoC容器创建好但还未进行任何Bean初始化时被ApplicationContext实现调用，
	 * 因此在这个阶段把SpEL前缀及后缀修改掉是安全的，具体代码如下：
	 */
			
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		StandardBeanExpressionResolver resover = (StandardBeanExpressionResolver) beanFactory.getBeanExpressionResolver();
		resover.setExpressionPrefix("%{");
		resover.setExpressionSuffix("}%");
	}
	
	@Test
	public void testPrefixExpression(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spel/spel_2.xml");
		SpELBean helloBean = context.getBean("helloBean1", SpELBean.class);
		System.out.println(helloBean);
		
		SpELBean helloBean2 = context.getBean("helloBean2", SpELBean.class);
		System.out.println(helloBean2);
	}

}


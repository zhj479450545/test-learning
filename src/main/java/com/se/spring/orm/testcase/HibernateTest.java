package com.se.spring.orm.testcase;


import com.se.spring.orm.vo.UserModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HibernateTest {
	
	private static SessionFactory sessionFactory;
	
	@BeforeClass
	public static void obtenSessionFactory(){
		String[] configLocations = new String[]{"classpath:orm/db.xml", "classpath:orm/hibernate.xml"};
		
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocations);
		sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
	}
	
	@Test
	public void testFirstCase() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = beginTransaction(session);
			UserModel userModel = new UserModel();
			userModel.setMyName("myName");
			session.save(userModel);
		} catch (Exception e) {
			rollbackTransaction(transaction);
			e.printStackTrace();
		} finally {
			closeTransaction(session);
		}
	}
	
	private Transaction beginTransaction(Session session){
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		return transaction;
	}
	
	private void rollbackTransaction(Transaction transaction){
		if(transaction != null){
			transaction.rollback();
		}
	}
	
	private void closeTransaction(Session session){
		if(session != null){
			session.close();
		}
	}
}


package com.se.spring.aop_helloworld.service.impl;

import com.se.spring.aop_helloworld.service.IIntroductionService;

public class IntroductionService implements IIntroductionService {

	public void induct() {
		System.out.println("***** induct *****");
	}

}


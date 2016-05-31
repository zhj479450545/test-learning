package com.se.spring.spel.bean;

import org.springframework.beans.factory.annotation.Value;

public class SpELBean {
	@Value("hehe")
	private String value;

	
	public SpELBean() {
		super();
	}

	public SpELBean(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SpELBean [value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
	
}


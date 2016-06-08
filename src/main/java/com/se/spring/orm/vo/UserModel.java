package com.se.spring.orm.vo;

import java.io.Serializable;

public class UserModel implements Serializable {
	
	private static final long serialVersionUID = -9190853219867251143L;

	private int id;
	
	private String myName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}
	
}


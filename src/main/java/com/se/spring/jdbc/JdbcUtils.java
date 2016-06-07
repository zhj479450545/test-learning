package com.se.spring.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcUtils {
	private static final String driverClassName="com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://127.0.0.1:3306/test";
	private static final String username = "root";
	private static final String password = "zhoujian";
	
	public static JdbcTemplate getJdbcTemplate(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
		dataSource.setDriverClassName(driverClassName);
		return new JdbcTemplate(dataSource);
	}
}


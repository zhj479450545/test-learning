package com.se.spring.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcTemplateTest {
	private static JdbcTemplate jdbcTemplate;
	
	@BeforeClass
	public static void setUpClass(){
		String driverClassName="com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/test";
		String username = "root";
		String password = "zhoujian";
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
		dataSource.setDriverClassName(driverClassName);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void test(){
		String sql = "select * from user";
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql);
		System.out.println(queryForList);
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			
			public void processRow(ResultSet rs) throws SQLException {
				String value = rs.getString("name");
				System.out.println("Column USER:" + value);
			}
		});
	}
	
	//	1）预编译语句及存储过程创建回调、自定义功能回调使用：
	@Test
	public void testPpreparedStatement1(){
		int count = jdbcTemplate.execute(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				
				return con.prepareStatement("select count(0) from user");
			}
		}, new PreparedStatementCallback<Integer>() {

			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.execute();
				ResultSet rs = ps.getResultSet();
				rs.next();
				return rs.getInt(1);
			}
		});
		System.out.println(count);
	}
	
	//	2）预编译语句设值回调使用：
	@Test
	public void testPreparedStatement2(){
		String insertSql = "insert into user (name) values(?)";
		int count = jdbcTemplate.update(insertSql, new PreparedStatementSetter(){

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, "xxxx");
			}
		});
		System.out.println(count);
		
		String deleteSql = "delete from user where name=?";
		count = jdbcTemplate.update(deleteSql, new Object[]{"xxxx"});
		System.out.println(count);
	}
	
	//	3）结果集处理回调：
	@Test
	public void testResultSet1(){
		String insertSql = "insert into user(name) values('asdfghj')";
		jdbcTemplate.update(insertSql);
		String querySql = "select * from user";
		List result = jdbcTemplate.query(querySql, new RowMapper<Map>(){
			public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map row = new HashMap();
				row.put(rs.getInt("id"), rs.getString("name"));
				return row;
			}
		});
		System.out.println(result.size());
		String deleteSql = "delete from user where name='asdfghj'";
		jdbcTemplate.update(deleteSql);
	}
}


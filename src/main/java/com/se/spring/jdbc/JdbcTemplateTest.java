package com.se.spring.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.util.StringUtils;

public class JdbcTemplateTest {
	private static JdbcTemplate jdbcTemplate;
	
	@BeforeClass
	public static void setUpClass(){
		jdbcTemplate = JdbcUtils.getJdbcTemplate();
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
	
	/**
	 * 准备工作：创建表、函数、存储过程
	 */
//	@Before
	public void setUp(String functionName, String procedrueName, String tableName){
		// 注：sql语句中不能是多条语句(语法中的复合语句除外)，否则会报语法错误
		if(StringUtils.isEmpty(tableName)){
			//	1).判断表存在并删除
			String dropTableSql = "drop table if exists " + tableName;
			jdbcTemplate.update(dropTableSql);
			//	2).建表
			String createTableSql = "create table " + tableName + "(id int(11) not null auto_increment, name varchar(50), primary key(id))";
			jdbcTemplate.update(createTableSql);
		} if(StringUtils.isEmpty(functionName)){
			//	3).创建函数
			String createFunctionSql = "create function " + functionName + "(str char(50)) returns int(11) begin return length(str);end";
			jdbcTemplate.update(createFunctionSql);
		} if(StringUtils.isEmpty(procedrueName)){
			//	4).创建存储过程
			String createProcedureSql = "create procedure " + procedrueName + "(inout inoutname varchar(50), out outid int) modifies sql data begin insert into test_jdbc(name) values(inoutname); set outid=identity(); set inoutname='hello'+inoutname;end;";
			jdbcTemplate.execute(createProcedureSql);
		}
	}
	
	/**
	 * 后续工作：删除表、函数、存储过程
	 */
//	@After
	public void tearDown(String functionName, String procedrueName, String tableName){
		if(!StringUtils.isEmpty(functionName)){			
			jdbcTemplate.execute("drop function if exists " + functionName);
		} if(!StringUtils.isEmpty(procedrueName)){			
			jdbcTemplate.execute("drop procedure if exists " + procedrueName);
		} if(!StringUtils.isEmpty(tableName)){			
			jdbcTemplate.execute("drop table if exists " + tableName);
		}
	}
	
	//	mysql如何调用自定义函数:
	@Test
	public void testCallableStatementCreator_fun(){
		setUp("function_test", "procedure_test", "test_jdbc");
		//	创建自定义函数
		String createFuntionSql = "create function function_test(str varchar(50)) returns int return length(str)";
		String dropFunctionSql = "drop function if exists function_test";
		jdbcTemplate.update(dropFunctionSql);
		jdbcTemplate.update(createFuntionSql);
		//	准备sql, mysql支持(?=call ...)
		final String callFunctionSql = "{?=call function_test(?)}";
		//	定义参数
		List<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlOutParameter("result", Types.INTEGER));
		params.add(new SqlParameter("str", Types.INTEGER));
		Map<String, Object> outValueMap = jdbcTemplate.call(new CallableStatementCreator() {
			
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cStatement = con.prepareCall(callFunctionSql);
				cStatement.registerOutParameter(1, Types.INTEGER);
				cStatement.setString(2, "天哪");
				return cStatement;
			}
		}, params);
		System.out.println(outValueMap.get("result"));
		tearDown("function_test", "procedure_test", "test_jdbc");
	}
	
	/**
	 * 调用存储过程
	 */
	@Test
	public void testCallableStatementCreator_pro(){
		setUp("function_test", "procedure_test", "test_jdbc");
		final String callProcedureSql = "{call procedure_test(?, ?)}";
		List<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlInOutParameter("inOutName", Types.VARCHAR));
		params.add(new SqlOutParameter("outId", Types.INTEGER));
		Map<String, Object> outValues = jdbcTemplate.call(new CallableStatementCreator() {
			
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cStatement = con.prepareCall(callProcedureSql);
				cStatement.registerOutParameter(1, Types.VARCHAR);
				cStatement.registerOutParameter(2, Types.INTEGER);
				cStatement.setString(1, "test");
				
				return cStatement;
			}
		}, params);
		System.out.println(outValues.get("inOutName"));
		System.out.println(outValues.get("outId"));
		tearDown("function_test", "procedure_test", "test_jdbc");
	}
}


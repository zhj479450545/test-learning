package com.se.spring.jdbc;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * 
 * @项目名称：test-learning
 * @包名：com.se.spring.jdbc
 * @类名称：RdbmsOperation.java
 * @类描述：Spring提供的其它帮助
 * @创建人：zhoujian
 * @创建时间：2016年6月6日
 * @修改人：zhoujian
 * @修改时间：2016年6月6日
 * @修改备注：
 * @version
 */
public class SimpleJdbcTest {
	private static JdbcTemplate jdbcTemplate;
	
	@BeforeClass
	public static void setUpClass(){
		jdbcTemplate = JdbcUtils.getJdbcTemplate();
	}
	
	/**
	 * SimpleJdbcInsert用于插入数据
	 */
	@Test
	public void testSimpleJdbcInsert(){
//		String createTableSql = "create table test_jdbc(id int not null auto_increment, name varchar(50), primary key(id));";
//		jdbcTemplate.update(createTableSql);
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);//首次通过DataSource对象或JdbcTemplate对象初始化SimpleJdbcInsert
		insert.withTableName("test_jdbc");//用于设置数据库表名
		Map<String, Object> args = new HashMap<String, Object>();// 用于指定插入时列名及值，如本例中只有name列名，即编译后的sql类似于“insert into test(name) values(?)”
		args.put("name", "name_1");
		insert.compile();//可选的编译步骤，在调用执行方法时自动编译，编译后不能再对insert对象修改
		//	1.普通插入
		insert.execute(args);//execute方法用于执行普通插入
		Map<String, Object> queryForMap = jdbcTemplate.queryForMap("select count(*) from test_jdbc");
		//	2.插入时获取主键
		insert = new SimpleJdbcInsert(jdbcTemplate);
		insert.withTableName("test_jdbc");
		insert.setGeneratedKeyName("id");//设置主键
		Number id = insert.executeAndReturnKey(args);//executeAndReturnKey用于执行并获取自动生成主键（注意是Number类型）,必须先通过setGeneratedKeyName设置主键然后才能获取。
													//如果想获取复合主键请使用setGeneratedKeyNames描述主键然后通过executeReturningKeyHolder获取复合主键KeyHolder对象
		//	3.批处理
		insert = new SimpleJdbcInsert(jdbcTemplate);
		insert.withTableName("test_jdbc");
		insert.setGeneratedKeyName("id");
		int[] updateCount = insert.executeBatch(new Map[]{args, args, args});//executeBatch用于批处理
		System.out.println(updateCount.toString());
		Integer queryForObject = jdbcTemplate.queryForObject("select count(*) from test_jdbc", int.class);
		System.out.println(queryForObject);
	}
	
	@Test
	public void testSimpleJdbcCallFunction(){
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);//通过DataSource对象或JdbcTemplate对象初始化SimpleJdbcCall
		call.withFunctionName("function_test");//定义自定义函数名(必须存在于数据库中)；自定义函数sql语句将被编译为类似于{?= call …}形式；
		call.declareParameters(new SqlOutParameter("result", Types.INTEGER));// 描述参数类型，使用方式与StoredProcedure对象一样
		call.addDeclaredParameter(new SqlParameter("str", Types.VARCHAR));
		Map<String, Object> outValues = call.execute("test_jdbc");//调用execute方法执行自定义函数；
		int result = (Integer) outValues.get("result");
		System.out.println(result);
	}
	
	@Test
	public void testSimpleJdbcCallProcedure(){
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
		call.withProcedureName("procedure_test");
		call.declareParameters(new SqlInOutParameter("inOutName", Types.VARCHAR));
		call.declareParameters(new SqlOutParameter("outId", Types.INTEGER));
		SqlParameterSource params = new MapSqlParameterSource().addValue("inOutName", "test_jdbc");
		Map<String, Object> outValues = call.execute(params);
		String result = (String) outValues.get("inOutName");
		System.out.println(result);
		int result2 = (Integer) outValues.get("outId");
		System.out.println(result2);
		
	}
	
	/**
	 * 获取自动生成的主键
	 */
	@Test
	public void testFetchKey1(){
		final String insertSql = "insert into test_jdbc(name)values('12345')";
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(insertSql, new String[]{"id"});
			}
		}, holder);
		System.out.println(holder.getKey());
	}
	
	/**
	 * SqlUpdate 获取自动生成主键方式
	 */
	@Test
	public void testFetchKey2(){
		final String insertSql = "insert into test_jdbc(name) values('54321');";
		KeyHolder holder = new GeneratedKeyHolder();
		SqlUpdate update = new SqlUpdate();
		update.setJdbcTemplate(jdbcTemplate);
		update.setReturnGeneratedKeys(true);
		update.setSql(insertSql);
		update.update(null, holder);
		Number key = holder.getKey();
		System.out.println(key);
	}
	
	/**
	 * 1)JdbcTemplate 批处理： 支持普通的批处理及占位符批处理
	 * (1.1)普通批处理
	 */
	@Test
	public void testBatchUpdate1(){
		String insertSql = "insert into test_jdbc(name) values('09876')";
		String[] batchSql = new String[]{insertSql, insertSql};
		// 对于普通的批处理直接调用batchUpdate方法执行语句即可
		jdbcTemplate.batchUpdate(batchSql);
		Integer count = jdbcTemplate.queryForObject("select count(0) from test_jdbc", int.class);
		System.out.println(count);
	}
	
	/**
	 * (1.2)占位符批处理
	 */
	@Test
	public void testBatchUpdate2(){
		String insertSql = "insert into test_jdbc(name) values(?)";
		final String[] batchValues = new String[]{"batch1","batch2"};
		jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, batchValues[i]);
				
			}
			
			public int getBatchSize() {
				return batchValues.length;
			}
		});
		Integer count = jdbcTemplate.queryForObject("select count(0) from test_jdbc", int.class);
		System.out.println(count);
	}
	
	/**
	 * 2)NamedParameterJdbcTemplate 批处理： 支持命名参数批处理；
	 * 
	 */
	@Test
	public void testBatchUpdate3(){
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String insertSql = "insert into test_jdbc(name) values(':myName')";// mysql插入到数据库的值为":myName"；即下面map里的值没起作用
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "pkjuhq");
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(new Map[]{map});
		namedParameterJdbcTemplate.batchUpdate(insertSql, params);
		Integer count = jdbcTemplate.queryForObject("select count(0) from test_jdbc", int.class);
		System.out.println(count);
	}
	
	/**
	 * 3)SimpleJdbcTemplate 批处理： 已更简单的方式进行批处理；
	 */
	@Test
	public void testBatchUpdate4(){
//		SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(jdbcTemplate); 
		 String insertSql = "insert into test(name) values(?)"; 
		 List<Object[]> params = new ArrayList<Object[]>(); 
		 params.add(new Object[]{"name5"}); 
		 params.add(new Object[]{"name5"}); 
//		 simpleJdbcTemplate.batchUpdate(insertSql, params); 
		 System.out.println(jdbcTemplate.queryForObject("select count(*) from test", int.class));
	}
	
	/**
	 * 5)SimpleJdbcInsert 批处理：
	 */
	public void testBatchUpdate5(){
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate); 
		 insert.withTableName("test"); 
		 Map<String, Object> valueMap = new HashMap<String, Object>(); 
		 valueMap.put("name", "name5"); 
		 insert.executeBatch(new Map[] {valueMap, valueMap}); 
		 System.out.println(jdbcTemplate.queryForObject("select count(*) from test", int.class)); 
	}
}


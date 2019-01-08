package com.zbjdl.boss.admin;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>所有DAO层（或涉及DAO层）的单元测试，需要继承此类。所有继承此类的dao单元测试方法执行完成后，都会将事务进行回滚，防止数据库操作之间互相影响。</p>
 */
@ContextConfiguration(locations={"classpath:/testContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="txManager", defaultRollback=true)
@Transactional
public class BaseDaoTest {
	
	static{
		//不要禁用远程调用组件，否则在linux环境下可能会首先加载此类，
		//导致需要mockrmi为true的测试场景无法运行通过
		//（如果mvn test设置为在一个线程内运行所有单元测试，则这个初始化只会进行一次）
//		System.setProperty("disablermi","true");
		System.setProperty("mockrmi", "true");
		System.setProperty("mockconfig", "true");
	}

	private static ScriptRunnerWrapper scriptRunnerWrapper;
	
    protected JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@BeforeClass
	public static void initTestData() throws IOException, SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("/testContext.xml");
		scriptRunnerWrapper = (ScriptRunnerWrapper) context.getBean("scriptRunnerWrapper");
		scriptRunnerWrapper.initTestData();
		
	}
	
	@AfterClass  
	public static void afterDaoTest() {
		scriptRunnerWrapper.release();
	}
}

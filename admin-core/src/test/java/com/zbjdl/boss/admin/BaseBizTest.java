package com.zbjdl.boss.admin;

import org.jmock.Mockery;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * biz层测试基类
 *
 * @author xingwei.bi
 * @version 1.0
 * @Copyright: Copyright (c)2018
 * @company 云宝金服
 * @since 2011-6-8
 */
@ContextConfiguration(locations = {"classpath:/testContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class BaseBizTest {

	static {
		//禁用远程调用组件
		System.setProperty("disablermi", "true");
		System.setProperty("mockrmi", "true");
		System.setProperty("mockconfig", "true");
	}

	private static ScriptRunnerWrapper scriptRunnerWrapper;

	protected Mockery mockery = new Mockery();

	@Autowired
	private JdbcTemplate jdbcTemplate;

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

	public void save(String sql) {
		jdbcTemplate.execute(sql);
	}

	public void update(String sql) {
		jdbcTemplate.execute(sql);
	}

	public void delete(String sql) {
		jdbcTemplate.execute(sql);
	}

	/**
	 * List 里的每个对应是一个Map
	 */
	@SuppressWarnings("rawtypes")
	public List query(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	@Test
	public void test() {

	}
}

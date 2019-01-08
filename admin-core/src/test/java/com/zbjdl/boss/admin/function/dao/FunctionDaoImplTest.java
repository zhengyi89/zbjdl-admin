/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.function.dao;

import com.zbjdl.boss.admin.BaseDaoTest;
import com.zbjdl.boss.admin.function.entity.FunctionEntity;
import com.zbjdl.boss.admin.function.enums.FunctionStatusEnum;
import com.zbjdl.boss.admin.repository.FunctionDao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 类名称：FunctionDaoImplTest <br>
 * 类描述：   <br>
 *
 * @author：feng
 * @since：2011-7-7 下午04:27:43
 * @version:
 */
public class FunctionDaoImplTest extends BaseDaoTest {

	@Autowired
	private FunctionDao functionDao;

	@Test
	public void testAdd() {
		FunctionEntity function = new FunctionEntity();
		function.setFunctionName("功能1");
		function.setPreFunctionId(1L);
		function.setFunctionUrl("http://www.test.com");
		function.setFunctionStatus(FunctionStatusEnum.ACTIVE);
		function.setCreateTime(new Timestamp(198765432));
		//function.setDeptIds("1,2,3");
		functionDao.save(function);

		System.out.println(function.getFunctionId());

		FunctionEntity entity = functionDao.selectById(function.getFunctionId());

		System.out.println(entity.getFunctionId());

		Assert.assertEquals(function.getFunctionId(), entity.getFunctionId());
	}

	@Test
	public void testDelete() {
		long functionId = 0;
		functionDao.delete(functionId);
		FunctionEntity nowFunction = functionDao.selectById(functionId);
		System.out.println(nowFunction == null);
		Assert.assertNull(nowFunction);

	}

	@Test
	public void testUpdate() {
		long functionId = -1;
		FunctionEntity function = functionDao.selectById(functionId);
		String changedName = "测试功能2";
		function.setFunctionName(changedName);
		functionDao.update(function);
		FunctionEntity nowFunction = functionDao.selectById(functionId);
		System.out.println(nowFunction.getFunctionName());
		Assert.assertEquals(changedName, nowFunction.getFunctionName());
	}

	@Test
	public void testGet() {
		long functionId = -1;
		FunctionEntity function = functionDao.selectById(functionId);
		System.out.println(function.getFunctionId());
		Assert.assertNotNull(function);
	}

	@Test
	public void testGetAll() {
		List<FunctionEntity> functions = functionDao.findAll();
		Assert.assertTrue(functions.size() != 0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testQuery() {
		FunctionEntity entity = new FunctionEntity();
		entity.setFunctionId(-1L);
		List<FunctionEntity> functions = functionDao.queryByFunction(entity);
		Assert.assertTrue(functions.size() != 0);
	}

	@Test
	public void testIsPreFunction() {
		long functionId = 0;
		int count = (Integer) functionDao.isPreFunction(functionId);
		System.out.println(count);
		Assert.assertEquals(0, count);
	}

}

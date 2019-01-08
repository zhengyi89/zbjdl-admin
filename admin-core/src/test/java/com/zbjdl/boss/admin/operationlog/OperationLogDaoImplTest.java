/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.operationlog;

import com.zbjdl.boss.admin.BaseDaoTest;
import com.zbjdl.boss.admin.operationlog.entity.OperationLogEntity;
import com.zbjdl.boss.admin.repository.OperationLogDao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 类名称：OperationLogDaoImplTest <br>
 * 类描述：操作日志DAO实现类单元测试<br>
 *
 * @author：feng
 * @since：2011-6-27 上午11:10:47
 * @version:
 */
public class OperationLogDaoImplTest extends BaseDaoTest {

	@Autowired
	private OperationLogDao operationLogDao;

	@Test
	public void testAdd() {
		//组装user实体
		OperationLogEntity entity = new OperationLogEntity();
		entity.setKeyWord("test");
		entity.setLogContent("testcontent");
		entity.setLogTime(new Date());

		//插入数据
		operationLogDao.save(entity);
		OperationLogEntity entity1 = operationLogDao.selectById(entity.getId());
		Assert.assertEquals(entity.getId(), entity1.getId());
	}

	@Test
	public void testGet() {
		OperationLogEntity log = operationLogDao.selectById(0L);
		Assert.assertNull(log);
	}

	@Test
	public void testGetAll() {
		List<OperationLogEntity> list = operationLogDao.findAll();
		Assert.assertNotNull(list);
	}

	@Test
	public void testQuery() {
		OperationLogEntity department = new OperationLogEntity();
		department.setId(0L);
		@SuppressWarnings("unchecked")
		List<OperationLogEntity> list = operationLogDao.findList(department);
		Assert.assertNotNull(list);
	}

}

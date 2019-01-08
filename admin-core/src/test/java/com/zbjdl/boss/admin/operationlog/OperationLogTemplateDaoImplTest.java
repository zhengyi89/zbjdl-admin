/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.operationlog;

import com.zbjdl.boss.admin.BaseDaoTest;
import com.zbjdl.boss.admin.operationlog.entity.OperationLogTemplateEntity;
import com.zbjdl.boss.admin.repository.OperationLogTemplateDao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 类名称：operationLogTemplateDaoImplTest <br>
 * 类描述：操作日志模板DAO实现类单元测试<br>
 *
 * @author：feng
 * @since：2011-6-27 上午11:10:47
 * @version:
 */
public class OperationLogTemplateDaoImplTest extends BaseDaoTest {

	@Autowired
	private OperationLogTemplateDao operationLogTemplateDao;

	@Test
	public void testAdd() {
		//组装user实体
		OperationLogTemplateEntity entity = new OperationLogTemplateEntity();
		entity.setKeyWord("test1");
		entity.setContent("templateContent1");
		entity.setFunctionId(121232132L);

		//插入数据
		operationLogTemplateDao.save(entity);
		OperationLogTemplateEntity entity1 = operationLogTemplateDao.selectById(entity.getId());
		Assert.assertEquals(entity.getId(), entity1.getId());
	}

	@Test
	public void testGet() {
		OperationLogTemplateEntity log = operationLogTemplateDao.selectById(0L);
		Assert.assertNull(log);
	}

	@Test
	public void testGetAll() {
		List<OperationLogTemplateEntity> list = operationLogTemplateDao.findAll();
		Assert.assertNotNull(list);
	}

	@Test
	public void testQuery() {
		OperationLogTemplateEntity entity = new OperationLogTemplateEntity();
		entity.setId(0L);
		@SuppressWarnings("unchecked")
		List<OperationLogTemplateEntity> list = operationLogTemplateDao.findList(entity);
		Assert.assertNotNull(list);
	}

	@Test
	public void testQueryByFunctionId() {
		OperationLogTemplateEntity template = (OperationLogTemplateEntity) operationLogTemplateDao.queryByFunctionId(6L);

		Assert.assertNull(template);
	}

}

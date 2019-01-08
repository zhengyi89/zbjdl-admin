/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.dao;

import com.zbjdl.boss.admin.BaseDaoTest;
import com.zbjdl.boss.admin.repository.DepartmentDao;
import com.zbjdl.boss.admin.user.entity.DepartmentEntity;
import com.zbjdl.boss.admin.user.enums.DepartmentStatusEnum;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 类名称：DepartmentDaoImplTest <br>
 * 类描述：部门DAO实现类单元测试<br>
 *
 * @author：feng
 * @since：2011-6-27 上午11:10:47
 * @version:
 */
public class DepartmentDaoImplTest extends BaseDaoTest {

	@Autowired
	private DepartmentDao departmentDao;

	@Test
	public void testGet() {
		DepartmentEntity department = departmentDao.selectById(1L);
		Assert.assertNotNull(department);
	}

	@Test
	public void testGetAll() {
		List<DepartmentEntity> list = departmentDao.findAll();
		Assert.assertNotNull(list);
	}

	@Test
	public void testQuery() {
		DepartmentEntity department = new DepartmentEntity();
		department.setId(0L);
		@SuppressWarnings("unchecked")
		List<DepartmentEntity> list = departmentDao.findList(department);
		Assert.assertNotNull(list);
	}

	@Test
	public void testAdd() {
		//组装user实体
		DepartmentEntity department = new DepartmentEntity();
		department.setDepartmentCode("testCode1");
		department.setDepartmentDesc("测试部门");
		department.setDepartmentName("部门123");
		department.setDepartmentStatus(DepartmentStatusEnum.ACTIVE);

		//插入数据
		departmentDao.save(department);
		DepartmentEntity department1 = departmentDao.selectById(department.getId());
		Assert.assertEquals(department.getId(), department1.getId());
	}

	@Test
	public void testUpdate() {
		//组装user实体
		DepartmentEntity department = departmentDao.selectById(1L);
		if (department == null) {
			Assert.assertTrue(false);
		}
		department.setDepartmentCode("testCode1");
		department.setDepartmentDesc("测试部门");
		department.setDepartmentName("部门123");
		department.setDepartmentStatus(DepartmentStatusEnum.ACTIVE);
		//插入数据
		departmentDao.update(department);
		DepartmentEntity department1 = departmentDao.selectById(department.getId());
		Assert.assertEquals("测试部门", department1.getDepartmentDesc());
	}

	@Test
	public void testDelete() {
		DepartmentEntity department = departmentDao.selectById(1L);
		Assert.assertNotNull(department);
		departmentDao.delete(department.getId());
		department = departmentDao.selectById(department.getId());
		Assert.assertNull(department);
		departmentDao.save(department);
	}

}

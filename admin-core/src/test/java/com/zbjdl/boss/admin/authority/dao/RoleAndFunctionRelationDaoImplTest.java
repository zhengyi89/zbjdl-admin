/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.authority.dao;

import com.zbjdl.boss.admin.BaseDaoTest;
import com.zbjdl.boss.admin.authority.entity.RoleAndFunctionRelationEntity;
import com.zbjdl.boss.admin.repository.RoleAndFunctionRelationDao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 类名称：RoleAndFunctionRelationDaoImplTest <br>
 * 类描述：   <br>
 *
 * @author：feng
 * @since：2011-7-8 下午07:34:42
 * @version:
 */
public class RoleAndFunctionRelationDaoImplTest extends BaseDaoTest {

	@Autowired
	private RoleAndFunctionRelationDao roleAndFunctionRelationDao;

	@Test
	public void testAdd() {
		RoleAndFunctionRelationEntity entity = new RoleAndFunctionRelationEntity();
		entity.setFunctionId(1L);
		entity.setRoleId(2L);
		roleAndFunctionRelationDao.save(entity);
		System.out.println(entity.getId());
		RoleAndFunctionRelationEntity entity1 = roleAndFunctionRelationDao.selectById(entity.getId());

		Assert.assertEquals(entity1.getFunctionId(), entity.getFunctionId());
	}

	@Test
	public void testQueryByRoleId() {
		Long roleId = 1L;
		List<RoleAndFunctionRelationEntity> list = roleAndFunctionRelationDao.queryByRoleId(roleId);
		Assert.assertTrue(list != null);
	}

	@Test
	public void testQueryByFunctionId() {
		Long functionId = 2L;
		List<RoleAndFunctionRelationEntity> list = roleAndFunctionRelationDao.queryByFunctionId(functionId);
		Assert.assertTrue(list != null);
	}


	@Test
	public void testDelete() {
		RoleAndFunctionRelationEntity entity = new RoleAndFunctionRelationEntity();
		entity.setFunctionId(2L);
		entity.setRoleId(1L);
		roleAndFunctionRelationDao.deleteRoleAndFunctionRelation(entity);
	}


}

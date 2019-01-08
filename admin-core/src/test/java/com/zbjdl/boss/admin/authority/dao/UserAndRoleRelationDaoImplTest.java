/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.authority.dao;

import com.zbjdl.boss.admin.BaseDaoTest;
import com.zbjdl.boss.admin.authority.entity.UserAndRoleRelationEntity;
import com.zbjdl.boss.admin.repository.UserAndRoleRelationDao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 类名称：UserAndRoleRelationDaoImplTest <br>
 * 类描述：   <br>
 *
 * @author：feng
 * @since：2011-7-8 下午08:17:56
 * @version:
 */
public class UserAndRoleRelationDaoImplTest extends BaseDaoTest {

	@Autowired
	private UserAndRoleRelationDao userAndRoleRelationDao;

	@Test
	public void testAdd() {
		UserAndRoleRelationEntity entity = new UserAndRoleRelationEntity();
		entity.setRoleId(1000L);
		entity.setUserId(1000L);
		userAndRoleRelationDao.save(entity);
		System.out.println(entity.getId());
		UserAndRoleRelationEntity entity1 = userAndRoleRelationDao.selectById(entity.getId());
		Assert.assertEquals(entity.getRoleId(), entity1.getRoleId());
	}

	@Test
	public void testQueryByRoleId() {
		Long roleId = 2L;
		List<UserAndRoleRelationEntity> list = userAndRoleRelationDao.queryByRoleId(roleId);
		Assert.assertTrue(list != null);
	}

	@Test
	public void testQueryByUserId() {
		Long userId = 1L;
		List<UserAndRoleRelationEntity> list = userAndRoleRelationDao.queryByUserId(userId);
		Assert.assertTrue(list != null);
	}

	@Test
	public void testQuery() {
		UserAndRoleRelationEntity entity = new UserAndRoleRelationEntity();
		entity.setUserId(1L);
		entity.setRoleId(2L);
		UserAndRoleRelationEntity obj = userAndRoleRelationDao.queryByRelation(entity);
		Assert.assertTrue(obj != null);
	}

	@Test
	public void testDelete() {
		UserAndRoleRelationEntity entity = new UserAndRoleRelationEntity();
		entity.setRoleId(2L);
		entity.setUserId(1L);
		userAndRoleRelationDao.deleteUserAndRoleRelation(entity);
	}

}

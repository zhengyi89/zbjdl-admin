/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.authority.dao;

import com.zbjdl.boss.admin.BaseDaoTest;
import com.zbjdl.boss.admin.authority.entity.RoleEntity;
import com.zbjdl.boss.admin.repository.RoleDao;
import com.zbjdl.boss.admin.role.enums.RoleStatusEnum;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 类名称：RoleDaoImplTest <br>
 * 类描述：   <br>
 *
 * @author：feng
 * @since：2011-7-7 上午10:37:46
 * @version:
 */
public class RoleDaoImplTest extends BaseDaoTest {

	@Autowired
	private RoleDao roleDao;

	@Test
	public void testAdd() {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRoleName("操作员");
		roleEntity.setRoleStatus(RoleStatusEnum.FORBID);
		roleEntity.setDescription("测试测试");
		roleDao.save(roleEntity);
		System.out.println(roleEntity.getRoleId());
		RoleEntity roleEntity1 = roleDao.selectById(roleEntity.getId());
		Assert.assertEquals(roleEntity1.getId(), roleEntity.getId());
	}

	@Test
	public void testGet() {
		Long roleId = 1L;
		RoleEntity roleEntity = roleDao.selectById(roleId);
		Assert.assertTrue(roleEntity.getRoleId() != null);
	}

	@Test
	public void testGetAll() {
		List<RoleEntity> list = roleDao.findAll();
		Assert.assertTrue(list != null);
	}

	@Test
	public void testQuery() {
		RoleEntity role = new RoleEntity();
//		role.setRoleStatus(RoleStatusEnum.ACTIVE);
		role.setRoleName("test11");
		List<RoleEntity> list = roleDao.queryByRole(role);
		Assert.assertTrue(list != null);
	}

	@Test
	public void update() {
		RoleEntity role = new RoleEntity();
		role.setRoleId(4L);
		role.setRoleName("业管");
		role.setDescription("测试一下");
		role.setRoleStatus(RoleStatusEnum.FORBID);
		roleDao.update(role);
	}

	@Test
	public void delete() {
		Long roleId = 4L;
		roleDao.delete(roleId);
	}


}

/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.dao;

import com.zbjdl.boss.admin.BaseDaoTest;
import com.zbjdl.boss.admin.repository.UserDao;
import com.zbjdl.boss.admin.user.entity.UserEntity;
import com.zbjdl.boss.admin.user.enums.UserStatusEnum;
import com.zbjdl.boss.admin.utils.StringBooleanUtil;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 类名称：UserDaoImplTest <br>
 * 类描述： 用户DAO实现类单元测试<br>
 *
 * @author：feng
 * @since：2011-6-25 上午10:16:58
 * @version:
 */
public class UserDaoImplTest extends BaseDaoTest {

	@Autowired
	private UserDao userDao;

	@Test
	public void testGet() {
		UserEntity user = userDao.selectById(1L);
		Assert.assertNotNull(user);
	}

	@Test
	public void testGetAll() {
		List<UserEntity> list = userDao.findAll();
		Assert.assertNotNull(list);

	}

	@Test
	public void testQuery() {
		UserEntity user = new UserEntity();
		user.setId(0L);
		@SuppressWarnings("unchecked")
		List<UserEntity> list = userDao.findList(user);
		Assert.assertNotNull(list);
	}

	@Test
	public void testAdd() {
		//组装user实体
		UserEntity user = new UserEntity();
		user.setIsAdmin(StringBooleanUtil.booleanToString(true));
		user.setUserName("贾英哲");
		user.setIsSuperAdmin(StringBooleanUtil.booleanToString(false));
		user.setLoginName("feng");
		user.setPassword("jlkausoidhgnalksdug");
		user.setPrimaryDepartmentId(1L);
		user.setPwdErrorNums(0);
		user.setUserStatus(UserStatusEnum.ACTIVE);
		//插入数据
		userDao.save(user);
		UserEntity user1 = userDao.selectById(user.getId());
		Assert.assertEquals(user.getId(), user1.getId());
	}

	@Test
	public void testUpdate() {
		//组装user实体
		UserEntity user = userDao.selectById(1L);
		if (user == null) {
			Assert.assertTrue(false);
		}
		user.setIsAdmin(StringBooleanUtil.booleanToString(true));
		user.setUserName("贾英哲");
		user.setIsSuperAdmin(StringBooleanUtil.booleanToString(false));
		user.setLoginName("feng1");
		user.setPassword("jlkausoidhgnalksdug");
		user.setPrimaryDepartmentId(2L);
		user.setPwdErrorNums(1);
		user.setUserStatus(UserStatusEnum.ACTIVE);
		//插入数据
		userDao.update(user);
		UserEntity user1 = userDao.selectById(user.getId());
		Assert.assertEquals(user.getPrimaryDepartmentId(), user1.getPrimaryDepartmentId());
	}

	@Test
	public void testDelete() {
		UserEntity user = userDao.selectById(1L);
		Assert.assertNotNull(user);
		userDao.delete(user.getId());
		user = userDao.selectById(user.getId());
		Assert.assertNull(user);
	}

	@Test
	public void testQueryByLoginName() {
		UserEntity user = new UserEntity();
		user.setLoginName("Admin");
		user = (UserEntity) userDao.queryUserByLoginName("Admin");
		Assert.assertNotNull(user);
	}

}

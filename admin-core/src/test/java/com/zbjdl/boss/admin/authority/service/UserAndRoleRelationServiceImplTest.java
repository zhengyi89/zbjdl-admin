/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.authority.service;

import com.zbjdl.boss.admin.BaseServiceTest;
import com.zbjdl.boss.admin.authority.entity.UserAndRoleRelationEntity;
import com.zbjdl.boss.admin.authority.service.impl.UserAndRoleRelationServiceImpl;
import com.zbjdl.boss.admin.repository.UserAndRoleRelationDao;

import org.jmock.Expectations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：UserAndRoleRelationServiceImplTest <br>
 * 类描述：  用户角色关系测试 <br>
 *
 * @author：feng
 * @since：2011-7-8 上午10:09:46
 * @version:
 */
public class UserAndRoleRelationServiceImplTest extends BaseServiceTest {

	private UserAndRoleRelationServiceImpl service = new UserAndRoleRelationServiceImpl();

	private UserAndRoleRelationDao userAndRoleRelationDao = mockery.mock(UserAndRoleRelationDao.class);

	@Before
	public void before() {
		service.setUserAndRoleRelationDao(userAndRoleRelationDao);
	}

	@Test
	public void testCreateUserAndRoleRelation() {
		final Long userId = 1L;
		final Long roleId = 2L;
		final UserAndRoleRelationEntity userAndRoleRelationEntity = new UserAndRoleRelationEntity();
		userAndRoleRelationEntity.setUserId(userId);
		userAndRoleRelationEntity.setRoleId(roleId);

		mockery.checking(new Expectations() {
			{
				oneOf(userAndRoleRelationDao).save(with(any(UserAndRoleRelationEntity.class)));
			}
		});
		service.createUserAndRoleRelation(userId, roleId);
	}

	@Test
	public void testQueryByUserId() {
		final Long userId = 1L;
		final List<UserAndRoleRelationEntity> list = new ArrayList<UserAndRoleRelationEntity>();
		list.add(new UserAndRoleRelationEntity());
		list.add(new UserAndRoleRelationEntity());
		mockery.checking(new Expectations() {
			{
				oneOf(userAndRoleRelationDao).queryByUserId(userId);
				will(returnValue(list));
			}
		});
		List<UserAndRoleRelationEntity> list1 = service.queryByUserId(userId);
		Assert.assertTrue(list1 != null);
	}

	@Test
	public void testQueryByRoleId() {
		final Long roleId = 1L;
		final List<UserAndRoleRelationEntity> list = new ArrayList<UserAndRoleRelationEntity>();
		list.add(new UserAndRoleRelationEntity());
		list.add(new UserAndRoleRelationEntity());
		mockery.checking(new Expectations() {
			{
				oneOf(userAndRoleRelationDao).queryByRoleId(roleId);
				will(returnValue(list));
			}
		});
		List<UserAndRoleRelationEntity> list1 = service.queryByRoleId(roleId);
		Assert.assertTrue(list1 != null);
	}

	@Test
	public void testDeleteUserAndRoleRelation() {
		final Long roleId = 1L;
		final Long userId = 2L;
		final UserAndRoleRelationEntity userAndRoleRelationEntity = new UserAndRoleRelationEntity();
		userAndRoleRelationEntity.setUserId(userId);
		userAndRoleRelationEntity.setRoleId(roleId);
		mockery.checking(new Expectations() {
			{
				oneOf(userAndRoleRelationDao).deleteUserAndRoleRelation(with(any(UserAndRoleRelationEntity.class)));
			}
		});
		service.deleteUserAndRoleRelation(userId, roleId);
	}

	@Test
	public void testQueryByUserIdAndRoleId() {
		final Long roleId = 1L;
		final Long userId = 2L;
		final UserAndRoleRelationEntity userAndRoleRelationEntity = new UserAndRoleRelationEntity();
		userAndRoleRelationEntity.setRoleId(roleId);
		userAndRoleRelationEntity.setUserId(userId);
		mockery.checking(new Expectations() {
			{
				oneOf(userAndRoleRelationDao).queryByRelation(with(any(UserAndRoleRelationEntity.class)));
				will(returnValue(userAndRoleRelationEntity));
			}
		});
		UserAndRoleRelationEntity userAndRoleRelation = service.queryByUserIdAndRoleId(userId, roleId);
		Assert.assertTrue(userAndRoleRelation != null);

	}

}

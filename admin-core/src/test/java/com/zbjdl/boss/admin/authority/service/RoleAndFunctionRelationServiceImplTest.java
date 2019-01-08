/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.authority.service;

import com.zbjdl.boss.admin.BaseServiceTest;
import com.zbjdl.boss.admin.authority.entity.RoleAndFunctionRelationEntity;
import com.zbjdl.boss.admin.authority.service.impl.RoleAndFunctionRelationServiceImpl;
import com.zbjdl.boss.admin.repository.RoleAndFunctionRelationDao;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：RoleAndFunctionRelationServiceImplTest <br>
 * 类描述：   角色功能关系service测试<br>
 *
 * @author：feng
 * @since：2011-7-8 上午11:36:03
 * @version:
 */
public class RoleAndFunctionRelationServiceImplTest extends BaseServiceTest {

	private RoleAndFunctionRelationServiceImpl service = new RoleAndFunctionRelationServiceImpl();

	private RoleAndFunctionRelationDao roleAndFunctionRelationDao = mockery.mock(RoleAndFunctionRelationDao.class);

	@Before
	public void before() {
		service.setRoleAndFunctionRelationDao(roleAndFunctionRelationDao);
	}


	@Test
	public void testCreateRoleAndFunctionRelation() {
		Long roleId = 1L;
		Long functionId = 2L;
		RoleAndFunctionRelationEntity roleAndFunctionRelationEntity = new RoleAndFunctionRelationEntity();
		roleAndFunctionRelationEntity.setRoleId(roleId);
		roleAndFunctionRelationEntity.setFunctionId(functionId);

		mockery.checking(new Expectations() {
			{
				oneOf(roleAndFunctionRelationDao).save(with(any(RoleAndFunctionRelationEntity.class)));
			}
		});
		service.createRoleAndFunctionRelation(roleId, functionId);

	}

	@Test
	public void testQueryByRoleId() {
		final Long roleId = 2L;
		final List<RoleAndFunctionRelationEntity> list = new ArrayList<RoleAndFunctionRelationEntity>();
		list.add(new RoleAndFunctionRelationEntity());

		mockery.checking(new Expectations() {
			{
				oneOf(roleAndFunctionRelationDao).queryByRoleId(roleId);
				will(returnValue(list));
			}
		});
		service.queryByRoleId(roleId);

	}

	@Test
	public void testQueryByFunctionId() {
		final Long functionId = 2L;
		final List<RoleAndFunctionRelationEntity> list = new ArrayList<RoleAndFunctionRelationEntity>();
		list.add(new RoleAndFunctionRelationEntity());

		mockery.checking(new Expectations() {
			{
				oneOf(roleAndFunctionRelationDao).queryByFunctionId(functionId);
				will(returnValue(list));
			}
		});
		service.queryByFunctionId(functionId);
	}

	@Test
	public void testQueryByRoleIdAndFunctionId() {
		Long roleId = 1L;
		Long functionId = 2L;
		final RoleAndFunctionRelationEntity roleAndFunctionRelation = new RoleAndFunctionRelationEntity();
		roleAndFunctionRelation.setFunctionId(functionId);
		roleAndFunctionRelation.setRoleId(roleId);
		mockery.checking(new Expectations() {
			{
				oneOf(roleAndFunctionRelationDao).queryByRelation(with(any(RoleAndFunctionRelationEntity.class)));
				will(returnValue(roleAndFunctionRelation));
			}
		});
		service.queryByRoleIdAndFunctionId(roleId, functionId);
	}

	@Test
	public void testDeleteRoleAndFunctionRelation() {
		Long roleId = 1L;
		Long functionId = 2L;
		final RoleAndFunctionRelationEntity roleAndFunctionRelation = new RoleAndFunctionRelationEntity();
		roleAndFunctionRelation.setFunctionId(functionId);
		roleAndFunctionRelation.setRoleId(roleId);
		mockery.checking(new Expectations() {
			{
				oneOf(roleAndFunctionRelationDao).delete(with(any(Long.class)));
			}
		});
		service.deleteRoleAndFunctionRelation(roleId, functionId);
	}

}

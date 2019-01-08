/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.authority.service;

import com.zbjdl.boss.admin.BaseServiceTest;
import com.zbjdl.boss.admin.authority.convert.impl.RoleConvert;
import com.zbjdl.boss.admin.authority.entity.RoleEntity;
import com.zbjdl.boss.admin.authority.service.impl.RoleServiceImpl;
import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.repository.RoleDao;
import com.zbjdl.boss.admin.role.dto.RoleDTO;
import com.zbjdl.boss.admin.role.enums.RoleStatusEnum;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：RoleServiceImplTest <br>
 * 类描述：   <br>
 *
 * @author：feng
 * @since：2011-7-7 上午11:32:37
 * @version:
 */
public class RoleServiceImplTest extends BaseServiceTest {

	private RoleServiceImpl service = new RoleServiceImpl();

	private RoleDao roleDao = mockery.mock(RoleDao.class);

	private Convert<RoleDTO, RoleEntity> roleConvert = mockery.mock(Convert.class);

	@Before
	public void before() {
		service.setRoleDao(roleDao);
		service.setRoleConvert(roleConvert);
	}

	@Test
	public void testCreateRole() {
		final RoleDTO roleDTO = new RoleDTO();
		roleDTO.setRoleName("业管操作员");
		roleDTO.setRoleStatus(RoleStatusEnum.ACTIVE);
		roleDTO.setDescription("测试");
		final RoleEntity roleEntity = new RoleConvert().convert(roleDTO);
		mockery.checking(new Expectations() {
			{
				oneOf(roleConvert).convert(roleDTO);
				will(returnValue(roleEntity));
				oneOf(roleDao).save(roleEntity);
			}
		});
		service.createRole(roleDTO);
	}

	@Test
	public void testDeleteRole() {
		final Long roleId = 1L;
		mockery.checking(new Expectations() {
			{
				oneOf(roleDao).delete(roleId);
			}
		});
		service.deleteRole(roleId);
	}

	@Test
	public void testUpdateRole() {
		final RoleDTO roleDTO = new RoleDTO();
		roleDTO.setRoleName("业管操作员12");
		roleDTO.setRoleStatus(RoleStatusEnum.ACTIVE);
		roleDTO.setDescription("测试2323");
		final RoleEntity roleEntity = new RoleConvert().convert(roleDTO);
		mockery.checking(new Expectations() {
			{
				oneOf(roleConvert).convert(roleDTO);
				will(returnValue(roleEntity));
				oneOf(roleDao).update(roleEntity);
			}
		});
		service.updateRole(roleDTO);
	}

	@Test
	public void testQueryRoleById() {
		final Long roleId = 1L;
		final RoleEntity role = new RoleEntity();
		role.setRoleId(roleId);
		role.setRoleName("结算");
		role.setDescription("测试");
		role.setRoleStatus(RoleStatusEnum.ACTIVE);
		mockery.checking(new Expectations() {
			{
				oneOf(roleDao).selectById(roleId);
				will(returnValue(role));
			}
		});
		RoleDTO role1 = service.queryRoleById(roleId);
		Assert.assertEquals(role.getRoleId(), role1.getRoleId());

	}

	@Test
	public void testQueryRole() {
		final RoleDTO roleDTO = new RoleDTO();
		roleDTO.setRoleName("业管操作员");
		roleDTO.setRoleStatus(RoleStatusEnum.ACTIVE);
		roleDTO.setDescription("测试");
		final RoleEntity roleEntity = new RoleConvert().convert(roleDTO);
		final List<RoleEntity> list = new ArrayList<RoleEntity>();
		list.add(roleEntity);
		mockery.checking(new Expectations() {
			{
				oneOf(roleConvert).convert(roleDTO);
				will(returnValue(roleEntity));
				oneOf(roleDao).queryByRole(roleEntity);
				will(returnValue(list));
			}
		});
		service.queryRole(roleDTO);
		Assert.assertTrue(list.size() != 0 && list.get(0).getRoleName().equals("业管操作员"));
	}

	@Test
	public void testActivateRole() {
		final Long roleId = 1L;
		final RoleEntity role = new RoleEntity();
		role.setId(roleId);
		role.setRoleId(roleId);
		role.setDescription("测试");
		role.setRoleName("业管");
		role.setRoleStatus(RoleStatusEnum.ACTIVE);
		mockery.checking(new Expectations() {
			{
				oneOf(roleDao).selectById(roleId);
				will(returnValue(role));

				oneOf(roleDao).update(role);
			}
		});
		service.activateRole(roleId);
	}

	@Test
	public void testFrozenRole() {
		final Long roleId = 1L;
		final RoleEntity role = new RoleEntity();
		role.setId(roleId);
		role.setRoleId(roleId);
		role.setDescription("测试");
		role.setRoleName("业管");
		role.setRoleStatus(RoleStatusEnum.FROZEN);
		mockery.checking(new Expectations() {
			{
				oneOf(roleDao).selectById(roleId);
				will(returnValue(role));

				oneOf(roleDao).update(role);
			}
		});
		service.frozenRole(roleId);
	}

	@Test
	public void testForbidRole() {
		final Long roleId = 1L;
		final RoleEntity role = new RoleEntity();
		role.setId(roleId);
		role.setRoleId(roleId);
		role.setDescription("测试");
		role.setRoleName("业管");
		role.setRoleStatus(RoleStatusEnum.FORBID);
		mockery.checking(new Expectations() {
			{
				oneOf(roleDao).selectById(roleId);
				will(returnValue(role));

				oneOf(roleDao).update(role);
			}
		});
		service.forbidRole(roleId);
	}

}

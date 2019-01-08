/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.service;

import com.google.common.collect.Maps;
import com.zbjdl.boss.admin.BaseServiceTest;
import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.repository.UserDao;
import com.zbjdl.boss.admin.repository.UserStatusUpdateRecordDao;
import com.zbjdl.boss.admin.user.convert.impl.UserConvert;
import com.zbjdl.boss.admin.user.dto.ModifyPasswordDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.entity.UserEntity;
import com.zbjdl.boss.admin.user.entity.UserStatusUpdateRecordEntity;
import com.zbjdl.boss.admin.user.enums.UserInfoEnum;
import com.zbjdl.boss.admin.user.enums.UserStatusEnum;
import com.zbjdl.boss.admin.user.service.impl.UserServiceImpl;
import com.zbjdl.boss.admin.utils.StringBooleanUtil;

import org.jmock.Expectations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称：UserServiceImplTest <br>
 * 类描述：   <br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-6-28 下午01:41:28
 */
public class UserServiceImplTest extends BaseServiceTest {

	private UserServiceImpl service = new UserServiceImpl();

	private UserDao userDao = mockery.mock(UserDao.class);

	private UserStatusUpdateRecordDao userStatusUpdateRecordDao =
			mockery.mock(UserStatusUpdateRecordDao.class);

	private Convert<UserDTO, UserEntity> userConvert = mockery.mock(Convert.class);

	@Before
	public void before() {
		service.setUserDao(userDao);
		service.setUserStatusUpdateRecordDao(userStatusUpdateRecordDao);
		service.setUserConvert(userConvert);
	}

	@Test
	public void testCreateUser() {
		final UserDTO userDTO = new UserDTO();
		userDTO.setUserName("贾英哲");
		userDTO.setIsAdmin(true);
		userDTO.setIsSuperAdmin(false);
		userDTO.setLoginName("贾英哲");
		userDTO.setPassword("123qwe");
		userDTO.setPrimaryDepartmentId(1L);
		final UserEntity userEntity = (new UserConvert()).convert(userDTO);
		mockery.checking(new Expectations() {
			{
				oneOf(userConvert).convert(userDTO);
				will(returnValue(userEntity));
				oneOf(userDao).save(userEntity);
			}
		});
		Long userId = service.createUser(userDTO);
		mockery.assertIsSatisfied();
	}

	@Test
	public void testDeleteUser() {
		final Long userId = 123L;
		mockery.checking(new Expectations() {
			{
				oneOf(userDao).delete(userId);
			}
		});
		boolean b = service.deleteUser(userId);
		Assert.assertTrue(b);
	}

	@Test
	public void testFrozenUser() {
		final Long userId = 2L;
		final String frozenReason = "测试";
		final Long adminUserId = 1L;
		mockery.checking(new Expectations() {
			{
				oneOf(userDao).selectById(userId);
				will(returnValue(with(any(UserEntity.class))));

				oneOf(userDao).update(with(any(UserEntity.class)));
				oneOf(userStatusUpdateRecordDao).save(with(any(UserStatusUpdateRecordEntity.class)));
			}
		});
		boolean b = service.frozenUser(userId, frozenReason, adminUserId);
		Assert.assertTrue(!b);
	}

	@Test
	public void testActivateUser() {
		final Long userId = 1L;
		final String activateReason = "测试";
		final Long adminUserId = 1L;
		mockery.checking(new Expectations() {
			{
				oneOf(userDao).selectById(userId);
				will(returnValue(with(any(UserEntity.class))));

				oneOf(userDao).update(with(any(UserEntity.class)));
				oneOf(userStatusUpdateRecordDao).save(with(any(UserStatusUpdateRecordEntity.class)));
			}
		});
		boolean b = service.activateUser(userId, activateReason, adminUserId);
		Assert.assertTrue(!b);
	}

	@Test
	public void testForbidUser() {
		final Long userId = 1L;
		final String forbidReason = "测试";
		final Long adminUserId = 1234L;
		mockery.checking(new Expectations() {
			{
				oneOf(userDao).selectById(userId);
				will(returnValue(with(any(UserEntity.class))));

				oneOf(userDao).update(with(any(UserEntity.class)));
				oneOf(userStatusUpdateRecordDao).save(with(any(UserStatusUpdateRecordEntity.class)));
			}
		});
		boolean b = service.forbidUser(userId, forbidReason, adminUserId);
		Assert.assertTrue(!b);
	}

	@Test
	public void testResetPassword() {
		final Long userId = 1234312L;
		String resetReason = "测试";
		final UserEntity user = new UserEntity();
		user.setIsAdmin(StringBooleanUtil.booleanToString(true));
		user.setUserName("贾英哲");
		user.setIsSuperAdmin(StringBooleanUtil.booleanToString(false));
		user.setLoginName("feng");
		user.setPassword("jlkausoidhgnalksdug");
		user.setPrimaryDepartmentId(1L);
		user.setPwdErrorNums(0);
		user.setUserStatus(UserStatusEnum.ACTIVE);
		mockery.checking(new Expectations() {
			{
				oneOf(userDao).selectById(userId);
				will(returnValue(user));
				oneOf(userDao).update(user);
			}
		});
		boolean b = service.resetPassword(userId, "123qwe", resetReason);
		Assert.assertTrue(b);
	}

	@Test
	public void testUpdatePassword() {
		final ModifyPasswordDTO modifyPasswordDTO = new ModifyPasswordDTO();
		modifyPasswordDTO.setLoginName("贾英哲");
		modifyPasswordDTO.setOriginalPassword("123qwe");
		modifyPasswordDTO.setNewPassword("123reqw");
		modifyPasswordDTO.setConfirmPassword("123reqw");

		final UserEntity user = new UserEntity();
		user.setIsAdmin(StringBooleanUtil.booleanToString(true));
		user.setUserName("贾英哲");
		user.setIsSuperAdmin(StringBooleanUtil.booleanToString(false));
		user.setLoginName("feng");
		user.setPassword("46f94c8de14fb36680850768ff1b7f2a");
		user.setPrimaryDepartmentId(1L);
		user.setPwdErrorNums(0);
		user.setUserStatus(UserStatusEnum.ACTIVE);

		mockery.checking(new Expectations() {
			{
				oneOf(userDao).queryUserByLoginName(modifyPasswordDTO.getLoginName());
				will(returnValue(user));

				oneOf(userDao).update(with(any(UserEntity.class)));
			}
		});
		boolean b = service.updatePassword(modifyPasswordDTO);
		Assert.assertTrue(b);
	}

	@Test
	public void testUpdateUserInfo() {
		Long userId = 123L;
		final UserDTO userDTO = new UserDTO();
		userDTO.setUserName("贾英哲");
		userDTO.setIsAdmin(true);
		userDTO.setIsSuperAdmin(false);
		userDTO.setLoginName("贾英哲");
		userDTO.setPassword("123qwe");
		userDTO.setPrimaryDepartmentId(1L);
		final UserEntity userEntity = (new UserConvert()).convert(userDTO);
		final String s = "updateUser";
		mockery.checking(new Expectations() {
			{
				oneOf(userConvert).convert(userDTO);
				will(returnValue(userEntity));
				oneOf(userDao).update(userEntity);
			}
		});
		boolean b = service.updateUserInfo(userId, userDTO);
		Assert.assertTrue(b);
	}

	@Test
	public void testValidateUserExistOrnot() {
		final UserDTO userDTO = new UserDTO();
		userDTO.setUserName("贾英哲");
		userDTO.setIsAdmin(true);
		userDTO.setIsSuperAdmin(false);
		userDTO.setLoginName("贾英哲");
		userDTO.setPassword("123qwe");
		userDTO.setPrimaryDepartmentId(1L);
		final UserEntity u = new UserConvert().convert(userDTO);
		final List<UserEntity> list = new ArrayList<UserEntity>();
		list.add(u);
		mockery.checking(new Expectations() {
			{
				oneOf(userConvert).convert(userDTO);
				will(returnValue(u));

				oneOf(userDao).findList( u);
				will(returnValue(list));
			}
		});

		boolean b = service.validateUserExistOrnot(userDTO);
		Assert.assertTrue(b);
	}

	@Test
	public void testValidateUserExistById() {
		final Long userId = 1L;
		mockery.checking(new Expectations() {
			{
				oneOf(userDao).selectById(userId);
				will(returnValue(with(any(UserEntity.class))));
			}
		});
		boolean b = service.validateUserExistById(userId);
		Assert.assertTrue(b);
	}

	@Test
	public void testValidateUserToAdministrator() {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName("贾英哲");
		userDTO.setIsAdmin(true);
		userDTO.setIsSuperAdmin(false);
		userDTO.setLoginName("贾英哲");
		userDTO.setPassword("123qwe");
		userDTO.setPrimaryDepartmentId(1L);

		final Long adminUserId = 1L;
		mockery.checking(new Expectations() {
			{
				oneOf(userDao).selectById(adminUserId);
				will(returnValue(with(any(UserEntity.class))));
			}
		});

		boolean b = service.validateUserToAdministrator(userDTO, adminUserId);
		Assert.assertTrue(b);
	}

	@Test
	public void testUserLoginValidate() {
		final String loginName = "zhangyong";
		String password = "123qwe";

		mockery.checking(new Expectations() {

			{
				oneOf(userDao).queryUserByLoginName(with(any(String.class)));
				will(returnValue(with(any(UserEntity.class))));
			}
		});

		UserDTO userDTO = service.userLoginValidate(loginName, password);
		Assert.assertNull(userDTO);
	}

	@Test
	public void testUserAuthentication() {
		final Long userId = 123L;
		String password = "123qwe";
		mockery.checking(new Expectations() {

			{
				oneOf(userDao).selectById(userId);
				will(returnValue(with(any(UserEntity.class))));
			}
		});

		boolean b = service.userAuthentication(userId, password);
		Assert.assertTrue(!b);
	}

	@Test
	public void testQueryUserById() {
		final Long userId = 123L;

		mockery.checking(new Expectations() {
			{
				oneOf(userDao).selectById(userId);
				will(returnValue(with(any(UserEntity.class))));
			}
		});

		UserDTO userEntity = service.queryUserById(userId);
		Assert.assertTrue(userEntity == null);
	}

	@Test
	public void testQueryUser() {
		final UserDTO userDTO = new UserDTO();
		userDTO.setUserName("贾英哲");
		userDTO.setIsAdmin(true);
		userDTO.setIsSuperAdmin(false);
		userDTO.setLoginName("贾英哲");
		userDTO.setPassword("123qwe");
		userDTO.setPrimaryDepartmentId(1L);
		final List<UserEntity> list = new ArrayList<UserEntity>();
		final UserEntity u = (new UserConvert()).convert(userDTO);
		list.add(u);
		mockery.checking(new Expectations() {
			{
				oneOf(userConvert).convert(userDTO);
				will(returnValue(u));
				oneOf(userDao).findList(u);
				will(returnValue(list));
			}
		});

		List<UserDTO> list1 = service.queryUser(userDTO);
		Assert.assertTrue(list.size() == list1.size());
	}

	@Test
	public void testUpdateUserPrimaryId() {
		Long userId = 123124L;
		Long departmentId = 45654l;
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("departmentId", departmentId);

		mockery.checking(new Expectations() {
			{
				oneOf(userDao).updateUserPrimaryDepartmentId(map);
			}
		});

		boolean b = service.updateUserPrimaryDepartmentId(userId, departmentId);
		Assert.assertTrue(b);
	}

	@Test
	public void testValidateUserIsSuperAdmin() {
		final Long adminUserId = 1L;
		mockery.checking(new Expectations() {
			{
				oneOf(userDao).selectById(adminUserId);
				will(returnValue(with(any(UserEntity.class))));
			}
		});

		boolean b = service.validateUserIsSuperAdmin(adminUserId);
		Assert.assertTrue(b);
	}

}

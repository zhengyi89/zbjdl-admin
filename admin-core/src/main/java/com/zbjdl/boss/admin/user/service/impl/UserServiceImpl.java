/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.service.impl;

import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.repository.UserDao;
import com.zbjdl.boss.admin.repository.UserStatusUpdateRecordDao;
import com.zbjdl.boss.admin.user.dto.ModifyPasswordDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.entity.UserEntity;
import com.zbjdl.boss.admin.user.entity.UserStatusUpdateRecordEntity;
import com.zbjdl.boss.admin.user.enums.UserInfoEnum;
import com.zbjdl.boss.admin.user.enums.UserStatusEnum;
import com.zbjdl.boss.admin.user.exception.UserAuthenticationException;
import com.zbjdl.boss.admin.user.service.UserService;
import com.zbjdl.boss.admin.user.utils.PasswordUtil;
import com.zbjdl.boss.admin.utils.StringBooleanUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称：UserServiceImpl <br>
 * 类描述：   <br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-6-22 下午03:02:24
 */
public class UserServiceImpl implements UserService {

	private static final Log logger = LogFactory.getLog(UserServiceImpl.class.getName());

	private UserDao userDao;

	private UserStatusUpdateRecordDao userStatusUpdateRecordDao;

	private Convert<UserDTO, UserEntity> userConvert;

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#createUser(com.zbjdl.boss.admin.user.dto.UserDTO)    
	 */
	@Override
	public Long createUser(UserDTO userDto) {
		UserEntity userEntity = userConvert.convert(userDto);
		userEntity.setCreateTime(new Date());
		userEntity.setPassword(PasswordUtil.encodePassword(userDto.getPassword(), userDto.isMigration()));
		userDao.save(userEntity);
		return userEntity.getUserId();
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#deleteUser(java.lang.Long,java.lang.String)    
	 */
	@Override
	public boolean deleteUser(Long userId) {
		userDao.delete(userId);
		return true;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#frozenUser(java.lang.Long, java.lang.String)    
	 */
	@Override
	public boolean frozenUser(Long userId, String frozenReason, Long adminUserId) {
		UserEntity userEntity = userDao.selectById(userId);
		if (userEntity != null) {
			UserStatusUpdateRecordEntity usur = new UserStatusUpdateRecordEntity();
			if (adminUserId == null) {
				usur.setAdminUserId(null);
			} else {
				usur.setAdminUserId(adminUserId);
			}
			//同时更新用户状态更新记录
			usur.setPreStatus(userEntity.getUserStatus());
			usur.setCurrentStatus(UserStatusEnum.FROZEN);
			usur.setUpdateDate(new Date());
			usur.setUpdateReason(frozenReason);
			usur.setUserId(userId);
			userEntity.setUserStatus(UserStatusEnum.FROZEN);
			userDao.update(userEntity);
			userStatusUpdateRecordDao.save(usur);
			return true;
		} else {
			return false;
		}

	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#activateUser(java.lang.Long, java.lang.String)    
	 */
	@Override
	public boolean activateUser(Long userId, String activateReason, Long adminUserId) {
		UserEntity userEntity = userDao.selectById(userId);
		if (userEntity != null) {
			UserStatusUpdateRecordEntity usur = new UserStatusUpdateRecordEntity();
			usur.setAdminUserId(adminUserId);
			usur.setPreStatus(userEntity.getUserStatus());
			usur.setCurrentStatus(UserStatusEnum.ACTIVE);
			usur.setUpdateDate(new Date());
			usur.setUpdateReason(activateReason);
			usur.setUserId(userId);

			userEntity.setUserStatus(UserStatusEnum.ACTIVE);
			userDao.update(userEntity);
			userStatusUpdateRecordDao.save(usur);
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#forbidUser(java.lang.Long, java.lang.String)    
	 */
	@Override
	public boolean forbidUser(Long userId, String forbidReason, Long adminUserId) {
		UserEntity userEntity = userDao.selectById(userId);
		if (userEntity != null) {
			UserStatusUpdateRecordEntity usur = new UserStatusUpdateRecordEntity();
			usur.setAdminUserId(adminUserId);
			usur.setPreStatus(userEntity.getUserStatus());
			usur.setCurrentStatus(UserStatusEnum.FORBIDDEN);
			usur.setUpdateDate(new Date());
			usur.setUpdateReason(forbidReason);
			usur.setUserId(userId);

			userEntity.setUserStatus(UserStatusEnum.FORBIDDEN);
			userDao.update(userEntity);
			userStatusUpdateRecordDao.save(usur);
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#resetPassword(java.lang.Long, java.lang.String, java.lang.String)    
	 */
	@Override
	public boolean resetPassword(Long userId, String password, String resetReason) {
		UserEntity user = userDao.selectById(userId);
		user.setPassword(PasswordUtil.encodePassword(password,
				StringBooleanUtil.stringToBoolean(user.getMigration())));// 密码加密方式调整
		userDao.update(user);
		return true;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#updatePassword(com.zbjdl.boss.admin.user.dto.ModifyPasswordDTO)    
	 */
	@Override
	public boolean updatePassword(ModifyPasswordDTO modifyPasswordDTO) {
		UserEntity user = (UserEntity) userDao.queryUserByLoginName(modifyPasswordDTO.getLoginName());
		//验证当前密码是否正确
		if (!PasswordUtil.validatePassword(user.getPassword(),
				modifyPasswordDTO.getOriginalPassword(),
				StringBooleanUtil.stringToBoolean(user.getMigration()))) { // 密码加密方式调整
			return false;
		}
		if (user != null
				&& modifyPasswordDTO.getNewPassword().equals(
				modifyPasswordDTO.getConfirmPassword())) {
			user.setPassword(PasswordUtil.encodePassword(
					modifyPasswordDTO.getNewPassword(),
					StringBooleanUtil.stringToBoolean(user.getMigration())));
			user.setPwdLastModifiedTime(new Date());
			userDao.update(user);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#updateUserInfo(java.lang.Long, com.zbjdl.boss.admin.user.dto.UserDTO)    
	 */
	@Override
	public boolean updateUserInfo(Long userId, UserDTO userDto) {
		UserEntity userEntity = userConvert.convert(userDto);
		userEntity.setUserId(userId);
		userDao.updateUser(userEntity);
		return true;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#validateUserExistOrnot(com.zbjdl.boss.admin.user.dto.UserDTO)    
	 */
	@Override
	public boolean validateUserExistOrnot(UserDTO userDto) {
		UserEntity userEntity = new UserEntity();
		userEntity.setLoginName(userDto.getLoginName());
		@SuppressWarnings("unchecked")
		List<UserEntity> list = userDao.findList(userEntity);
		if (list == null || list.size() == 0) {
			return false;
		} else {
			for (UserEntity user : list) {
				if (user.getUserStatus() != UserStatusEnum.FORBIDDEN) {
					return true;
				}
			}
			return false;
		}
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#validateUserExistById(java.lang.Long)    
	 */
	@Override
	public boolean validateUserExistById(Long userId) {
		UserEntity userEntity = userDao.selectById(userId);
		if (userEntity == null) {
			return true;
		} else {
			return false;
		}

	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#validateUserToAdministrator(com.zbjdl.boss.admin.user.dto.UserDTO)    
	 */
	@Override
	public boolean validateUserToAdministrator(UserDTO userDto, Long adminUserId) {
		UserEntity adminUser = userDao.selectById(adminUserId);
		//如果是系管员直接返回true
		if (adminUser != null && StringBooleanUtil.stringToBoolean(adminUser.getIsSuperAdmin()) == true) {
			return true;
		}
		if (adminUser != null && userDto.getPrimaryDepartmentId().equals(adminUser.getPrimaryDepartmentId())) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#validateUserInfo(com.zbjdl.boss.admin.user.dto.UserDTO)    
	 */
	@Override
	public UserInfoEnum validateUserInfo(UserDTO userDto) {
		String loginName = userDto.getLoginName();
		UserEntity userEntity = (UserEntity) userDao.queryUserByLoginName(loginName);
		if (userEntity != null) {
			return UserInfoEnum.USERNAMEEXISTED;
		}
		return UserInfoEnum.USERINFOCORRECT;
	}

	@Override
	public UserDTO userLoginValidate(String loginName, String password) throws UserAuthenticationException {
		UserEntity userEntity = (UserEntity) userDao.queryUserByLoginName(loginName);
		if (userEntity != null) {
			if (UserStatusEnum.ACTIVE.equals(userEntity.getUserStatus())) {
				// TODO 密码验证方式修改
				boolean b = PasswordUtil.validatePassword(userEntity
						.getPassword(), password, StringBooleanUtil
						.stringToBoolean(userEntity.getMigration()));
				if (b) {
					//如果之前有登录错误，验证成功次数清零
					if (userEntity.getPwdErrorNums() != 0) {
						userEntity.setPwdErrorNums(0);
						userDao.update(userEntity);
					}
					return userConvert.convert(userEntity);
				} else {

					// 客户提的bug
//					userEntity.setPwdErrorNums(userEntity.getPwdErrorNums()+1);
//					if(userEntity.getPwdErrorNums() >= 3){
//						UserStatusUpdateRecordEntity usur = new UserStatusUpdateRecordEntity();
//						usur.setPreStatus(userEntity.getUserStatus());
//						usur.setCurrentStatus(UserStatusEnum.FROZEN);
//						usur.setUpdateDate(new Date());
//						usur.setUpdateReason("连续登录三次错误,系统自动冻结!");
//						usur.setUserId(userEntity.getUserId());
//						userEntity.setUserStatus(UserStatusEnum.FROZEN);
//						userStatusUpdateRecordDao.save(usur);
//					}
//					userDao.update(userEntity);
					throw UserAuthenticationException.USERAUTHEN_FAIL_EXCEPTION;
				}
			} else {
				throw UserAuthenticationException.USER_FROZEN_EXCEPTION;
			}
		} else {
			throw UserAuthenticationException.USER_NOEXIST_EXCEPTION;
		}

	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#userAuthentication(java.lang.Long, java.lang.String)    
	 */
	@Override
	public boolean userAuthentication(Long userId, String password) {
//		UserEntity userEntity = userDao.selectById(userId);
//		if(userEntity != null){
//			boolean b = PasswordUtil.validatePassword(userEntity.getPassword(), password);
//			if(b && (StringBooleanUtil.stringToBoolean(userEntity.getIsAdmin()) || StringBooleanUtil.stringToBoolean(userEntity.getIsSuperAdmin()))){
//				return true;
//			}else{
//				return false;
//			}
//		}else{
//			return false;
//		}

//		@SuppressWarnings("unchecked")
//		List<UserEntity> userEntityList = userDao.selectById(userId);
//		if (userEntityList == null || userEntityList.isEmpty()) {
//			return false;
//		}
		UserEntity userEntity = userDao.selectById(userId);
		if (userEntity != null) {
			if (UserStatusEnum.ACTIVE.equals(userEntity.getUserStatus())) {
				boolean b = PasswordUtil.validatePassword(userEntity
						.getPassword(), password, StringBooleanUtil
						.stringToBoolean(userEntity.getMigration()));
				if (b == true) {
					//如果之前有登录错误，验证成功次数清零
					if (userEntity.getPwdErrorNums() != 0) {
						userEntity.setPwdErrorNums(0);
						userDao.update(userEntity);
					}
					return true;
				} else {

					return false;
				}
			} else {
				return false;
			}

		} else {
			return false;
		}

	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#queryUserById(java.lang.Long)    
	 */
	@Override
	public UserDTO queryUserById(Long userId) {
		return userConvert.convert((UserEntity)userDao.selectById(userId));
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#queryUser(com.zbjdl.boss.admin.user.dto.UserDTO)    
	 */
	@Override
	public List<UserDTO> queryUser(UserDTO userDTO) {
		UserEntity userEntity = userConvert.convert(userDTO);
		@SuppressWarnings("unchecked")
		List<UserEntity> list = userDao.findList(userEntity);
		return userConvert.convertToDtos(list);
	}

	@Override
	public boolean updateUserPrimaryDepartmentId(Long userId, Long departmentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("departmentId", departmentId);
		try {
			userDao.updateUserPrimaryDepartmentId(map);
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	@Override
	public boolean migrateUser(Long departmentId, Long newDepartmentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("departmentId", departmentId);
		map.put("newDepartmentId", newDepartmentId);
		try {
			userDao.migrateUser(map);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean batchDeleteUserByDepartmentId(Long departmentId) {
		userDao.batchDeleteUserRelationByDepartmentId(departmentId);// 删除用户-角色关系
		userDao.batchDeleteUserByDepartmentId(departmentId);        // 删除用户
		return true;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#createAdminUser(com.zbjdl.boss.admin.user.dto.UserDTO, java.lang.Long)    
	 */
	@Override
	public boolean validateUserIsSuperAdmin(Long adminUserId) {
		UserEntity superAdminUser = userDao.selectById(adminUserId);
		//不是超级管理员不可创建
		if (superAdminUser == null) {
			return false;
		}
		if (StringBooleanUtil.stringToBoolean(superAdminUser.getIsSuperAdmin())) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#validateUserToAdministrator(java.lang.Long, java.lang.Long)    
	 */
	@Override
	public boolean validateUserToAdministrator(Long userId, Long adminUserId) {
		UserEntity adminUser = userDao.selectById(adminUserId);
		UserEntity user = userDao.selectById(userId);
		//如果是系管员直接返回true
		if (adminUser != null && StringBooleanUtil.stringToBoolean(adminUser.getIsSuperAdmin()) == true) {
			return true;
		}
		if (adminUser != null && user.getPrimaryDepartmentId().equals(adminUser.getPrimaryDepartmentId())) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#queryAllUser()    
	 */
	@Override
	public List<UserDTO> queryAllUser() {
		List<UserEntity> list = userDao.findAll();
		return userConvert.convertToDtos(list);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#querySuperAdmin(java.lang.String)    
	 */
	@Override
	public List<UserDTO> querySuperAdmin() {
		UserEntity userEntity = new UserEntity();
		userEntity.setIsSuperAdmin(StringBooleanUtil.booleanToString(true));
		List<UserEntity> list = userDao.findList(userEntity);
		return userConvert.convertToDtos(list);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#validateSuperAdmin(java.lang.Long, java.lang.String)    
	 */
	@Override
	public boolean validateSuperAdmin(Long userId, String password) {
		UserEntity userEntity = userDao.selectById(userId);
		if (userEntity != null) {
			boolean b = PasswordUtil.validatePassword(userEntity.getPassword(),
					password, StringBooleanUtil.stringToBoolean(userEntity
					.getMigration()));
			if (b == true && StringBooleanUtil.stringToBoolean(userEntity.getIsSuperAdmin())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 描述： 注入用户DAO
	 *
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 描述：  注入用户状态更改记录DAO
	 *
	 * @param userStatusUpdateRecordDao
	 */
	public void setUserStatusUpdateRecordDao(
			UserStatusUpdateRecordDao userStatusUpdateRecordDao) {
		this.userStatusUpdateRecordDao = userStatusUpdateRecordDao;
	}

	/**
	 * 描述： 注入用户数据转换器
	 *
	 * @param userConvert the userConvert to set
	 */
	public void setUserConvert(Convert<UserDTO, UserEntity> userConvert) {
		this.userConvert = userConvert;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#queryUserByLoginName(java.lang.String)    
	 */
	@Override
	public UserDTO queryUserByLoginName(String loginName) {
		UserEntity user = (UserEntity) userDao.queryUserByLoginName(loginName);
		return userConvert.convert(user);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.UserService#validatePasswordByLoginName(java.lang.String, java.lang.String)    
	 */
	@Override
	public boolean validatePasswordByLoginName(String loginName, String password) {
		UserEntity user = (UserEntity) userDao.queryUserByLoginName(loginName);
		if (user == null) {
			return false;
		} else {
			return PasswordUtil.validatePassword(user.getPassword(), password,
					StringBooleanUtil.stringToBoolean(user.getMigration()));

		}
	}
	@Override
	public UserDTO userLoginValidateByMobile(String mobile, String password) {
		UserEntity userEntity = (UserEntity) userDao.queryUserByMobile(mobile);
		if (userEntity != null) {
			if (UserStatusEnum.ACTIVE.equals(userEntity.getUserStatus())) {
				// TODO 密码验证方式修改
				boolean b = PasswordUtil.validatePassword(userEntity
						.getPassword(), password, StringBooleanUtil
						.stringToBoolean(userEntity.getMigration()));
				if (b) {
					//如果之前有登录错误，验证成功次数清零
					if (userEntity.getPwdErrorNums() != 0) {
						userEntity.setPwdErrorNums(0);
						userDao.update(userEntity);
					}
					return userConvert.convert(userEntity);
				} else {
					throw UserAuthenticationException.USERAUTHEN_FAIL_EXCEPTION;
				}
			} else {
				throw UserAuthenticationException.USER_FROZEN_EXCEPTION;
			}
		} else {
			throw UserAuthenticationException.USER_NOEXIST_EXCEPTION;
		}
	}


}

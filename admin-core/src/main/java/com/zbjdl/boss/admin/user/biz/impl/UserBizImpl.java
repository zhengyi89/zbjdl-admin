/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.biz.impl;

import com.zbjdl.boss.admin.biz.UserBiz;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.ModifyPasswordDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.UserStatusEnum;
import com.zbjdl.boss.admin.user.exception.ModifyPasswordException;
import com.zbjdl.boss.admin.user.exception.UserAuthenticationException;
import com.zbjdl.boss.admin.user.exception.UserException;
import com.zbjdl.boss.admin.user.service.DepartmentService;
import com.zbjdl.boss.admin.user.service.UserService;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * 类名称：UserBizImpl <br>
 * 类描述： <br>
 * 
 * @author feng
 * @since 2011-6-22 下午02:59:44
 * @version 1.0.0
 * 
 */

public class UserBizImpl implements UserBiz {

	private UserService userService;

	private DepartmentService departmentService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserBizImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.UserBiz#userLoginValidate(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public UserDTO userLoginValidate(String loginName, String password)
			throws UserAuthenticationException {
		return userService.userLoginValidate(loginName, password);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.UserBiz#userAuthentication(java.lang.
	 * Long, java.lang.String)
	 */
	@Override
	@Transactional
	public boolean userAuthentication(Long userId, String password)
			throws UserAuthenticationException {
		return userService.userAuthentication(userId, password);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.UserBiz#queryUserById(java.lang.Long)
	 */
	@Override
	@Transactional
	public UserDTO queryUserById(Long userId) {
		return userService.queryUserById(userId);
	}

	
	@Override
	@Transactional
	public List<UserDTO> queryUser(UserDTO userDTO) {
		return userService.queryUser(userDTO);
	}

	@Override
	public UserDTO queryUserByLoginName(String loginName) {
		return userService.queryUserByLoginName(loginName);
	}

	
	@Override
	@Transactional
	public boolean updatePassword(ModifyPasswordDTO modifyPasswordDTO)
			throws ModifyPasswordException {
		boolean passwordValid = userService.validatePasswordByLoginName(
				modifyPasswordDTO.getLoginName(),
				modifyPasswordDTO.getOriginalPassword());
		if (passwordValid) {
			boolean ret = userService.updatePassword(modifyPasswordDTO);

			return ret;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.UserBiz#queryDepartmentById(java.lang
	 * .Long)
	 */
	@Override
	@Transactional
	public DepartmentDTO queryDepartmentById(Long departmentId) {
		return departmentService.queryDepartmentById(departmentId);
	}


	@Override
	@Transactional
	public List<DepartmentDTO> queryDepartments(DepartmentDTO departmentDTO) {
		return departmentService.queryDepartments(departmentDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.boss.admin.biz.UserBiz#queryAllDepartments()
	 */
	@Override
	@Transactional
	public List<DepartmentDTO> queryAllDepartments() {
		return departmentService.queryAllDepartments();
	}

	/**
	 * 描述： 注入用户Service
	 * 
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 描述： 注入部门Service
	 * 
	 * @param departmentService
	 *            the departmentService to set
	 */
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.UserBiz#queryDepartmentUsers(java.lang
	 * .Long)
	 */
	@Override
	public List<UserDTO> queryDepartmentUsers(Long departmentId) {
		UserDTO query = new UserDTO();
		query.setPrimaryDepartmentId(departmentId);
		return userService.queryUser(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.UserBiz#validateUserStatus(java.lang.
	 * String)
	 */
	@Override
	public boolean validateUserStatus(String userName) {
		UserDTO user = userService.queryUserByLoginName(userName);
		if (user == null) {
			throw UserException.USER_NOTEXIST_EXCEPTION;
		}

        return user.getUserstatus().equals(UserStatusEnum.ACTIVE);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.biz.UserBiz#updateUserInfo(com.zbjdl.boss.admin.user.dto.UserDTO)    
	 */
	@Override
	@Transactional
	public void updateUserInfo(UserDTO userDTO) {
		userService.updateUserInfo(userDTO.getUserId(), userDTO);

	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.biz.UserBiz#resetPassword(java.lang.Long, java.lang.String, java.lang.String)    
	 */
	@Override
	@Transactional
	public void resetPassword(Long userId, String password, String resetReason) {
		userService.resetPassword(userId, password,resetReason);
	}

	/**
	 * 是否打开用户信息同步开关
	 * @return
	 */
	private boolean isSynchronizationOpen() {
		@SuppressWarnings("unchecked")
		ConfigParam<String> param = ConfigurationUtils
				.getAppConfigParam("employee-boss-synchronization");
		if (param != null && param.getValue() != null
				&& "1".equals(param.getValue().trim())) {
			return true;
		}
		return false;
	}

	@Override
	public UserDTO userLoginValidateByMobile(String mobile, String password) {
		return userService.userLoginValidateByMobile(mobile, password);
	}

}

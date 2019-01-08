/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.facade.impl;

import com.zbjdl.boss.admin.biz.SecurityBiz;
import com.zbjdl.boss.admin.biz.SecurityConfigBiz;
import com.zbjdl.boss.admin.biz.UserBiz;
import com.zbjdl.boss.admin.facade.UserFacade;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.ModifyPasswordDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.dto.UserDepartmentsDTO;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;
import com.zbjdl.boss.admin.user.exception.DepartmentException;
import com.zbjdl.boss.admin.user.exception.ModifyPasswordException;
import com.zbjdl.boss.admin.user.exception.UserAuthenticationException;
import com.zbjdl.boss.admin.user.exception.UserException;
import com.zbjdl.common.utils.CheckUtils;

import java.util.List;

/**
 * 类名称：UserFacadeImpl <br>
 * 类描述：用户门面实现类<br>
 * 
 * @author feng
 * @since 2011-6-22 下午02:33:55
 * @version 1.0.0
 */
public class UserFacadeImpl implements UserFacade {

	private UserBiz userBiz;

	private SecurityBiz securityBiz;

	private SecurityConfigBiz securityConfigBiz;

	@Override
	public UserDTO userLoginValidate(String loginName, String password)
			throws UserAuthenticationException {
		return userBiz.userLoginValidate(loginName, password);
	}

	@Override
	public boolean userAuthentication(Long userId, String password)
			throws UserAuthenticationException {
		return userBiz.userAuthentication(userId, password);
	}

	@Override
	public UserDTO queryUserById(Long userId) {
		return userBiz.queryUserById(userId);
	}

	@Override
	public List<UserDTO> queryUser(UserDTO userDTO) {
		return userBiz.queryUser(userDTO);
	}

	@Override
	public UserDTO queryUserByLoginName(String loginName) {
		return userBiz.queryUserByLoginName(loginName);
	}

	@Override
	public boolean updatePassword(ModifyPasswordDTO modifyPasswordDTO)
			throws ModifyPasswordException {
		return userBiz.updatePassword(modifyPasswordDTO);
	}

	@Override
	public DepartmentDTO queryDepartmentById(Long departmentId) {
		return userBiz.queryDepartmentById(departmentId);
	}

	@Override
	public List<DepartmentDTO> queryDepartments(DepartmentDTO departmentDTO) {
		return userBiz.queryDepartments(departmentDTO);
	}

	/**
	 * 描述： 注入biz业务类
	 * 
	 * @param userBiz
	 *            the userBiz to set
	 */
	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}

	@Override
	public UserDepartmentsDTO queryUserDepartments(Long userId)
			throws UserException {
		return null;
	}

	@Override
	public List<UserDTO> queryDepartmentUsers(Long departmentId)
			throws DepartmentException {

		return userBiz.queryDepartmentUsers(departmentId);
	}

	@Override
	public List<DepartmentDTO> queryAllDepartments() {
		return userBiz.queryAllDepartments();
	}

	public UserDTO userAuthorityValidate(String loginName, String password,
			Long functionId) throws UserAuthenticationException {
		UserDTO userDTO = userBiz.userLoginValidate(loginName, password);

		if (functionId == null) {
			functionId = -300L;
		}
		if (securityBiz.checkPermission(userDTO.getUserId(), functionId)) {
			return userDTO;
		}
		throw UserAuthenticationException.USERAUTHEN_NO_FUNCTION_EXCEPTION;
	}

	@Override
	public Long addUser(UserDTO userDTO, UserTypeEnum userType) {
		return securityConfigBiz.addUser(userDTO, userType);
	}

	@Override
	public void deleteUser(Long userId) {
		securityConfigBiz.deleteUser(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.UserFacade#frozeUser(java.lang.Long,
	 * java.lang.String, java.lang.Long)
	 */
	@Override
	public void frozeUser(Long userId, String frozenReason, Long operatorId) {
		securityConfigBiz.frozeUser(userId, frozenReason, operatorId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.UserFacade#unFrozeUser(java.lang
	 * .Long, java.lang.String, java.lang.Long)
	 */
	@Override
	public void unFrozeUser(Long userId, String activateReason, Long operatorId) {
		securityConfigBiz.unFrozeUser(userId, activateReason, operatorId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.UserFacade#validateUserStatus(java
	 * .lang.String)
	 */
	public boolean validateUserStatus(String userName) {
		return userBiz.validateUserStatus(userName);
	}


	@Override
	public void updateUserInfo(UserDTO userDTO) {
		CheckUtils.notNull(userDTO, "人员信息");
		CheckUtils.notNull(userDTO.getUserId(), "人员ID");
		userBiz.updateUserInfo(userDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.UserFacade#resetPassword(java.lang
	 * .Long, java.lang.String)
	 */
	@Override
	public void resetPassword(Long userId, String resetReason) {
		CheckUtils.notNull(userId, "人员ID");
		String password = "123qwe";
		userBiz.resetPassword(userId, password, resetReason);
	}

	/**
	 * @param securityBiz
	 *            the securityBiz to set
	 */
	public void setSecurityBiz(SecurityBiz securityBiz) {
		this.securityBiz = securityBiz;
	}

	/**
	 * @param securityConfigBiz
	 *            the securityConfigBiz to set
	 */
	public void setSecurityConfigBiz(SecurityConfigBiz securityConfigBiz) {
		this.securityConfigBiz = securityConfigBiz;
	}

	@Override
	public UserDTO userLoginValidateByMobile(String mobile, String password) throws UserAuthenticationException {
		return userBiz.userLoginValidateByMobile(mobile, password);
	}

}

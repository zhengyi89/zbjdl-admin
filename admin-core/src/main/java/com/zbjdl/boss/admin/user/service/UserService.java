/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.user.service;

import com.zbjdl.boss.admin.user.dto.ModifyPasswordDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.UserInfoEnum;
import com.zbjdl.boss.admin.user.exception.UserAuthenticationException;

import java.util.List;

/**
 * 类名称：UserService <br>
 * 类描述：用户管理Service<br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-6-17 下午05:01:09
 */
public interface UserService {

	/**
	 * 描述：创建用户
	 *
	 * @param userDto 用户注册信息
	 * @return 用户id
	 */
	public Long createUser(UserDTO userDto);

	/**
	 * 描述：删除用户
	 *
	 * @param userId
	 * @return 删除结果
	 */
	public boolean deleteUser(Long userId);

	/**
	 * 描述：   冻结用户
	 *
	 * @param userId
	 * @param frozenReason 冻结原因
	 * @return 冻结结果
	 */
	public boolean frozenUser(Long userId, String frozenReason, Long adminUserId);

	/**
	 * 描述：    激活冻结用户
	 *
	 * @param userId
	 * @param activateReason 激活原因
	 * @return 激活结果
	 */
	public boolean activateUser(Long userId, String activateReason, Long adminUserId);

	/**
	 * 描述：    停用用户
	 *
	 * @param userId
	 * @param forbidReason 停用原因
	 * @return 停用结果
	 */
	public boolean forbidUser(Long userId, String forbidReason, Long adminUserId);

	/**
	 * 描述：    重置密码
	 *
	 * @param userId
	 * @param password:重置后的密码
	 * @param resetReason     重置原因
	 * @return 重置结果
	 */
	public boolean resetPassword(Long userId, String password, String resetReason);

	/**
	 * 描述：    修改密码
	 *
	 * @param modifyPasswordDTO 密码相关信息
	 * @return 修改结果
	 */
	public boolean updatePassword(ModifyPasswordDTO modifyPasswordDTO);

	/**
	 * 描述：    更新用户信息
	 *
	 * @param userId
	 * @param userDto
	 * @return 更新结果
	 */
	public boolean updateUserInfo(Long userId, UserDTO userDto);

	/**
	 * 描述：    验证用户是否存在
	 *
	 * @param userDto 用户注册信息
	 * @return 验证结果
	 */
	public boolean validateUserExistOrnot(UserDTO userDto);

	/**
	 * 描述：    通过userId判断用户是否存在
	 *
	 * @param userId
	 * @return 验证结果
	 */
	public boolean validateUserExistById(Long userId);

	/**
	 * 描述：   验证用户和管理员是否属于同一部门
	 * 验证用户信息的主部门ID和管理员的主部门ID是否一致
	 *
	 * @param userDto
	 * @param adminUserId 管理员Id
	 * @return 验证结果
	 */
	public boolean validateUserToAdministrator(UserDTO userDto, Long adminUserId);

	/**
	 * 描述：    验证用户信息是否合法
	 *
	 * @param userDto
	 * @return 验证结果
	 */
	public UserInfoEnum validateUserInfo(UserDTO userDto);

	/**
	 * 用户登录验证
	 *
	 * @param loginName
	 * @param password
	 * @return 所需要的用户信息
	 */
	public UserDTO userLoginValidate(String loginName, String password) throws UserAuthenticationException;

	/**
	 * 用户身份验证
	 *
	 * @param userId
	 * @param password
	 * @return 是否是管理员
	 */
	public boolean userAuthentication(Long userId, String password);

	/**
	 * 描述：    根据userId查找用户
	 *
	 * @param userId
	 * @return 用户信息
	 */
	public UserDTO queryUserById(Long userId);

	/**
	 * 描述：    根据用户信息查找用户
	 *
	 * @param userDTO
	 * @return 用户信息列表
	 */
	public List<UserDTO> queryUser(UserDTO userDTO);

	/**
	 * 描述：    更换用户主部门
	 *
	 * @param userId
	 * @param departmentId
	 * @return
	 */
	public boolean updateUserPrimaryDepartmentId(Long userId, Long departmentId);

	/**
	 * 批量更新用户主部门 Id
	 *
	 * @param departmentId    原部门编号
	 * @param newDepartmentId 拟迁移部门编号
	 * @return 是否成功
	 * @author feng
	 * @since 2013-06-27 16:10:56
	 */
	public boolean migrateUser(Long departmentId, Long newDepartmentId);

	/**
	 * 批量指定部门的所有用户数据(包含相关的关系)
	 *
	 * @param departmentId 部门编号
	 * @return 是否成功
	 * @author feng
	 * @since 2013-06-27 16:10:56
	 */
	public boolean batchDeleteUserByDepartmentId(Long departmentId);

	/**
	 * 描述：  验证当前登陆用户是不是系统管理员
	 *
	 * @param adminUserId
	 * @return 验证结果
	 */
	public boolean validateUserIsSuperAdmin(Long adminUserId);

	/**
	 * 描述：    验证所要操作的用户主部门是否与操作员主部门一致
	 *
	 * @param userId
	 * @param adminUserId
	 * @return 验证结果
	 */
	public boolean validateUserToAdministrator(Long userId, Long adminUserId);

	/**
	 * 描述：取出所有的用户
	 *
	 * @return 用户列表
	 */
	public List<UserDTO> queryAllUser();

	/**
	 * 描述：    查询系统管理员
	 *
	 * @return
	 */
	public List<UserDTO> querySuperAdmin();

	/**
	 * 描述：   验证是否是系统管理员
	 *
	 * @param
	 * @return
	 */
	public boolean validateSuperAdmin(Long userId, String password);

	/**
	 * 描述：    通过登陆名查询用户
	 *
	 * @param loginName
	 * @return
	 */
	public UserDTO queryUserByLoginName(String loginName);

	/**
	 * 描述：    根据登录名验证密码是否正确
	 *
	 * @param loginName
	 * @param password
	 * @return
	 */
	public boolean validatePasswordByLoginName(String loginName, String password);
	
	/**
	 * 用户登录验证
	 *
	 * @param mobile
	 * @param password
	 * @return 所需要的用户信息
	 */
	public UserDTO userLoginValidateByMobile(String mobile, String password);
}

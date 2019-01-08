/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.facade;

import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.ModifyPasswordDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.dto.UserDepartmentsDTO;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;
import com.zbjdl.boss.admin.user.exception.DepartmentException;
import com.zbjdl.boss.admin.user.exception.ModifyPasswordException;
import com.zbjdl.boss.admin.user.exception.UserAuthenticationException;
import com.zbjdl.boss.admin.user.exception.UserException;

import java.util.List;

/**
 * 类名称：UserFacade <br>
 * 类描述：用户管理Facade接口<br>
 * 
 * @author feng
 * @version 1.0.0
 * @since 2011-6-20 上午10:53:15
 */
public interface UserFacade {

	/**
	 * 用户登录验证并判断是否有functionId对应的功能权限(会员使用)
	 * 
	 * @param loginName
	 * @param password
	 * @param functionId
	 * @return 所需要的用户信息
	 */
	public UserDTO userAuthorityValidate(String loginName, String password,
			Long functionId) throws UserAuthenticationException;

	/**
	 * 用户登录验证
	 * 
	 * @param loginName
	 * @param password
	 * @return 所需要的用户信息
	 */
	public UserDTO userLoginValidate(String loginName, String password)
			throws UserAuthenticationException;

	/**
	 * 用户身份验证
	 * 
	 * @param userId
	 * @param password
	 * @return 是否是管理员
	 */
	public boolean userAuthentication(Long userId, String password)
			throws UserAuthenticationException;

	/**
	 * 描述： 根据用户ID查询用户
	 * 
	 * @param userId
	 * @return 用户信息
	 */
	public UserDTO queryUserById(Long userId);

	/**
	 * 描述： 根据用户信息查找用户，UserDTO为空则全查 用 loginName 查询推荐使用 queryUserByLoginName 方法
	 * 
	 * @param userDTO
	 * @return 用户信息列表
	 */
	public List<UserDTO> queryUser(UserDTO userDTO);

	/**
	 * 描述： 根据登录名查找用户
	 * 
	 * @param loginName
	 *            登录名
	 * @return 用户信息列表
	 */
	public UserDTO queryUserByLoginName(String loginName);

	/**
	 * 修改密码
	 * 
	 * @param modifyPasswordDTO
	 * @return 修改结果
	 */
	public boolean updatePassword(ModifyPasswordDTO modifyPasswordDTO)
			throws ModifyPasswordException;

	/**
	 * 描述： 根据部门ID查找部门
	 * 
	 * @param departmentId
	 * @return 部门信息
	 */
	public DepartmentDTO queryDepartmentById(Long departmentId);

	/**
	 * 描述： 根据部门信息查找部门
	 * 
	 * @param departmentDTO
	 * @return 部门列表
	 */
	public List<DepartmentDTO> queryDepartments(DepartmentDTO departmentDTO);

	/**
	 * 描述： 查询用户所有部门
	 * 
	 * @param userId
	 *            用户ID
	 * @return 部门列表
	 * @throws UserException
	 *             用户不存在
	 */
	public UserDepartmentsDTO queryUserDepartments(Long userId)
			throws UserException;

	/**
	 * 描述： 查询部门所有用户
	 * 
	 * @param userId
	 *            用户ID
	 * @return 部门列表
	 * @throws UserException
	 *             部门不存在
	 */
	public List<UserDTO> queryDepartmentUsers(Long departmentId)
			throws DepartmentException;

	/**
	 * 描述： 获取所有的部门信息
	 * 
	 * @return
	 */
	public List<DepartmentDTO> queryAllDepartments();

	/**
	 * 描述： 通过用户名称 验证用户状态，只有活动的用户才会返回true
	 * 
	 * @param userName
	 * @return
	 */
	public boolean validateUserStatus(String userName);

	/**
	 * 添加人员
	 * 
	 * @param userDTO
	 *            ：人员
	 * @param userType
	 *            ：人员类型
	 */
	public Long addUser(UserDTO userDTO, UserTypeEnum userType);

	/**
	 * 删除人员
	 * 
	 * @param userId
	 */
	public void deleteUser(Long userId);

	/**
	 * 冻结人员
	 * 
	 * @param userId
	 *            ：人员ID
	 * @param frozenReason
	 *            ：冻结原因
	 * @param operatorId
	 *            ：操作人员ID
	 */
	public void frozeUser(Long userId, String frozenReason, Long operatorId);

	/**
	 * 解冻人员
	 * 
	 * @param userId
	 *            ：人员ID
	 * @param activateReason
	 *            ：解冻原因
	 * @param operatorId
	 *            ：操作人员ID
	 */
	public void unFrozeUser(Long userId, String activateReason, Long operatorId);

	/**
	 * 更新人员信息
	 * 
	 * @param userDTO
	 *            :人员信息
	 */
	public void updateUserInfo(UserDTO userDTO);

	/**
	 * 重置密码
	 * 
	 * @param userId
	 *            :人员ID
	 * @param resetReason
	 *            :重置原因
	 */
	public void resetPassword(Long userId, String resetReason);
	
	/**
	 * 用户登录验证
	 * 
	 * @param loginName
	 * @param password
	 * @return 所需要的用户信息
	 */
	public UserDTO userLoginValidateByMobile(String mobile, String password)
			throws UserAuthenticationException;

}
